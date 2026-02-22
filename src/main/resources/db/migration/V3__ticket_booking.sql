CREATE TABLE bookings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    booking_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_ticket INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_booking_event FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE INDEX idx_booking_event_id ON bookings(event_id);
CREATE INDEX idx_booking_email ON bookings(email);
CREATE INDEX idx_booking_status ON bookings(status);
CREATE INDEX idx_booking_booking_date ON bookings(booking_date);

CREATE TABLE ticket_booking_details (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    booking_id UUID NOT NULL,
    ticket_class_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(19, 4) NOT NULL,
    CONSTRAINT fk_ticket_booking_detail_booking FOREIGN KEY (booking_id) REFERENCES bookings(id),
    CONSTRAINT fk_ticket_booking_detail_ticket_class FOREIGN KEY (ticket_class_id) REFERENCES ticket_classes(id)
);

CREATE INDEX idx_ticket_booking_detail_booking_id ON ticket_booking_details(booking_id);
CREATE INDEX idx_ticket_booking_detail_ticket_class_id ON ticket_booking_details(ticket_class_id);