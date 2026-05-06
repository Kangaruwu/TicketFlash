-- =============================================================
-- SEED DATA — TicketFlash
-- All user passwords are: password123
-- BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC
-- Today reference: 2026-05-06
-- Past events:  1 (VinFest, 2026-03-15), 3 (Comedy Night, 2026-05-01), 10 (Startup Pitch, 2025-12-01)
-- Future events: 2, 4, 5, 7, 8
-- Draft event:   6
-- Cancelled:     9
-- =============================================================

-- ---------------------------------------------------------------
-- USERS
-- ---------------------------------------------------------------
INSERT INTO users (username, full_name, password, email, role) VALUES
  ('admin',   'Admin TicketFlash',  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'admin@ticketflash.io',  'ADMIN'),
  ('alice',   'Alice Nguyễn',       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'alice@example.com',     'USER'),
  ('bob',     'Bob Trần',           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'bob@example.com',       'USER'),
  ('carol',   'Carol Lê',           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'carol@example.com',     'USER'),
  ('dave',    'Dave Phạm',          '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'dave@example.com',      'USER'),
  ('eve',     'Eve Hoàng',          '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'eve@example.com',       'USER'),
  ('frank',   'Frank Đặng',         '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'frank@example.com',     'USER'),
  ('grace',   'Grace Vũ',           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'grace@example.com',     'USER'),
  ('henry',   'Henry Đinh',         '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'henry@example.com',     'USER'),
  ('iris',    'Iris Bùi',           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'iris@example.com',      'USER'),
  ('staff1',  'Staff Kiểm Tra 1',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'staff1@ticketflash.io', 'ADMIN'),
  ('staff2',  'Staff Kiểm Tra 2',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LfEVrbMSELC', 'staff2@ticketflash.io', 'ADMIN');

-- ---------------------------------------------------------------
-- VENUES
-- ---------------------------------------------------------------
INSERT INTO venues (name, address, capacity) VALUES
  ('Trung tâm Hội nghị Quốc gia',         '1 Đường Phạm Hùng, Mỹ Đình, Hà Nội',             50000),
  ('Nhà hát Lớn Hà Nội',                  '1 Tràng Tiền, Hoàn Kiếm, Hà Nội',                  900),
  ('GEM Center Sài Gòn',                   '8 Nguyễn Bỉnh Khiêm, Đa Kao, Q.1, TP.HCM',        3000),
  ('Nhà thi đấu Hòa Xuân',                'Lô 1 Hòa Xuân, Cẩm Lệ, Đà Nẵng',                  8000),
  ('Trung tâm Văn hóa Phố Cổ Hội An',    '33 Trần Phú, Hội An, Quảng Nam',                    500),
  ('White Palace Convention Center',       '194 Hoàng Văn Thụ, Phú Nhuận, TP.HCM',            5000),
  ('Đà Lạt Dragon Arena',                  'Khu du lịch Quảng Trường Lâm Viên, Đà Lạt',        6000),
  ('Cung Hữu Nghị Việt–Xô',               '91 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội',             1200);

-- ---------------------------------------------------------------
-- EVENTS
-- ---------------------------------------------------------------
INSERT INTO events (name, description, start_time, end_time, status, venue_id) VALUES
  -- 1: PAST — VinFest 2026
  ('VinFest Music 2026',
   'Lễ hội âm nhạc ngoài trời lớn nhất năm với hàng chục nghệ sĩ trong nước và quốc tế.',
   '2026-03-15 17:00:00', '2026-03-15 23:00:00', 'PUBLISHED', 1),

  -- 2: FUTURE — Tech Summit
  ('Tech Summit Vietnam 2026',
   'Hội nghị công nghệ hàng đầu Đông Nam Á: AI, Cloud, Blockchain và hơn thế nữa.',
   '2026-07-10 08:30:00', '2026-07-11 18:00:00', 'PUBLISHED', 1),

  -- 3: PAST — Comedy Night
  ('Comedy Night Live – Tháng 5',
   'Đêm hài độc thoại cùng các danh hài nổi tiếng: Trấn Thành, Hoài Linh, Xuân Hinh.',
   '2026-05-01 19:30:00', '2026-05-01 22:00:00', 'PUBLISHED', 3),

  -- 4: FUTURE — K-Pop
  ('K-Pop Fanmeeting 2026',
   'Gặp gỡ idol Hàn Quốc: SEVENTEEN, aespa & nhiều nhóm nhạc đình đám.',
   '2026-08-05 18:00:00', '2026-08-05 22:30:00', 'PUBLISHED', 4),

  -- 5: FUTURE — Classical
  ('Đêm Nhạc Cổ Điển Châu Âu',
   'Dàn nhạc giao hưởng Vienna biểu diễn các tác phẩm Beethoven, Mozart, Chopin.',
   '2026-06-28 20:00:00', '2026-06-28 22:30:00', 'PUBLISHED', 5),

  -- 6: DRAFT — Gaming Expo
  ('Vietnam Gaming Expo 2026',
   'Triển lãm game lớn nhất Việt Nam: esports, thực tế ảo, ra mắt tựa game mới.',
   '2026-10-15 09:00:00', '2026-10-17 20:00:00', 'DRAFT', 6),

  -- 7: FUTURE — Food & Wine
  ('Lễ hội Ẩm thực & Rượu Vang Quốc tế',
   'Hơn 200 gian hàng ẩm thực từ 30 quốc gia, workshop nấu ăn, degustation rượu vang.',
   '2026-09-05 10:00:00', '2026-09-07 22:00:00', 'PUBLISHED', 6),

  -- 8: FUTURE — Jazz
  ('Hội An Jazz & Blue Festival',
   'Liên hoan nhạc Jazz quốc tế tại phố cổ Hội An dưới ánh đèn lồng lung linh.',
   '2026-07-25 19:00:00', '2026-07-27 23:00:00', 'PUBLISHED', 5),

  -- 9: CANCELLED — EDM Night
  ('EDM Night Da Nang',
   'Đêm nhạc điện tử bùng cháy với DJ quốc tế. (Đã hủy do điều kiện thời tiết.)',
   '2026-07-20 20:00:00', '2026-07-21 02:00:00', 'CANCELLED', 4),

  -- 10: PAST — Startup Pitch
  ('Startup Pitch Day 2025',
   'Ngày hội khởi nghiệp: 50 startup trình bày trước hội đồng đầu tư 500 tỷ đồng.',
   '2025-12-01 08:00:00', '2025-12-01 18:00:00', 'PUBLISHED', 3),

  -- 11: FUTURE — Rock Fest
  ('Rock Fest Hanoi 2026',
   'Festival rock ngoài trời: Metallica tribute, Bức Tường, Da LAB, Ngũ Cung.',
   '2026-11-20 16:00:00', '2026-11-20 23:59:00', 'PUBLISHED', 1),

  -- 12: FUTURE — Ballet
  ('Ballet Thiên Nga Hồ – Bolshoi Theatre',
   'Ballet Hồ Thiên Nga kinh điển do đoàn múa Bolshoi Theatre biểu diễn.',
   '2026-06-14 19:30:00', '2026-06-14 22:00:00', 'PUBLISHED', 2);

-- ---------------------------------------------------------------
-- TICKET CLASSES
-- (3 hạng vé per event: VIP / Standard / Economy)
-- ---------------------------------------------------------------
INSERT INTO ticket_classes (event_id, name, price, quantity_available, quantity_sold) VALUES
  -- Event 1: VinFest (past)
  (1,  'VIP',      500000.0000,   200,  0),   -- tc_id=1
  (1,  'Standard', 200000.0000,   800,  0),   -- tc_id=2
  (1,  'Economy',  100000.0000,  1500,  0),   -- tc_id=3

  -- Event 2: Tech Summit (future)
  (2,  'VIP',     1500000.0000,   100,  0),   -- tc_id=4
  (2,  'Standard', 600000.0000,   400,  0),   -- tc_id=5
  (2,  'Economy',  250000.0000,   800,  0),   -- tc_id=6

  -- Event 3: Comedy Night (past)
  (3,  'VIP',      450000.0000,   100,  0),   -- tc_id=7
  (3,  'Standard', 200000.0000,   300,  0),   -- tc_id=8
  (3,  'Economy',   80000.0000,   500,  0),   -- tc_id=9

  -- Event 4: K-Pop (future)
  (4,  'VIP',     2000000.0000,   150,  0),   -- tc_id=10
  (4,  'Standard', 800000.0000,   500,  0),   -- tc_id=11
  (4,  'Economy',  350000.0000,  1000,  0),   -- tc_id=12

  -- Event 5: Classical (future)
  (5,  'VIP',     1200000.0000,    50,  0),   -- tc_id=13
  (5,  'Standard', 500000.0000,   150,  0),   -- tc_id=14
  (5,  'Economy',  200000.0000,   300,  0),   -- tc_id=15

  -- Event 6: Gaming Expo (DRAFT)
  (6,  'VIP',      600000.0000,   100,  0),   -- tc_id=16
  (6,  'Standard', 250000.0000,   300,  0),   -- tc_id=17
  (6,  'Economy',  120000.0000,   600,  0),   -- tc_id=18

  -- Event 7: Food & Wine (future)
  (7,  'VIP',     1000000.0000,   120,  0),   -- tc_id=19
  (7,  'Standard', 400000.0000,   400,  0),   -- tc_id=20
  (7,  'Economy',  150000.0000,   800,  0),   -- tc_id=21

  -- Event 8: Jazz (future)
  (8,  'VIP',      900000.0000,    80,  0),   -- tc_id=22
  (8,  'Standard', 350000.0000,   250,  0),   -- tc_id=23
  (8,  'Economy',  150000.0000,   500,  0),   -- tc_id=24

  -- Event 9: EDM (CANCELLED)
  (9,  'VIP',     1100000.0000,   200,  0),   -- tc_id=25
  (9,  'Standard', 450000.0000,   600,  0),   -- tc_id=26
  (9,  'Economy',  200000.0000,  1200,  0),   -- tc_id=27

  -- Event 10: Startup Pitch (past)
  (10, 'VIP',      800000.0000,    50,  0),   -- tc_id=28
  (10, 'Standard', 300000.0000,   200,  0),   -- tc_id=29
  (10, 'Economy',  100000.0000,   400,  0),   -- tc_id=30

  -- Event 11: Rock Fest (future)
  (11, 'VIP',      700000.0000,   300,  0),   -- tc_id=31
  (11, 'Standard', 280000.0000,   800,  0),   -- tc_id=32
  (11, 'Economy',  130000.0000,  1500,  0),   -- tc_id=33

  -- Event 12: Ballet (future)
  (12, 'VIP',     1800000.0000,    60,  0),   -- tc_id=34
  (12, 'Standard', 700000.0000,   180,  0),   -- tc_id=35
  (12, 'Economy',  300000.0000,   400,  0);   -- tc_id=36

-- ---------------------------------------------------------------
-- BOOKINGS
-- Explicit UUIDs for referencing in ticket_booking_details
-- Past-event bookings: checked_in may be TRUE (if CONFIRMED)
-- ---------------------------------------------------------------
INSERT INTO bookings (id, event_id, email, booking_date, total_ticket, status, qr_token, checked_in, checked_in_at) VALUES
  -- ===== Event 1 — VinFest (PAST 2026-03-15) =====
  -- B01: alice — CONFIRMED, checked-in
  ('b0000000-0000-0000-0000-000000000001', 1, 'alice@example.com',
   '2026-02-10 09:00:00', 3, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000001', TRUE,  '2026-03-15 16:45:00'),

  -- B02: bob — CONFIRMED, checked-in
  ('b0000000-0000-0000-0000-000000000002', 1, 'bob@example.com',
   '2026-02-11 11:00:00', 3, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000002', TRUE,  '2026-03-15 17:10:00'),

  -- B03: carol — CANCELLED
  ('b0000000-0000-0000-0000-000000000003', 1, 'carol@example.com',
   '2026-02-12 14:00:00', 2, 'CANCELLED',
   'a0000000-0000-0000-0000-000000000003', FALSE, NULL),

  -- B04: dave — CONFIRMED, checked-in
  ('b0000000-0000-0000-0000-000000000004', 1, 'dave@example.com',
   '2026-02-13 10:30:00', 4, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000004', TRUE,  '2026-03-15 17:30:00'),

  -- B05: eve — CONFIRMED, NOT checked-in (arrived late, missed check-in)
  ('b0000000-0000-0000-0000-000000000005', 1, 'eve@example.com',
   '2026-02-14 08:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000005', FALSE, NULL),

  -- B06: frank — CONFIRMED, checked-in
  ('b0000000-0000-0000-0000-000000000006', 1, 'frank@example.com',
   '2026-02-15 15:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000006', TRUE,  '2026-03-15 17:05:00'),

  -- B07: grace — CONFIRMED, checked-in
  ('b0000000-0000-0000-0000-000000000007', 1, 'grace@example.com',
   '2026-02-16 12:00:00', 5, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000007', TRUE,  '2026-03-15 16:55:00'),

  -- B08: henry — CANCELLED
  ('b0000000-0000-0000-0000-000000000008', 1, 'henry@example.com',
   '2026-02-17 09:00:00', 1, 'CANCELLED',
   'a0000000-0000-0000-0000-000000000008', FALSE, NULL),

  -- ===== Event 2 — Tech Summit (FUTURE 2026-07-10) =====
  -- B09: alice — CONFIRMED
  ('b0000000-0000-0000-0000-000000000009', 2, 'alice@example.com',
   '2026-04-01 10:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000009', FALSE, NULL),

  -- B10: bob — PENDING
  ('b0000000-0000-0000-0000-000000000010', 2, 'bob@example.com',
   '2026-04-02 11:00:00', 2, 'PENDING',
   'a0000000-0000-0000-0000-000000000010', FALSE, NULL),

  -- B11: carol — CONFIRMED
  ('b0000000-0000-0000-0000-000000000011', 2, 'carol@example.com',
   '2026-04-03 09:30:00', 1, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000011', FALSE, NULL),

  -- B12: iris — PENDING
  ('b0000000-0000-0000-0000-000000000012', 2, 'iris@example.com',
   '2026-04-04 14:00:00', 3, 'PENDING',
   'a0000000-0000-0000-0000-000000000012', FALSE, NULL),

  -- B13: henry — CANCELLED
  ('b0000000-0000-0000-0000-000000000013', 2, 'henry@example.com',
   '2026-04-05 08:00:00', 1, 'CANCELLED',
   'a0000000-0000-0000-0000-000000000013', FALSE, NULL),

  -- ===== Event 3 — Comedy Night (PAST 2026-05-01) =====
  -- B14: bob — CONFIRMED, checked-in
  ('b0000000-0000-0000-0000-000000000014', 3, 'bob@example.com',
   '2026-03-20 10:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000014', TRUE,  '2026-05-01 19:20:00'),

  -- B15: carol — CONFIRMED, checked-in
  ('b0000000-0000-0000-0000-000000000015', 3, 'carol@example.com',
   '2026-03-21 11:00:00', 1, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000015', TRUE,  '2026-05-01 19:15:00'),

  -- B16: dave — CONFIRMED, NOT checked-in
  ('b0000000-0000-0000-0000-000000000016', 3, 'dave@example.com',
   '2026-03-22 12:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000016', FALSE, NULL),

  -- B17: frank — CANCELLED
  ('b0000000-0000-0000-0000-000000000017', 3, 'frank@example.com',
   '2026-03-23 09:00:00', 3, 'CANCELLED',
   'a0000000-0000-0000-0000-000000000017', FALSE, NULL),

  -- ===== Event 4 — K-Pop (FUTURE 2026-08-05) =====
  -- B18: alice — CONFIRMED
  ('b0000000-0000-0000-0000-000000000018', 4, 'alice@example.com',
   '2026-04-10 10:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000018', FALSE, NULL),

  -- B19: eve — CONFIRMED
  ('b0000000-0000-0000-0000-000000000019', 4, 'eve@example.com',
   '2026-04-11 11:00:00', 1, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000019', FALSE, NULL),

  -- B20: grace — PENDING
  ('b0000000-0000-0000-0000-000000000020', 4, 'grace@example.com',
   '2026-04-12 12:00:00', 4, 'PENDING',
   'a0000000-0000-0000-0000-000000000020', FALSE, NULL),

  -- B21: iris — CONFIRMED
  ('b0000000-0000-0000-0000-000000000021', 4, 'iris@example.com',
   '2026-04-13 13:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000021', FALSE, NULL),

  -- ===== Event 5 — Classical (FUTURE 2026-06-28) =====
  -- B22: carol — CONFIRMED
  ('b0000000-0000-0000-0000-000000000022', 5, 'carol@example.com',
   '2026-04-20 10:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000022', FALSE, NULL),

  -- B23: dave — PENDING
  ('b0000000-0000-0000-0000-000000000023', 5, 'dave@example.com',
   '2026-04-21 11:00:00', 1, 'PENDING',
   'a0000000-0000-0000-0000-000000000023', FALSE, NULL),

  -- B24: eve — CANCELLED
  ('b0000000-0000-0000-0000-000000000024', 5, 'eve@example.com',
   '2026-04-22 09:00:00', 2, 'CANCELLED',
   'a0000000-0000-0000-0000-000000000024', FALSE, NULL),

  -- ===== Event 7 — Food & Wine (FUTURE 2026-09-05) =====
  -- B25: frank — CONFIRMED
  ('b0000000-0000-0000-0000-000000000025', 7, 'frank@example.com',
   '2026-05-01 10:00:00', 3, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000025', FALSE, NULL),

  -- B26: grace — CONFIRMED
  ('b0000000-0000-0000-0000-000000000026', 7, 'grace@example.com',
   '2026-05-02 11:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000026', FALSE, NULL),

  -- B27: henry — PENDING
  ('b0000000-0000-0000-0000-000000000027', 7, 'henry@example.com',
   '2026-05-03 12:00:00', 2, 'PENDING',
   'a0000000-0000-0000-0000-000000000027', FALSE, NULL),

  -- B28: iris — PENDING
  ('b0000000-0000-0000-0000-000000000028', 7, 'iris@example.com',
   '2026-05-04 13:00:00', 1, 'PENDING',
   'a0000000-0000-0000-0000-000000000028', FALSE, NULL),

  -- ===== Event 8 — Jazz (FUTURE 2026-07-25) =====
  -- B29: alice — PENDING
  ('b0000000-0000-0000-0000-000000000029', 8, 'alice@example.com',
   '2026-05-01 09:00:00', 1, 'PENDING',
   'a0000000-0000-0000-0000-000000000029', FALSE, NULL),

  -- B30: bob — CONFIRMED
  ('b0000000-0000-0000-0000-000000000030', 8, 'bob@example.com',
   '2026-05-02 10:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000030', FALSE, NULL),

  -- B31: henry — CONFIRMED
  ('b0000000-0000-0000-0000-000000000031', 8, 'henry@example.com',
   '2026-05-03 11:00:00', 1, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000031', FALSE, NULL),

  -- ===== Event 10 — Startup Pitch (PAST 2025-12-01) =====
  -- B32: alice — CONFIRMED, checked-in
  ('b0000000-0000-0000-0000-000000000032', 10, 'alice@example.com',
   '2025-11-01 10:00:00', 1, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000032', TRUE,  '2025-12-01 08:05:00'),

  -- B33: bob — CONFIRMED, checked-in
  ('b0000000-0000-0000-0000-000000000033', 10, 'bob@example.com',
   '2025-11-02 11:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000033', TRUE,  '2025-12-01 08:10:00'),

  -- B34: carol — CONFIRMED, NOT checked-in
  ('b0000000-0000-0000-0000-000000000034', 10, 'carol@example.com',
   '2025-11-03 12:00:00', 1, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000034', FALSE, NULL),

  -- B35: dave — CANCELLED
  ('b0000000-0000-0000-0000-000000000035', 10, 'dave@example.com',
   '2025-11-04 09:00:00', 2, 'CANCELLED',
   'a0000000-0000-0000-0000-000000000035', FALSE, NULL),

  -- ===== Event 11 — Rock Fest (FUTURE 2026-11-20) =====
  -- B36: eve — CONFIRMED
  ('b0000000-0000-0000-0000-000000000036', 11, 'eve@example.com',
   '2026-05-01 10:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000036', FALSE, NULL),

  -- B37: frank — PENDING
  ('b0000000-0000-0000-0000-000000000037', 11, 'frank@example.com',
   '2026-05-02 11:00:00', 3, 'PENDING',
   'a0000000-0000-0000-0000-000000000037', FALSE, NULL),

  -- B38: grace — CONFIRMED
  ('b0000000-0000-0000-0000-000000000038', 11, 'grace@example.com',
   '2026-05-03 12:00:00', 4, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000038', FALSE, NULL),

  -- ===== Event 12 — Ballet (FUTURE 2026-06-14) =====
  -- B39: iris — CONFIRMED
  ('b0000000-0000-0000-0000-000000000039', 12, 'iris@example.com',
   '2026-05-01 10:00:00', 2, 'CONFIRMED',
   'a0000000-0000-0000-0000-000000000039', FALSE, NULL),

  -- B40: henry — PENDING
  ('b0000000-0000-0000-0000-000000000040', 12, 'henry@example.com',
   '2026-05-02 11:00:00', 1, 'PENDING',
   'a0000000-0000-0000-0000-000000000040', FALSE, NULL);

-- ---------------------------------------------------------------
-- TICKET BOOKING DETAILS
-- ---------------------------------------------------------------
INSERT INTO ticket_booking_details (booking_id, ticket_class_id, quantity, price) VALUES
  -- B01: alice, Event 1 — VIP x1 + Standard x2
  ('b0000000-0000-0000-0000-000000000001', 1, 1, 500000.0000),
  ('b0000000-0000-0000-0000-000000000001', 2, 2, 200000.0000),

  -- B02: bob, Event 1 — Standard x3
  ('b0000000-0000-0000-0000-000000000002', 2, 3, 200000.0000),

  -- B03: carol, Event 1 — Economy x2 (CANCELLED)
  ('b0000000-0000-0000-0000-000000000003', 3, 2, 100000.0000),

  -- B04: dave, Event 1 — Standard x2 + Economy x2
  ('b0000000-0000-0000-0000-000000000004', 2, 2, 200000.0000),
  ('b0000000-0000-0000-0000-000000000004', 3, 2, 100000.0000),

  -- B05: eve, Event 1 — VIP x2
  ('b0000000-0000-0000-0000-000000000005', 1, 2, 500000.0000),

  -- B06: frank, Event 1 — Standard x2
  ('b0000000-0000-0000-0000-000000000006', 2, 2, 200000.0000),

  -- B07: grace, Event 1 — VIP x1 + Standard x2 + Economy x2
  ('b0000000-0000-0000-0000-000000000007', 1, 1, 500000.0000),
  ('b0000000-0000-0000-0000-000000000007', 2, 2, 200000.0000),
  ('b0000000-0000-0000-0000-000000000007', 3, 2, 100000.0000),

  -- B08: henry, Event 1 — Economy x1 (CANCELLED)
  ('b0000000-0000-0000-0000-000000000008', 3, 1, 100000.0000),

  -- B09: alice, Event 2 — VIP x1 + Standard x1
  ('b0000000-0000-0000-0000-000000000009', 4, 1, 1500000.0000),
  ('b0000000-0000-0000-0000-000000000009', 5, 1,  600000.0000),

  -- B10: bob, Event 2 — Standard x2 (PENDING)
  ('b0000000-0000-0000-0000-000000000010', 5, 2, 600000.0000),

  -- B11: carol, Event 2 — Economy x1
  ('b0000000-0000-0000-0000-000000000011', 6, 1, 250000.0000),

  -- B12: iris, Event 2 — Standard x1 + Economy x2 (PENDING)
  ('b0000000-0000-0000-0000-000000000012', 5, 1, 600000.0000),
  ('b0000000-0000-0000-0000-000000000012', 6, 2, 250000.0000),

  -- B13: henry, Event 2 — Standard x1 (CANCELLED)
  ('b0000000-0000-0000-0000-000000000013', 5, 1, 600000.0000),

  -- B14: bob, Event 3 — VIP x1 + Standard x1
  ('b0000000-0000-0000-0000-000000000014', 7, 1, 450000.0000),
  ('b0000000-0000-0000-0000-000000000014', 8, 1, 200000.0000),

  -- B15: carol, Event 3 — Standard x1
  ('b0000000-0000-0000-0000-000000000015', 8, 1, 200000.0000),

  -- B16: dave, Event 3 — Economy x2
  ('b0000000-0000-0000-0000-000000000016', 9, 2, 80000.0000),

  -- B17: frank, Event 3 — VIP x1 + Economy x2 (CANCELLED)
  ('b0000000-0000-0000-0000-000000000017', 7, 1, 450000.0000),
  ('b0000000-0000-0000-0000-000000000017', 9, 2,  80000.0000),

  -- B18: alice, Event 4 — VIP x2
  ('b0000000-0000-0000-0000-000000000018', 10, 2, 2000000.0000),

  -- B19: eve, Event 4 — Standard x1
  ('b0000000-0000-0000-0000-000000000019', 11, 1, 800000.0000),

  -- B20: grace, Event 4 — Economy x4 (PENDING)
  ('b0000000-0000-0000-0000-000000000020', 12, 4, 350000.0000),

  -- B21: iris, Event 4 — VIP x1 + Economy x1
  ('b0000000-0000-0000-0000-000000000021', 10, 1, 2000000.0000),
  ('b0000000-0000-0000-0000-000000000021', 12, 1,  350000.0000),

  -- B22: carol, Event 5 — VIP x1 + Standard x1
  ('b0000000-0000-0000-0000-000000000022', 13, 1, 1200000.0000),
  ('b0000000-0000-0000-0000-000000000022', 14, 1,  500000.0000),

  -- B23: dave, Event 5 — Standard x1 (PENDING)
  ('b0000000-0000-0000-0000-000000000023', 14, 1, 500000.0000),

  -- B24: eve, Event 5 — Economy x2 (CANCELLED)
  ('b0000000-0000-0000-0000-000000000024', 15, 2, 200000.0000),

  -- B25: frank, Event 7 — VIP x1 + Standard x1 + Economy x1
  ('b0000000-0000-0000-0000-000000000025', 19, 1, 1000000.0000),
  ('b0000000-0000-0000-0000-000000000025', 20, 1,  400000.0000),
  ('b0000000-0000-0000-0000-000000000025', 21, 1,  150000.0000),

  -- B26: grace, Event 7 — Standard x2
  ('b0000000-0000-0000-0000-000000000026', 20, 2, 400000.0000),

  -- B27: henry, Event 7 — Economy x2 (PENDING)
  ('b0000000-0000-0000-0000-000000000027', 21, 2, 150000.0000),

  -- B28: iris, Event 7 — Economy x1 (PENDING)
  ('b0000000-0000-0000-0000-000000000028', 21, 1, 150000.0000),

  -- B29: alice, Event 8 — Economy x1 (PENDING)
  ('b0000000-0000-0000-0000-000000000029', 24, 1, 150000.0000),

  -- B30: bob, Event 8 — VIP x1 + Standard x1
  ('b0000000-0000-0000-0000-000000000030', 22, 1, 900000.0000),
  ('b0000000-0000-0000-0000-000000000030', 23, 1, 350000.0000),

  -- B31: henry, Event 8 — Standard x1
  ('b0000000-0000-0000-0000-000000000031', 23, 1, 350000.0000),

  -- B32: alice, Event 10 — Standard x1
  ('b0000000-0000-0000-0000-000000000032', 29, 1, 300000.0000),

  -- B33: bob, Event 10 — Economy x2
  ('b0000000-0000-0000-0000-000000000033', 30, 2, 100000.0000),

  -- B34: carol, Event 10 — Standard x1
  ('b0000000-0000-0000-0000-000000000034', 29, 1, 300000.0000),

  -- B35: dave, Event 10 — Economy x2 (CANCELLED)
  ('b0000000-0000-0000-0000-000000000035', 30, 2, 100000.0000),

  -- B36: eve, Event 11 — Standard x2
  ('b0000000-0000-0000-0000-000000000036', 32, 2, 280000.0000),

  -- B37: frank, Event 11 — Economy x3 (PENDING)
  ('b0000000-0000-0000-0000-000000000037', 33, 3, 130000.0000),

  -- B38: grace, Event 11 — VIP x1 + Standard x2 + Economy x1
  ('b0000000-0000-0000-0000-000000000038', 31, 1, 700000.0000),
  ('b0000000-0000-0000-0000-000000000038', 32, 2, 280000.0000),
  ('b0000000-0000-0000-0000-000000000038', 33, 1, 130000.0000),

  -- B39: iris, Event 12 — VIP x1 + Economy x1
  ('b0000000-0000-0000-0000-000000000039', 34, 1, 1800000.0000),
  ('b0000000-0000-0000-0000-000000000039', 36, 1,  300000.0000),

  -- B40: henry, Event 12 — Standard x1 (PENDING)
  ('b0000000-0000-0000-0000-000000000040', 35, 1, 700000.0000);

-- ---------------------------------------------------------------
-- UPDATE quantity_sold to match active (non-CANCELLED) bookings
-- ---------------------------------------------------------------
-- tc_id=1  (Event1 VIP):      B01(1) + B05(2) + B07(1) = 4
-- tc_id=2  (Event1 Standard): B01(2) + B02(3) + B04(2) + B06(2) + B07(2) = 11
-- tc_id=3  (Event1 Economy):  B04(2) + B07(2) = 4
-- tc_id=4  (Event2 VIP):      B09(1) = 1
-- tc_id=5  (Event2 Standard): B09(1) + B10(2) + B12(1) = 4  (B13 cancelled)
-- tc_id=6  (Event2 Economy):  B11(1) + B12(2) = 3
-- tc_id=7  (Event3 VIP):      B14(1) = 1               (B17 cancelled)
-- tc_id=8  (Event3 Standard): B14(1) + B15(1) = 2
-- tc_id=9  (Event3 Economy):  B16(2) = 2               (B17 cancelled)
-- tc_id=10 (Event4 VIP):      B18(2) + B21(1) = 3
-- tc_id=11 (Event4 Standard): B19(1) = 1
-- tc_id=12 (Event4 Economy):  B20(4) + B21(1) = 5
-- tc_id=13 (Event5 VIP):      B22(1) = 1
-- tc_id=14 (Event5 Standard): B22(1) + B23(1) = 2
-- tc_id=15 (Event5 Economy):  0                         (B24 cancelled)
-- tc_id=19 (Event7 VIP):      B25(1) = 1
-- tc_id=20 (Event7 Standard): B25(1) + B26(2) = 3
-- tc_id=21 (Event7 Economy):  B25(1) + B27(2) + B28(1) = 4
-- tc_id=22 (Event8 VIP):      B30(1) = 1
-- tc_id=23 (Event8 Standard): B30(1) + B31(1) = 2
-- tc_id=24 (Event8 Economy):  B29(1) = 1
-- tc_id=29 (Event10 Standard):B32(1) + B34(1) = 2
-- tc_id=30 (Event10 Economy): B33(2) = 2                (B35 cancelled)
-- tc_id=31 (Event11 VIP):     B38(1) = 1
-- tc_id=32 (Event11 Standard):B36(2) + B38(2) = 4
-- tc_id=33 (Event11 Economy): B37(3) + B38(1) = 4
-- tc_id=34 (Event12 VIP):     B39(1) = 1
-- tc_id=35 (Event12 Standard):B40(1) = 1
-- tc_id=36 (Event12 Economy): B39(1) = 1

UPDATE ticket_classes SET quantity_sold = 4  WHERE id = 1;
UPDATE ticket_classes SET quantity_sold = 11 WHERE id = 2;
UPDATE ticket_classes SET quantity_sold = 4  WHERE id = 3;
UPDATE ticket_classes SET quantity_sold = 1  WHERE id = 4;
UPDATE ticket_classes SET quantity_sold = 4  WHERE id = 5;
UPDATE ticket_classes SET quantity_sold = 3  WHERE id = 6;
UPDATE ticket_classes SET quantity_sold = 1  WHERE id = 7;
UPDATE ticket_classes SET quantity_sold = 2  WHERE id = 8;
UPDATE ticket_classes SET quantity_sold = 2  WHERE id = 9;
UPDATE ticket_classes SET quantity_sold = 3  WHERE id = 10;
UPDATE ticket_classes SET quantity_sold = 1  WHERE id = 11;
UPDATE ticket_classes SET quantity_sold = 5  WHERE id = 12;
UPDATE ticket_classes SET quantity_sold = 1  WHERE id = 13;
UPDATE ticket_classes SET quantity_sold = 2  WHERE id = 14;
UPDATE ticket_classes SET quantity_sold = 1  WHERE id = 19;
UPDATE ticket_classes SET quantity_sold = 3  WHERE id = 20;
UPDATE ticket_classes SET quantity_sold = 4  WHERE id = 21;
UPDATE ticket_classes SET quantity_sold = 1  WHERE id = 22;
UPDATE ticket_classes SET quantity_sold = 2  WHERE id = 23;
UPDATE ticket_classes SET quantity_sold = 1  WHERE id = 24;
UPDATE ticket_classes SET quantity_sold = 2  WHERE id = 29;
UPDATE ticket_classes SET quantity_sold = 2  WHERE id = 30;
UPDATE ticket_classes SET quantity_sold = 1  WHERE id = 31;
UPDATE ticket_classes SET quantity_sold = 4  WHERE id = 32;
UPDATE ticket_classes SET quantity_sold = 4  WHERE id = 33;
UPDATE ticket_classes SET quantity_sold = 1  WHERE id = 34;
UPDATE ticket_classes SET quantity_sold = 1  WHERE id = 35;
UPDATE ticket_classes SET quantity_sold = 1  WHERE id = 36;
