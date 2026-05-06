# TicketFlash

Hệ thống đặt vé sự kiện backend — Spring Boot 4, Java 21.

---

## Tech Stack

| | |
|---|---|
| Core | Java 21, Spring Boot 4.0.2 |
| Security | Spring Security + JWT (jjwt) |
| Database | PostgreSQL, H2 (test) |
| ORM | Spring Data JPA, Hibernate, QueryDSL |
| Migration | Flyway |
| Cache | Spring Cache |
| Messaging | RabbitMQ (Spring AMQP) |
| Rate Limit | Bucket4j + Redis (Lettuce) |
| Mapping | MapStruct |
| Infra | Docker Compose, Virtual Threads (Java 21) |

---

## Features

- **Auth** — Đăng ký / đăng nhập, JWT Access + Refresh Token, phân quyền ADMIN/USER
- **Venue** — CRUD địa điểm
- **Event** — Tạo event + ticket classes, tìm kiếm động (QueryDSL), phân trang
- **Booking** — Đặt nhiều loại vé, Pessimistic Lock tránh oversell, deadlock prevention
- **Payment** — Thanh toán booking, chuyển trạng thái PENDING → CONFIRMED
- **Auto-cancel** — RabbitMQ Dead Letter Exchange: booking chưa thanh toán sau 2 phút tự hoàn vé
- **Notification** — Spring Events + @Async: gửi email bất đồng bộ sau booking/payment
- **Rate Limiting** — Custom `@RateLimit` annotation + HandlerInterceptor + Redis Bucket4j, fail-open
- QR Code vé điện tử + endpoint check-in

---

## Kỹ thuật áp dụng

- Pessimistic Lock + sort ticket ID → tránh race condition & deadlock khi đặt vé đồng thời
- Dead Letter Exchange pattern → auto-expire booking
- `@EntityGraph` → giải quyết N+1 query
- Token bucket (Bucket4j) distributed trên Redis
- Virtual Threads (`spring.threads.virtual.enabled=true`)
- Layered architecture: Controller → Service (interface/impl) → Repository

---

## Phát triển tiếp

### Tính năng
- [ ] Refresh Token endpoint (thu hồi, rotate token)
- [ ] Waitlist: đăng ký chờ khi vé hết, tự động phân bổ khi có người huỷ

### Kỹ thuật / Công nghệ
- [ ] **Testcontainers** — integration test với PostgreSQL, Redis, RabbitMQ thật
- [ ] **Outbox Pattern** — đảm bảo gửi message RabbitMQ atomic với DB transaction
- [ ] **Idempotency Key** — tránh double-payment khi client retry
