---
description: "Scaffold a new CRUD feature (controller/service/repo/DTO/mapper) following TicketFlash conventions. Use when adding a new domain resource."
agent: agent
argument-hint: "Feature name, e.g. 'Organizer' or 'Sponsor'"
---

Scaffold a complete new feature for the TicketFlash backend following all existing project conventions.

**Feature name**: $input

Use the feature name to derive:
- Entity class name: `{FeatureName}` (PascalCase)
- Table name: `{feature_names}` (snake_case plural)
- Base URL: `/{feature-names}` (kebab-case plural)
- Package prefix: `com.barox.ticketflash`

---

## Files to create

### 1. Entity — `entity/{FeatureName}.java`

Follow the pattern of existing entities. Use Lombok `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`. Use `@Entity` + `@Table(name = "{feature_names}")`. ID should be `Long` with `@GeneratedValue(strategy = GenerationType.IDENTITY)` unless the feature warrants UUID. Add `@ToString.Exclude` on any bidirectional relation fields.

### 2. Request DTO — `dto/request/{FeatureName}Request.java`

Use `@Data` + `@AllArgsConstructor`. Add `@NotBlank` / `@NotNull` / `@Size` Jakarta Validation constraints on required fields. Never expose entity types — use only primitive/String/Long/enum fields.

### 3. Response DTO — `dto/response/{FeatureName}Response.java`

Use `@Data` only (no `@AllArgsConstructor`). Flatten any nested entity fields (e.g. `eventName` instead of `Event event`). Mirror the fields callers actually need.

### 4. Mapper — `mapper/{FeatureName}Mapper.java`

```java
@Mapper(componentModel = "spring")
public interface {FeatureName}Mapper {

    @Mapping(target = "id", ignore = true)
    // ignore all fields that the service sets (status, dates, computed values, related entities)
    {FeatureName} toEntity({FeatureName}Request request);

    // use source = "nested.field" to flatten nested entity fields into response
    {FeatureName}Response toResponse({FeatureName} entity);

    @Mapping(target = "id", ignore = true)
    // ignore non-updatable fields
    void updateEntity(@MappingTarget {FeatureName} entity, {FeatureName}Request request);
}
```

### 5. Repository — `repository/{FeatureName}Repository.java`

```java
@Repository
public interface {FeatureName}Repository extends JpaRepository<{FeatureName}, Long> {
    // Add @Lock(PESSIMISTIC_WRITE) + @Query if the resource has inventory/quantity that needs concurrency protection
}
```

### 6. Service interface — `service/{FeatureName}Service.java`

Define methods: `create`, `getById`, `getAll` (paged → returns `PagedResponse<{FeatureName}Response>`), `update`, `delete`. Match the signature style of [EventService.java](../../src/main/java/com/barox/ticketflash/service/EventService.java).

### 7. Service implementation — `service/impl/{FeatureName}ServiceImpl.java`

- Annotate: `@Service`, `@RequiredArgsConstructor`
- Inject repository, mapper (and any related repos)
- Use `@Transactional` on write operations
- Throw `DataNotFoundException` for missing entities (triggers 404 via `GlobalExceptionHandler`)
- Use `@Cacheable` / `@CacheEvict` where reads are frequent and data changes infrequently (follow [EventServiceImpl.java](../../src/main/java/com/barox/ticketflash/service/impl/EventServiceImpl.java) pattern)
- For paged list operations, return `PagedResponse<{FeatureName}Response>` built from `Page<{FeatureName}>`

### 8. Controller — `controller/{FeatureName}Controller.java`

```java
@RestController
@RequestMapping("/{feature-names}")
@RequiredArgsConstructor
public class {FeatureName}Controller {

    private final {FeatureName}Service {featureName}Service;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")          // adjust role as needed
    public ResponseEntity<{FeatureName}Response> create(@Valid @RequestBody {FeatureName}Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body({featureName}Service.create(request));
    }

    @GetMapping
    @RateLimit(capacity = 5, refillTokens = 5)     // add to high-traffic read endpoints
    public ResponseEntity<PagedResponse<{FeatureName}Response>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok({featureName}Service.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<{FeatureName}Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok({featureName}Service.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<{FeatureName}Response> update(@PathVariable Long id,
            @Valid @RequestBody {FeatureName}Request request) {
        return ResponseEntity.ok({featureName}Service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        {featureName}Service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

- Only add `@RateLimit` to endpoints expected to receive high read traffic.
- Only add `@PreAuthorize` to write endpoints (POST/PUT/DELETE). Public read endpoints need no role annotation.
- `@RateLimit` only works on controller methods; do not place it on service methods.

### 9. Flyway migration — `src/main/resources/db/migration/V{n}__add_{feature_names}.sql`

Find the current highest migration version (V1–V4 exist) and use the next number. Create the table with appropriate columns, constraints, indexes. Use `BIGSERIAL` for ID unless UUID is warranted. Follow the schema style in [V1__init_database.sql](../../src/main/resources/db/migration/V1__init_database.sql).

---

## Checklist before finishing

- [ ] All ignored `@Mapping` targets correspond to fields the service actually sets
- [ ] No entity type leaked into any DTO
- [ ] Mapper `updateEntity` ignores `id` and any non-user-updatable fields
- [ ] `@Transactional` on all write service methods
- [ ] `DataNotFoundException` (not `RuntimeException`) thrown for 404 cases
- [ ] Migration version number is the next available (check existing V1–V4)
- [ ] `@PreAuthorize` used on all ADMIN-only endpoints
- [ ] `@RateLimit` added only to controller methods, not service methods
