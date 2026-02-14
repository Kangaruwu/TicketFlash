CREATE TABLE bookings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    booking_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_booking_event FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE INDEX idx_booking_event_id ON bookings(event_id);
CREATE INDEX idx_booking_email ON bookings(email);
CREATE INDEX idx_booking_status ON bookings(status);
CREATE INDEX idx_booking_booking_date ON bookings(booking_date);

ALTER TABLE ticket_classes ADD COLUMN booking_id UUID;
ALTER TABLE ticket_classes ADD CONSTRAINT fk_ticket_class_booking FOREIGN KEY (booking_id) REFERENCES bookings(id);
CREATE INDEX idx_ticket_class_booking_id ON ticket_classes(booking_id);