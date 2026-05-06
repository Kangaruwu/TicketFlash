package com.barox.TicketFlash;

import com.barox.ticketflash.entity.Event;
import com.barox.ticketflash.entity.TicketClass;
import com.barox.ticketflash.entity.Venue;
import com.barox.ticketflash.enums.EventStatus;
import com.barox.ticketflash.repository.EventRepository;
import com.barox.ticketflash.repository.TicketClassRepository;
import com.barox.ticketflash.repository.VenueRepository;
import com.barox.ticketflash.TicketFlashApplication;
import com.barox.ticketflash.service.producer.BookingProducer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.api.StatefulRedisConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TicketFlashApplication.class, properties = {
    "app.jwt.secret=ZmFrZS1zZWNyZXQta2V5LWZvci10ZXN0cy1mYWtlLXNlY3JldC1rZXk=",
    "app.jwt.expiration=1",
    "spring.rabbitmq.host=localhost",
    "spring.rabbitmq.port=5672",
    "spring.rabbitmq.username=guest",
    "spring.rabbitmq.password=guest",
    "spring.rabbitmq.listener.simple.auto-startup=false",
    "spring.rabbitmq.listener.direct.auto-startup=false",
    "spring.data.redis.host=localhost",
    "spring.data.redis.port=6379",
    "spring.data.redis.password=test"
})
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookingQrCheckInIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketClassRepository ticketClassRepository;

    @MockitoBean
    private BookingProducer bookingProducer;

    @MockitoBean(name = "bucket4jConnection", answers = Answers.RETURNS_DEEP_STUBS)
    private StatefulRedisConnection<byte[], byte[]> bucket4jConnection;

    private Long eventId;
    private Long ticketClassId;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        ticketClassRepository.deleteAll();
        eventRepository.deleteAll();
        venueRepository.deleteAll();

        Venue venue = Venue.builder()
            .name("Integration Venue")
            .address("Integration Address")
            .capacity(100)
            .build();
        Venue savedVenue = venueRepository.save(venue);

        Event event = Event.builder()
            .name("Integration Event")
            .description("Integration test event")
            .startTime(LocalDateTime.now().plusDays(1))
            .endTime(LocalDateTime.now().plusDays(1).plusHours(2))
            .status(EventStatus.PUBLISHED)
            .venue(savedVenue)
            .build();
        Event savedEvent = eventRepository.save(event);

        TicketClass ticketClass = TicketClass.builder()
            .name("VIP")
            .price(BigDecimal.valueOf(200))
            .quantityAvailable(20)
            .quantitySold(0)
            .event(savedEvent)
            .build();
        TicketClass savedTicketClass = ticketClassRepository.save(ticketClass);

        this.eventId = savedEvent.getId();
        this.ticketClassId = savedTicketClass.getId();

        doNothing().when(bookingProducer).sendBookingToQueue(any(UUID.class));
    }

    @Test
    void should_complete_booking_payment_qr_and_checkin_flow() throws Exception {
        String userEmail = "user_" + System.nanoTime() + "@example.com";
        String adminEmail = "admin_" + System.nanoTime() + "@example.com";

        registerUser("user_it_" + System.nanoTime(), userEmail, "123456");
        registerAdmin("admin_it_" + System.nanoTime(), adminEmail, "123456");

        String userToken = loginAndGetAccessToken(userEmail, "123456");
        String adminToken = loginAndGetAccessToken(adminEmail, "123456");

        String bookingRequestJson = """
            {
              "eventId": %d,
              "ticketDetails": [
                {
                  "ticketId": %d,
                  "quantity": 2
                }
              ]
            }
            """.formatted(eventId, ticketClassId);

        MvcResult bookingResult = mockMvc.perform(post("/api/v1/bookings")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookingRequestJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", not(nullValue())))
            .andExpect(jsonPath("$.qrToken", not(nullValue())))
            .andExpect(jsonPath("$.status").value("PENDING"))
            .andReturn();

        JsonNode bookingJson = objectMapper.readTree(bookingResult.getResponse().getContentAsString());
        String bookingId = bookingJson.get("id").asText();
        String qrToken = bookingJson.get("qrToken").asText();

        mockMvc.perform(post("/api/v1/payments/{bookingId}/pay", bookingId)
                .header("Authorization", "Bearer " + userToken))
            .andExpect(status().isOk());

        byte[] qrImage = mockMvc.perform(get("/api/v1/bookings/my-bookings/{bookingId}/qr", bookingId)
                .header("Authorization", "Bearer " + userToken))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_PNG))
            .andReturn()
            .getResponse()
            .getContentAsByteArray();

        assertTrue(qrImage.length > 50, "QR image should not be empty");

        String checkInRequestJson = """
            {
              "bookingId": "%s",
              "qrToken": "%s"
            }
            """.formatted(bookingId, qrToken);

        mockMvc.perform(post("/api/v1/check-in")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(checkInRequestJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.bookingId").value(bookingId))
            .andExpect(jsonPath("$.checkedIn").value(true))
            .andExpect(jsonPath("$.checkedInAt", not(nullValue())));
    }

    private void registerUser(String username, String email, String password) throws Exception {
        String payload = """
            {
              "username": "%s",
              "email": "%s",
              "password": "%s",
              "fullName": "Test User"
            }
            """.formatted(username, email, password);

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk());
    }

    private void registerAdmin(String username, String email, String password) throws Exception {
        String payload = """
            {
              "username": "%s",
              "email": "%s",
              "password": "%s",
              "fullName": "Test Admin"
            }
            """.formatted(username, email, password);

        mockMvc.perform(post("/api/v1/auth/register-admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk());
    }

    private String loginAndGetAccessToken(String usernameOrEmail, String password) throws Exception {
        String payload = """
            {
              "usernameOrEmail": "%s",
              "password": "%s"
            }
            """.formatted(usernameOrEmail, password);

        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        return jsonNode.get("tokens").get("accessToken").asText();
    }
}
