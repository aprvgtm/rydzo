# Rydzo Microservices Platform

A complete **microservices-based transportation platform** with an API Gateway routing requests to multiple independent services.

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                     API GATEWAY (8080)                      │
│              Routes requests to microservices               │
└─────────────┬─────────────────────────────────────────────┬─┘
              │                                               │
    ┌─────────────────────────────────────────────────────────────────────┐
    │                      EUREKA SERVICE REGISTRY (8761)                  │
    │                     (Service Discovery & Health)                     │
    └─────────────────────────────────────────────────────────────────────┘
              │
    ┌─────────┴──────────────┬──────────────────┬──────────────┐
    │                        │                  │              │
┌───────────┐   ┌──────────────┐   ┌──────────────┐   ┌──────────────┐
│ USER      │   │ VENDOR       │   │ VEHICLE      │   │ LOCAL        │
│ SERVICE   │   │ SERVICE      │   │ SERVICE      │   │ CARPOOL      │
│ (8081)    │   │ (8082)       │   │ (8083)       │   │ (8084)       │
└───────────┘   └──────────────┘   └──────────────┘   └──────────────┘
    │                │                   │                   │
    └────────────────┴───────────────────┴───────────────────┘
                     │
          ┌──────────┴──────────┐
          │                     │
    ┌─────────────┐      ┌──────────────────┐
    │             │      │                  │
┌──────────────┐  │  ┌──────────────┐  ┌──────────────┐
│ INTERCITY    │  │  │ BOOKING      │  │ PAYMENT      │
│ POOL (8085)  │  │  │ SERVICE (8086)   SERVICE (8087)│
└──────────────┴──┴──┴──────────────┴──┴──────────────┘
                     │
             ┌───────┴────────┐
             │                │
        ┌──────────────┐  ┌──────────────┐
        │NOTIFICATION │  │ POSTGRESQL   │
        │SERVICE(8088)│  │ DATABASE     │
        └──────────────┘  └──────────────┘
```

## Services Overview

### 1. **API Gateway** (Port: 8080)
- Central entry point for all API requests
- Routes requests to appropriate microservices using Spring Cloud Gateway
- Load balancing via Eureka service registry
- CORS enabled, Security configured

**Routes:**
- `/api/v1/users/**` → User Service
- `/api/v1/vendors/**` → Vendor Service  
- `/api/v1/vehicles/**` → Vehicle Service
- `/api/v1/local-rides/**` → Local Carpool Service
- `/api/v1/intercity-rides/**` → Intercity Pool Service
- `/api/v1/bookings/**` → Booking Service
- `/api/v1/payments/**` → Payment Service
- `/api/v1/notifications/**` → Notification Service

### 2. **Service Registry** (Port: 8761)
- Eureka Server for service discovery
- Services auto-register themselves
- Health checks and monitoring
- Dashboard at `http://localhost:8761`

### 3. **User Service** (Port: 8081)
- User registration and authentication
- Document/KYC verification
- User profiles management
- Database: `rydzo_users_db`

### 4. **Vendor Service** (Port: 8082)
- Vendor registration and onboarding
- Vendor verification and approvals
- Vendor dashboard and analytics
- Database: `rydzo_vendors_db`

### 5. **Vehicle Service** (Port: 8083)
- Vehicle registration (Cars, Bikes, Scooties, Autos)
- Vehicle verification (RC, Insurance docs)
- Vehicle availability management
- Pricing rules per vehicle
- Database: `rydzo_vehicles_db`

### 6. **Local Carpool Service** (Port: 8084)
- Create/manage local rides within city
- Join existing local rides
- Recurring daily/weekly routes
- Advance booking (7+ days)
- Database: `rydzo_local_carpool_db`

### 7. **Intercity Pool Service** (Port: 8085)
- Long-distance routes between cities
- Multiple stops/stations management
- Advance booking (14+ days)
- Premium pricing
- Database: `rydzo_intercity_pool_db`

### 8. **Booking Service** (Port: 8086)
- Manage bookings across all services
- Confirmation workflow
- Cancellation and rescheduling
- Booking history
- Database: `rydzo_bookings_db`

### 9. **Payment Service** (Port: 8087)
- Multiple payment methods
- Wallet management
- Commission calculation for vendors
- Invoice generation
- Database: `rydzo_payments_db`

### 10. **Notification Service** (Port: 8088)
- Email notifications
- SMS alerts
- Push notifications
- Booking confirmations & updates

## Database Structure

**PostgreSQL** is used with separate databases per microservice (Database-per-Service Pattern):

```
PostgreSQL (postgres:5432)
├── rydzo_users_db
├── rydzo_vendors_db
├── rydzo_vehicles_db
├── rydzo_local_carpool_db
├── rydzo_intercity_pool_db
├── rydzo_bookings_db
└── rydzo_payments_db
```

**Credentials:**
- User: `rydzo_user`
- Password: `rydzo_password`

## Quick Start

### Prerequisites

- Java 21 JDK
- Maven 3.8.9+
- Docker & Docker Compose
- Git

### 1. Build All Services

Build the rydzo-api-gateway first:
```bash
cd C:\rydzo-platform\rydzo-api-gateway
mvn clean package -DskipTests
```

### 2. Start All Services with Docker Compose

```bash
cd C:\rydzo-platform
docker-compose up -d
```

**Wait 30-60 seconds for all services to start and register with Eureka.**

### 3. Verify Services Are Running

**Check Eureka Dashboard:**
```
http://localhost:8761
```

**Check Gateway Health:**
```
curl http://localhost:8080/api/v1/health
```

**Check Registered Services:**
```
curl http://localhost:8080/api/v1/health/services
```

## API Usage Examples

### Create User
```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phoneNumber": "9876543210",
    "password": "securePass123",
    "profileImage": null
  }'
```

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phoneNumber": "9876543210",
  "rating": 5.0,
  "totalRides": 0,
  "isVerified": false,
  "isActive": true,
  "createdAt": "2026-06-02T10:30:00"
}
```

### Create Vendor
```bash
curl -X POST http://localhost:8080/api/v1/vendors \
  -H "Content-Type: application/json" \
  -d '{
    "businessName": "Quick Cabs",
    "ownerName": "Rajesh Kumar",
    "phoneNumber": "9988776655",
    "email": "vendor@quickcabs.com",
    "address": "123 Main St, City",
    "bankAccount": "XXXX-XXXX-1234",
    "ifscCode": "IBKL0001234"
  }'
```

### Register Vehicle (by Vendor)
```bash
curl -X POST "http://localhost:8080/api/v1/vehicles?vendorId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "type": "CAR",
    "make": "Maruti",
    "model": "Swift",
    "licensePlate": "MH01AB1234",
    "registrationNumber": "MH0112345678",
    "year": 2023,
    "seatingCapacity": 5,
    "color": "Silver"
  }'
```

### Create Local Carpool Ride
```bash
curl -X POST "http://localhost:8080/api/v1/local-rides?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "startingLocation": "Downtown Station",
    "destination": "Airport",
    "departureTime": "2026-06-03T10:00:00",
    "availableSeats": 3,
    "estimatedFare": 300.00,
    "rideType": "LOCAL_CARPOOL"
  }'
```

## Docker Compose Commands

**Start all services:**
```bash
docker-compose up -d
```

**View logs:**
```bash
docker-compose logs -f api-gateway
docker-compose logs -f user-service
```

**Stop all services:**
```bash
docker-compose down
```

**Remove volumes (delete database data):**
```bash
docker-compose down -v
```

**Rebuild specific service:**
```bash
docker-compose up -d --build user-service
```

## Monitoring & Dashboards

- **Eureka Dashboard:** `http://localhost:8761`
- **Gateway Health:** `http://localhost:8080/api/v1/health`
- **Actuator Endpoints:** `http://localhost:8080/actuator`

## Security

- CORS enabled for all origins
- CSRF protection disabled (can be enabled in production)
- Default credentials:
  - Username: `admin`
  - Password: `admin123`

## Development Workflow

1. **Create new service:** Copy one service structure
2. **Register with Eureka:** Add `@EnableDiscoveryClient`
3. **Add gateway route:** Update `application.yaml` in gateway
4. **Rebuild:** `mvn clean package -DskipTests`
5. **Deploy:** Re-run `docker-compose up`

## Troubleshooting

### Services not registering with Eureka?
- Check network connectivity: `docker network ls`
- Verify service URLs use container names, not localhost

### Gateway routing not working?
- Check routes in `application.yaml`
- Verify service names match in Eureka registry
- Check logs: `docker-compose logs api-gateway`

### Database connection error?
- Ensure PostgreSQL is running: `docker-compose logs postgres`
- Verify database exists: Check `init-db.sql` was executed

## Next Steps

1. Implement business logic in each microservice
2. Add inter-service communication (OpenFeign or RestTemplate)
3. Implement authentication/JWT tokens
4. Add message queue (RabbitMQ/Kafka) for async operations
5. Implement circuit breaker patterns (Resilience4j)
6. Add distributed tracing (Sleuth + Zipkin)
7. Configure Kubernetes deployment
8. Setup CI/CD pipelines

## Technology Stack

- **Java:** 21 LTS
- **Spring Boot:** 3.5.10
- **Spring Cloud:** 2025.0.1
- **Database:** PostgreSQL 16
- **Containerization:** Docker & Docker Compose
- **Build Tool:** Maven
- **Service Discovery:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway

---

**Status:** Ready for development
**Last Updated:** June 2, 2026

