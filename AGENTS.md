# TicketFlash Backend — Agent Instructions

Spring Boot 4 / Java 21 event ticketing backend. See [README.md](README.md) for feature overview and tech stack.

## Build & Test

```bash
# Build
./mvnw clean package

# Run all tests (H2 in-memory, no external deps needed)
./mvnw test

# Run specific test class
./mvnw test -Dtest=TicketConcurrencyTest

# Start full local stack (postgres, redis, rabbitmq, pgadmin)
docker compose up -d
```

> `TicketConcurrencyTest` is `@Disabled` by default — enable it explicitly to run concurrency assertions.

## Architecture

```
Controller → Service (interface + impl) → Repository → Entity
                 ↕
           Event pub/sub (Spring Events + @Async)
           RabbitMQ producer/consumer (booking expiry)
```

- **Controllers**: [`src/main/java/com/barox/ticketflash/controller/`](src/main/java/com/barox/ticketflash/controller/)
- **Service impls**: [`service/impl/`](src/main/java/com/barox/ticketflash/service/impl/)
- **Entities** (5): `User`, `Event`, `Venue`, `TicketClass`, `Booking` + `BookingDetails` join
- **Config**: [`config/`](src/main/java/com/barox/ticketflash/config/) — `SecurityConfig`, `RabbitMQConfig`, `RedisConfig`, `ApiConfig`
- **API prefix**: all routes are under `/api/v1` (registered in `ApiConfig`)

## Key Conventions

### DTOs & Mapping
- Always use DTOs — never expose entities in responses.
- Request DTOs live in `dto/request/`, response DTOs in `dto/response/`.
- Mapping via **MapStruct** (`@Mapper(componentModel = "spring")`). Mappers are in [`mapper/`](src/main/java/com/barox/ticketflash/mapper/).
- Ignore service-set fields with `@Mapping(target = "id", ignore = true)`.
- Flatten nested entity fields: `@Mapping(source = "event.name", target = "eventName")`.

### Exception Handling
- Throw `DataNotFoundException` for 404s, other `RuntimeException` subclasses for 400s.
- [`GlobalExceptionHandler`](src/main/java/com/barox/ticketflash/exception/GlobalExceptionHandler.java) (`@RestControllerAdvice`) maps all exceptions to `ErrorResponse`.

### Rate Limiting
- Annotate controller methods with [`@RateLimit`](src/main/java/com/barox/ticketflash/annotation/RateLimit.java).
- Only works on controller methods (interceptor checks `HandlerMethod`). Does NOT work on services.
- Fail-open: if Redis is unavailable, all requests are allowed.
- Key format: `rl:{clientIP}:{requestURI}` stored in Redis via Bucket4j.

### Security
- JWT Bearer tokens. Filter: [`JwtAuthenticationFilter`](src/main/java/com/barox/ticketflash/security/JwtAuthenticationFilter.java).
- Roles: `ADMIN`, `USER`. Use `@PreAuthorize("hasAuthority('ADMIN')")` in controllers.
- Public endpoints are whitelisted in [`SecurityConfig`](src/main/java/com/barox/ticketflash/config/SecurityConfig.java).

### Bidirectional Relationships
- Always use helper methods (e.g., `event.addTicketClass(tc)`) to set both sides.
- Add `@ToString.Exclude` on circular references to prevent stack overflows.

## Critical Patterns

### Concurrency / Booking
- Ticket quantity is protected with `@Lock(PESSIMISTIC_WRITE)` in `TicketClassRepository`.
- **Always sort ticket IDs** before locking to prevent deadlocks between concurrent bookings.
- Core logic: [`BookingServiceImpl`](src/main/java/com/barox/ticketflash/service/impl/BookingServiceImpl.java).

### Auto-Cancel (Dead Letter Exchange)
- On booking creation, a message is sent to `booking.queue` with a **2-minute TTL**.
- Expired messages → DLX queue → [`DLXConsumer`](src/main/java/com/barox/ticketflash/service/listener_consumer/DLXConsumer.java) cancels PENDING bookings and restores ticket quantities.
- DLX topology declared in [`RabbitMQConfig`](src/main/java/com/barox/ticketflash/config/RabbitMQConfig.java).

### Async Notifications
- Post-booking and post-payment emails use Spring's `ApplicationEventPublisher` + `@Async`.
- Event classes live in [`event/`](src/main/java/com/barox/ticketflash/event/), consumers in `service/listener_consumer/`.

## Database

- **Migrations**: Flyway, files in [`src/main/resources/db/migration/`](src/main/resources/db/migration/).
- Never modify existing migration files — always add a new `V{n}__description.sql`.
- Booking IDs: UUID (`gen_random_uuid()`). Ticket/Event IDs: BIGSERIAL.

## Environment

All secrets and connection details come from environment variables. Copy `.env.example` → `.env` before starting with Docker Compose. Required variables:

| Variable | Purpose |
|---|---|
| `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` | PostgreSQL |
| `JWT_SECRET_KEY`, `JWT_EXPIRATION_TIME` | JWT signing |
| `RABBITMQ_HOST`, `RABBITMQ_PORT`, `RABBITMQ_DEFAULT_USER`, `RABBITMQ_DEFAULT_PASS` | RabbitMQ |
| `REDIS_HOST`, `REDIS_PORT`, `REDIS_PASSWORD` | Redis |

## Testing

- Tests use **H2 in-memory** DB in PostgreSQL compatibility mode.
- Flyway is **disabled** in tests; Hibernate DDL creates the schema from entities.
- Test config: [`src/test/resources/application-test.properties`](src/test/resources/application-test.properties).
- `TicketConcurrencyTest` uses 15 threads against 10 available tickets to validate pessimistic locking.
