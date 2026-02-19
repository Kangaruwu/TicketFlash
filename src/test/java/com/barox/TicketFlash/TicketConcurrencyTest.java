package com.barox.TicketFlash;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.barox.ticketflash.entity.Event;
import com.barox.ticketflash.entity.TicketClass;
import com.barox.ticketflash.entity.Venue;
import com.barox.ticketflash.enums.EventStatus;
import com.barox.ticketflash.repository.EventRepository;
import com.barox.ticketflash.repository.TicketClassRepository;
import com.barox.ticketflash.repository.VenueRepository;
import com.barox.ticketflash.service.BookingService;
import com.barox.ticketflash.dto.request.BookingRequest;
import com.barox.ticketflash.dto.request.TicketBookingRequest;


@SpringBootTest
@Disabled
@ActiveProfiles("test")
public class TicketConcurrencyTest {
    private final BookingService bookingService;
    private final EventRepository eventRepository;
    private final TicketClassRepository ticketClassRepository;
    private final VenueRepository venueRepository;
    private Long ticketClassId;
    private Long eventId;

    @Autowired
    public TicketConcurrencyTest(BookingService bookingService, 
                                 EventRepository eventRepository, 
                                 TicketClassRepository ticketClassRepository, 
                                 VenueRepository venueRepository) {
        this.bookingService = bookingService;
        this.eventRepository = eventRepository;
        this.ticketClassRepository = ticketClassRepository;
        this.venueRepository = venueRepository;
    }

    @BeforeEach
    void setup() {
        eventRepository.deleteAll();
        ticketClassRepository.deleteAll();
        venueRepository.deleteAll();

        Venue venue = new Venue();
        venue.setName("Test Venue");
        venue.setAddress("Test Address");
        venue.setCapacity(100);
        Venue savedVenue = venueRepository.save(venue);

        Event event = new Event();
        event.setVenue(savedVenue);
        event.setName("Test Event");
        event.setStartTime(LocalDateTime.now().plusHours(1));
        event.setEndTime(LocalDateTime.now().plusHours(2));
        event.setStatus(EventStatus.PUBLISHED);
        Event savedEvent = eventRepository.save(event);

        TicketClass ticketClass = new TicketClass();
        ticketClass.setEvent(savedEvent);
        ticketClass.setName("General Admission");
        ticketClass.setPrice(BigDecimal.valueOf(50));
        ticketClass.setQuantityAvailable(10);
        ticketClass.setQuantitySold(0);
        TicketClass savedTicketClass = ticketClassRepository.save(ticketClass);

        this.ticketClassId = savedTicketClass.getId();
        this.eventId = savedEvent.getId();
    }


    @Test
    public void testConcurrentBooking() throws InterruptedException {
        int numberOfThreads = 15;  // More threads than available tickets to test race condition
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        AtomicInteger successfulBookings = new AtomicInteger(0);
        AtomicInteger failedBookings = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    latch.await();  // Wait for all threads to be ready
                    bookingService.bookTickets(new BookingRequest(
                        eventId, 
                        "test@example.com", 
                        List.of(new TicketBookingRequest(ticketClassId, Integer.valueOf(1)))
                    ));
                    successfulBookings.incrementAndGet();
                } catch (Exception e) {
                    failedBookings.incrementAndGet();
                }
            });
        }

        latch.countDown();  // Release all threads at once

        executorService.shutdown();
        executorService.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS);

        TicketClass updatedTicketClass = ticketClassRepository.findById(ticketClassId).orElse(null);
        
        // Should sell exactly 10 tickets (the available quantity)
        assertEquals(0, updatedTicketClass.getQuantityAvailable());
        assertEquals(10, updatedTicketClass.getQuantitySold());
        
        // 10 successful bookings, 5 failed
        assertEquals(10, successfulBookings.get());
        assertEquals(5, failedBookings.get());

        System.out.println("Successful bookings: " + successfulBookings.get());
        System.out.println("Failed bookings: " + failedBookings.get());
        System.out.println("Total bookings: " + (successfulBookings.get() + failedBookings.get()));
    }

}
