# Raga Roads – Microservices

The [Raga Roads](https://github.com/Subzero-005/raga-roads) artist allocation platform split into four Spring Boot services that talk to each other over REST, each with its own job and running in its own container.

## Services

| Service | Port | What it does |
|---|---|---|
| `auth-service` | 8084 | Logs users in and issues tokens |
| `artist-service` | 8081 | Artist data (JPA + PostgreSQL, seeded from `data.sql`) |
| `concert-service` | 8082 | Concert data (JPA + PostgreSQL, seeded from `data.sql`) |
| `booking-service` | 8083 | Fetches the artist and concert over HTTP (WebClient), runs the allocation strategy, and stores the booking |

```
client ──► auth-service (login → token)
client ──► booking-service ──► artist-service
                          └──► concert-service
                 │
                 ▼
             PostgreSQL
```

## Tech stack

Java 17 · Spring Boot 3 · Spring Data JPA · Spring WebFlux `WebClient` · Spring Security · PostgreSQL 15 · Docker Compose

## Running it

```bash
docker compose up --build
```

This starts PostgreSQL (on host port 5433) and all four services. The database password defaults to `postgres`; set `POSTGRES_PASSWORD` to change it.

## Trying it out

```bash
# 1. log in as the Band Manager
TOKEN=$(curl -s -X POST localhost:8084/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"manager","password":"manager123"}' | jq -r .token)

# 2. allocate an artist to a concert
curl -X POST localhost:8083/bookings/allocate \
  -H "Authorization: $TOKEN" -H 'Content-Type: application/json' \
  -d '{"concertId":1,"artistId":1}'

# 3. list bookings
curl localhost:8083/bookings -H "Authorization: $TOKEN"
```

| Endpoint | Service |
|---|---|
| `POST /auth/login`, `POST /auth/validate` | auth |
| `GET /artists/{id}` | artist |
| `GET /concerts/{id}` | concert |
| `POST /bookings/allocate` (Band Manager only), `GET /bookings` | booking |

The two allocation strategies work the same way as in the monolith: `BALANCED` checks role, location, availability and budget, and `AVAILABILITY` skips the location check.

## Tests

```bash
cd booking-service && mvn test
```

33 unit tests cover the validation rules, both strategies, the strategy factory and the booking service.

## Known limitations

The token is a Base64-encoded `username:role` string, not a signed JWT, so each service trusts it without checking it. This keeps the services simple for the exercise, but anyone could forge one. The monolith version uses signed JWTs, and that is what a real deployment should use here too.
