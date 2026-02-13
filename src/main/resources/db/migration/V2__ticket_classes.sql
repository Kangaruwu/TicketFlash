CREATE TABLE ticket_classes (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(19, 4) NOT NULL,
    quantity_available INTEGER NOT NULL,
    quantity_sold INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT fk_ticket_class_event FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE INDEX idx_ticket_class_event_id ON ticket_classes(event_id);
CREATE INDEX idx_ticket_class_name ON ticket_classes(name);