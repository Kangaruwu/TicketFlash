-- =============================================================
-- SEED DATA — TicketFlash
-- All user passwords are: password123
-- BCrypt hash: $2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy
-- This migration avoids hardcoded FK IDs by resolving references via names.
-- =============================================================

-- USERS
INSERT INTO users (username, full_name, password, email, role) VALUES
  ('admin',  'Admin TicketFlash', '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'admin@ticketflash.io',  'ADMIN'),
  ('staff1', 'Staff Checkin 1',   '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'staff1@ticketflash.io', 'ADMIN'),
  ('staff2', 'Staff Checkin 2',   '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'staff2@ticketflash.io', 'ADMIN'),
  ('alice',  'Alice Nguyen',      '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'alice@example.com',     'USER'),
  ('bob',    'Bob Tran',          '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'bob@example.com',       'USER'),
  ('carol',  'Carol Le',          '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'carol@example.com',     'USER'),
  ('dave',   'Dave Pham',         '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'dave@example.com',      'USER'),
  ('eve',    'Eve Hoang',         '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'eve@example.com',       'USER'),
  ('frank',  'Frank Dang',        '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'frank@example.com',     'USER'),
  ('grace',  'Grace Vu',          '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'grace@example.com',     'USER'),
  ('henry',  'Henry Dinh',        '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'henry@example.com',     'USER'),
  ('iris',   'Iris Bui',          '$2a$12$HCknbLUApY7w6AzmMAkeje2dTmR1g9SS7PKvz/m91FX772pGXbKXy', 'iris@example.com',      'USER');

-- VENUES
INSERT INTO venues (name, address, capacity) VALUES
  ('National Convention Center', 'Pham Hung, Hanoi',                     50000),
  ('Hanoi Opera House',          'Trang Tien, Hoan Kiem, Hanoi',           900),
  ('GEM Center',                 'Nguyen Binh Khiem, District 1, HCMC',   3000),
  ('Hoa Xuan Arena',             'Cam Le, Da Nang',                        8000),
  ('Hoi An Culture Center',      'Tran Phu, Hoi An',                        500),
  ('White Palace',               'Hoang Van Thu, Phu Nhuan, HCMC',        5000),
  ('Da Lat Dragon Arena',        'Lam Vien Square, Da Lat',                6000),
  ('Viet Xo Friendship Palace',  'Tran Hung Dao, Hanoi',                   1200);

-- EVENTS
INSERT INTO events (name, description, start_time, end_time, status, venue_id) VALUES
  ('VinFest Music 2026',                 'Large outdoor music festival.', '2026-03-15 17:00:00', '2026-03-15 23:00:00', 'PUBLISHED', (SELECT id FROM venues WHERE name = 'National Convention Center')),
  ('Tech Summit Vietnam 2026',           'Technology conference.',         '2026-07-10 08:30:00', '2026-07-11 18:00:00', 'PUBLISHED', (SELECT id FROM venues WHERE name = 'National Convention Center')),
  ('Comedy Night Live May',              'Stand-up comedy show.',          '2026-05-01 19:30:00', '2026-05-01 22:00:00', 'PUBLISHED', (SELECT id FROM venues WHERE name = 'GEM Center')),
  ('K-Pop Fanmeeting 2026',              'Fan meeting event.',             '2026-08-05 18:00:00', '2026-08-05 22:30:00', 'PUBLISHED', (SELECT id FROM venues WHERE name = 'Hoa Xuan Arena')),
  ('European Classical Night',           'Classical concert.',             '2026-06-28 20:00:00', '2026-06-28 22:30:00', 'PUBLISHED', (SELECT id FROM venues WHERE name = 'Hoi An Culture Center')),
  ('Vietnam Gaming Expo 2026',           'Gaming expo.',                   '2026-10-15 09:00:00', '2026-10-17 20:00:00', 'DRAFT',     (SELECT id FROM venues WHERE name = 'White Palace')),
  ('Food and Wine International Fest',   'Food and wine festival.',        '2026-09-05 10:00:00', '2026-09-07 22:00:00', 'PUBLISHED', (SELECT id FROM venues WHERE name = 'White Palace')),
  ('Hoi An Jazz and Blue Festival',      'Jazz and blues festival.',       '2026-07-25 19:00:00', '2026-07-27 23:00:00', 'PUBLISHED', (SELECT id FROM venues WHERE name = 'Hoi An Culture Center')),
  ('EDM Night Da Nang',                  'EDM show (cancelled).',          '2026-07-20 20:00:00', '2026-07-21 02:00:00', 'CANCELLED', (SELECT id FROM venues WHERE name = 'Hoa Xuan Arena')),
  ('Startup Pitch Day 2025',             'Startup pitching day.',          '2025-12-01 08:00:00', '2025-12-01 18:00:00', 'PUBLISHED', (SELECT id FROM venues WHERE name = 'GEM Center')),
  ('Rock Fest Hanoi 2026',               'Rock music festival.',           '2026-11-20 16:00:00', '2026-11-20 23:59:00', 'PUBLISHED', (SELECT id FROM venues WHERE name = 'National Convention Center')),
  ('Swan Lake Ballet by Bolshoi',        'Ballet performance.',            '2026-06-14 19:30:00', '2026-06-14 22:00:00', 'PUBLISHED', (SELECT id FROM venues WHERE name = 'Hanoi Opera House'));

-- TICKET CLASSES
INSERT INTO ticket_classes (event_id, name, price, quantity_available, quantity_sold)
SELECT e.id, x.class_name, x.price, x.qty, 0
FROM events e
JOIN (
  VALUES
    ('VinFest Music 2026',               'VIP',      500000.0000,  200),
    ('VinFest Music 2026',               'Standard', 200000.0000,  800),
    ('VinFest Music 2026',               'Economy',  100000.0000, 1500),
    ('Tech Summit Vietnam 2026',         'VIP',     1500000.0000,  100),
    ('Tech Summit Vietnam 2026',         'Standard', 600000.0000,  400),
    ('Tech Summit Vietnam 2026',         'Economy',  250000.0000,  800),
    ('Comedy Night Live May',            'VIP',      450000.0000,  100),
    ('Comedy Night Live May',            'Standard', 200000.0000,  300),
    ('Comedy Night Live May',            'Economy',   80000.0000,  500),
    ('K-Pop Fanmeeting 2026',            'VIP',     2000000.0000,  150),
    ('K-Pop Fanmeeting 2026',            'Standard', 800000.0000,  500),
    ('K-Pop Fanmeeting 2026',            'Economy',  350000.0000, 1000),
    ('European Classical Night',         'VIP',     1200000.0000,   50),
    ('European Classical Night',         'Standard', 500000.0000,  150),
    ('European Classical Night',         'Economy',  200000.0000,  300),
    ('Vietnam Gaming Expo 2026',         'VIP',      600000.0000,  100),
    ('Vietnam Gaming Expo 2026',         'Standard', 250000.0000,  300),
    ('Vietnam Gaming Expo 2026',         'Economy',  120000.0000,  600),
    ('Food and Wine International Fest', 'VIP',     1000000.0000,  120),
    ('Food and Wine International Fest', 'Standard', 400000.0000,  400),
    ('Food and Wine International Fest', 'Economy',  150000.0000,  800),
    ('Hoi An Jazz and Blue Festival',    'VIP',      900000.0000,   80),
    ('Hoi An Jazz and Blue Festival',    'Standard', 350000.0000,  250),
    ('Hoi An Jazz and Blue Festival',    'Economy',  150000.0000,  500),
    ('EDM Night Da Nang',                'VIP',     1100000.0000,  200),
    ('EDM Night Da Nang',                'Standard', 450000.0000,  600),
    ('EDM Night Da Nang',                'Economy',  200000.0000, 1200),
    ('Startup Pitch Day 2025',           'VIP',      800000.0000,   50),
    ('Startup Pitch Day 2025',           'Standard', 300000.0000,  200),
    ('Startup Pitch Day 2025',           'Economy',  100000.0000,  400),
    ('Rock Fest Hanoi 2026',             'VIP',      700000.0000,  300),
    ('Rock Fest Hanoi 2026',             'Standard', 280000.0000,  800),
    ('Rock Fest Hanoi 2026',             'Economy',  130000.0000, 1500),
    ('Swan Lake Ballet by Bolshoi',      'VIP',     1800000.0000,   60),
    ('Swan Lake Ballet by Bolshoi',      'Standard', 700000.0000,  180),
    ('Swan Lake Ballet by Bolshoi',      'Economy',  300000.0000,  400)
) x(event_name, class_name, price, qty) ON x.event_name = e.name;

-- BOOKINGS
INSERT INTO bookings (id, event_id, email, booking_date, total_ticket, status, qr_token, checked_in, checked_in_at) VALUES
  ('b0000000-0000-0000-0000-000000000001', (SELECT id FROM events WHERE name='VinFest Music 2026'),             'alice@example.com', '2026-02-10 09:00:00', 3, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000001', TRUE,  '2026-03-15 16:45:00'),
  ('b0000000-0000-0000-0000-000000000002', (SELECT id FROM events WHERE name='VinFest Music 2026'),             'bob@example.com',   '2026-02-11 11:00:00', 3, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000002', TRUE,  '2026-03-15 17:10:00'),
  ('b0000000-0000-0000-0000-000000000003', (SELECT id FROM events WHERE name='VinFest Music 2026'),             'carol@example.com', '2026-02-12 14:00:00', 2, 'CANCELLED', 'a0000000-0000-0000-0000-000000000003', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000004', (SELECT id FROM events WHERE name='VinFest Music 2026'),             'dave@example.com',  '2026-02-13 10:30:00', 4, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000004', TRUE,  '2026-03-15 17:30:00'),
  ('b0000000-0000-0000-0000-000000000005', (SELECT id FROM events WHERE name='Tech Summit Vietnam 2026'),       'alice@example.com', '2026-04-01 10:00:00', 2, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000005', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000006', (SELECT id FROM events WHERE name='Tech Summit Vietnam 2026'),       'bob@example.com',   '2026-04-02 11:00:00', 2, 'PENDING',   'a0000000-0000-0000-0000-000000000006', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000007', (SELECT id FROM events WHERE name='Tech Summit Vietnam 2026'),       'carol@example.com', '2026-04-03 09:30:00', 1, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000007', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000008', (SELECT id FROM events WHERE name='Comedy Night Live May'),          'bob@example.com',   '2026-03-20 10:00:00', 2, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000008', TRUE,  '2026-05-01 19:20:00'),
  ('b0000000-0000-0000-0000-000000000009', (SELECT id FROM events WHERE name='Comedy Night Live May'),          'carol@example.com', '2026-03-21 11:00:00', 1, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000009', TRUE,  '2026-05-01 19:15:00'),
  ('b0000000-0000-0000-0000-000000000010', (SELECT id FROM events WHERE name='Comedy Night Live May'),          'dave@example.com',  '2026-03-22 12:00:00', 2, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000010', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000011', (SELECT id FROM events WHERE name='K-Pop Fanmeeting 2026'),          'eve@example.com',   '2026-04-11 11:00:00', 1, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000011', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000012', (SELECT id FROM events WHERE name='K-Pop Fanmeeting 2026'),          'grace@example.com', '2026-04-12 12:00:00', 4, 'PENDING',   'a0000000-0000-0000-0000-000000000012', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000013', (SELECT id FROM events WHERE name='European Classical Night'),       'carol@example.com', '2026-04-20 10:00:00', 2, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000013', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000014', (SELECT id FROM events WHERE name='European Classical Night'),       'dave@example.com',  '2026-04-21 11:00:00', 1, 'PENDING',   'a0000000-0000-0000-0000-000000000014', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000015', (SELECT id FROM events WHERE name='Food and Wine International Fest'),'frank@example.com', '2026-05-01 10:00:00', 3, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000015', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000016', (SELECT id FROM events WHERE name='Food and Wine International Fest'),'grace@example.com', '2026-05-02 11:00:00', 2, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000016', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000017', (SELECT id FROM events WHERE name='Food and Wine International Fest'),'henry@example.com', '2026-05-03 12:00:00', 2, 'PENDING',   'a0000000-0000-0000-0000-000000000017', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000018', (SELECT id FROM events WHERE name='Hoi An Jazz and Blue Festival'),  'alice@example.com', '2026-05-01 09:00:00', 1, 'PENDING',   'a0000000-0000-0000-0000-000000000018', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000019', (SELECT id FROM events WHERE name='Hoi An Jazz and Blue Festival'),  'bob@example.com',   '2026-05-02 10:00:00', 2, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000019', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000020', (SELECT id FROM events WHERE name='Startup Pitch Day 2025'),         'alice@example.com', '2025-11-01 10:00:00', 1, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000020', TRUE,  '2025-12-01 08:05:00'),
  ('b0000000-0000-0000-0000-000000000021', (SELECT id FROM events WHERE name='Startup Pitch Day 2025'),         'bob@example.com',   '2025-11-02 11:00:00', 2, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000021', TRUE,  '2025-12-01 08:10:00'),
  ('b0000000-0000-0000-0000-000000000022', (SELECT id FROM events WHERE name='Startup Pitch Day 2025'),         'carol@example.com', '2025-11-03 12:00:00', 1, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000022', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000023', (SELECT id FROM events WHERE name='Rock Fest Hanoi 2026'),           'eve@example.com',   '2026-05-01 10:00:00', 2, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000023', FALSE, NULL),
  ('b0000000-0000-0000-0000-000000000024', (SELECT id FROM events WHERE name='Swan Lake Ballet by Bolshoi'),    'iris@example.com',  '2026-05-01 10:00:00', 2, 'CONFIRMED', 'a0000000-0000-0000-0000-000000000024', FALSE, NULL);

-- BOOKING DETAILS
INSERT INTO ticket_booking_details (booking_id, ticket_class_id, quantity, price) VALUES
  ('b0000000-0000-0000-0000-000000000001', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='VinFest Music 2026' AND tc.name='VIP'), 1, 500000.0000),
  ('b0000000-0000-0000-0000-000000000001', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='VinFest Music 2026' AND tc.name='Standard'), 2, 200000.0000),
  ('b0000000-0000-0000-0000-000000000002', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='VinFest Music 2026' AND tc.name='Standard'), 3, 200000.0000),
  ('b0000000-0000-0000-0000-000000000003', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='VinFest Music 2026' AND tc.name='Economy'), 2, 100000.0000),
  ('b0000000-0000-0000-0000-000000000004', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='VinFest Music 2026' AND tc.name='Standard'), 2, 200000.0000),
  ('b0000000-0000-0000-0000-000000000004', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='VinFest Music 2026' AND tc.name='Economy'), 2, 100000.0000),
  ('b0000000-0000-0000-0000-000000000005', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Tech Summit Vietnam 2026' AND tc.name='VIP'), 1, 1500000.0000),
  ('b0000000-0000-0000-0000-000000000005', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Tech Summit Vietnam 2026' AND tc.name='Standard'), 1, 600000.0000),
  ('b0000000-0000-0000-0000-000000000006', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Tech Summit Vietnam 2026' AND tc.name='Standard'), 2, 600000.0000),
  ('b0000000-0000-0000-0000-000000000007', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Tech Summit Vietnam 2026' AND tc.name='Economy'), 1, 250000.0000),
  ('b0000000-0000-0000-0000-000000000008', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Comedy Night Live May' AND tc.name='VIP'), 1, 450000.0000),
  ('b0000000-0000-0000-0000-000000000008', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Comedy Night Live May' AND tc.name='Standard'), 1, 200000.0000),
  ('b0000000-0000-0000-0000-000000000009', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Comedy Night Live May' AND tc.name='Standard'), 1, 200000.0000),
  ('b0000000-0000-0000-0000-000000000010', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Comedy Night Live May' AND tc.name='Economy'), 2, 80000.0000),
  ('b0000000-0000-0000-0000-000000000011', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='K-Pop Fanmeeting 2026' AND tc.name='Standard'), 1, 800000.0000),
  ('b0000000-0000-0000-0000-000000000012', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='K-Pop Fanmeeting 2026' AND tc.name='Economy'), 4, 350000.0000),
  ('b0000000-0000-0000-0000-000000000013', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='European Classical Night' AND tc.name='VIP'), 1, 1200000.0000),
  ('b0000000-0000-0000-0000-000000000013', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='European Classical Night' AND tc.name='Standard'), 1, 500000.0000),
  ('b0000000-0000-0000-0000-000000000014', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='European Classical Night' AND tc.name='Standard'), 1, 500000.0000),
  ('b0000000-0000-0000-0000-000000000015', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Food and Wine International Fest' AND tc.name='VIP'), 1, 1000000.0000),
  ('b0000000-0000-0000-0000-000000000015', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Food and Wine International Fest' AND tc.name='Standard'), 1, 400000.0000),
  ('b0000000-0000-0000-0000-000000000015', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Food and Wine International Fest' AND tc.name='Economy'), 1, 150000.0000),
  ('b0000000-0000-0000-0000-000000000016', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Food and Wine International Fest' AND tc.name='Standard'), 2, 400000.0000),
  ('b0000000-0000-0000-0000-000000000017', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Food and Wine International Fest' AND tc.name='Economy'), 2, 150000.0000),
  ('b0000000-0000-0000-0000-000000000018', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Hoi An Jazz and Blue Festival' AND tc.name='Economy'), 1, 150000.0000),
  ('b0000000-0000-0000-0000-000000000019', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Hoi An Jazz and Blue Festival' AND tc.name='VIP'), 1, 900000.0000),
  ('b0000000-0000-0000-0000-000000000019', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Hoi An Jazz and Blue Festival' AND tc.name='Standard'), 1, 350000.0000),
  ('b0000000-0000-0000-0000-000000000020', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Startup Pitch Day 2025' AND tc.name='Standard'), 1, 300000.0000),
  ('b0000000-0000-0000-0000-000000000021', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Startup Pitch Day 2025' AND tc.name='Economy'), 2, 100000.0000),
  ('b0000000-0000-0000-0000-000000000022', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Startup Pitch Day 2025' AND tc.name='Standard'), 1, 300000.0000),
  ('b0000000-0000-0000-0000-000000000023', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Rock Fest Hanoi 2026' AND tc.name='Standard'), 2, 280000.0000),
  ('b0000000-0000-0000-0000-000000000024', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Swan Lake Ballet by Bolshoi' AND tc.name='VIP'), 1, 1800000.0000),
  ('b0000000-0000-0000-0000-000000000024', (SELECT tc.id FROM ticket_classes tc JOIN events e ON e.id = tc.event_id WHERE e.name='Swan Lake Ballet by Bolshoi' AND tc.name='Economy'), 1, 300000.0000);

-- Recalculate quantity_sold from active bookings (exclude CANCELLED)
UPDATE ticket_classes tc
SET quantity_sold = COALESCE(src.sold, 0)
FROM (
  SELECT tbd.ticket_class_id, SUM(tbd.quantity) AS sold
  FROM ticket_booking_details tbd
  JOIN bookings b ON b.id = tbd.booking_id
  WHERE b.status <> 'CANCELLED'
  GROUP BY tbd.ticket_class_id
) src
WHERE src.ticket_class_id = tc.id;

UPDATE ticket_classes
SET quantity_sold = 0
WHERE id NOT IN (
  SELECT DISTINCT ticket_class_id FROM ticket_booking_details
);

-- Ensure sequences are aligned with inserted rows
SELECT setval(pg_get_serial_sequence('users', 'id'),          COALESCE((SELECT MAX(id) FROM users), 1), true);
SELECT setval(pg_get_serial_sequence('venues', 'id'),         COALESCE((SELECT MAX(id) FROM venues), 1), true);
SELECT setval(pg_get_serial_sequence('events', 'id'),         COALESCE((SELECT MAX(id) FROM events), 1), true);
SELECT setval(pg_get_serial_sequence('ticket_classes', 'id'), COALESCE((SELECT MAX(id) FROM ticket_classes), 1), true);
SELECT setval(pg_get_serial_sequence('ticket_booking_details', 'id'), COALESCE((SELECT MAX(id) FROM ticket_booking_details), 1), true);
