ALTER TABLE bookings
    ADD COLUMN qr_token UUID NOT NULL DEFAULT gen_random_uuid(),
    ADD COLUMN checked_in BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN checked_in_at TIMESTAMP;

CREATE INDEX idx_booking_qr_token ON bookings(qr_token);
