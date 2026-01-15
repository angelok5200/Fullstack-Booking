# Reservation System

## Overview
A Spring Boot REST API for a reservation management system. This is a course project that demonstrates Spring Boot fundamentals including REST controllers, services, validation, and in-memory data storage.

## Project Structure
```
spring-boot-intensive-main/
├── src/main/java/liaskovych/reservation/
│   ├── ReservationSystemApplication.java  # Main application entry
│   ├── reservations/                       # Reservation domain
│   │   ├── ReservationController.java      # REST endpoints
│   │   ├── ReservationService.java         # Business logic
│   │   ├── Reservation.java                # DTO/Record
│   │   ├── ReservationEntity.java          # Entity model
│   │   ├── InMemoryReservationRepository.java
│   │   └── availability/                   # Availability checking
│   └── web/                                # Web layer (error handling)
└── pom.xml                                 # Maven dependencies
```

## Technology Stack
- Java 17 (compatible with GraalVM 22.3)
- Spring Boot 3.4.3
- Maven (wrapper included)
- In-memory data storage (no database required)

## API Endpoints

### Reservations
- `GET /reservation` - List all reservations (supports filtering by roomId, userId, pagination)
- `GET /reservation/{id}` - Get reservation by ID
- `POST /reservation` - Create new reservation
- `PUT /reservation/{id}` - Update reservation
- `DELETE /reservation/{id}/cancel` - Cancel reservation
- `POST /reservation/{id}/approve` - Approve reservation

### Availability
- `POST /reservation/availability/check` - Check room availability

## Running the Application
The application runs on port 5000 and binds to 0.0.0.0 for Replit compatibility.

```bash
cd spring-boot-intensive-main && ./mvnw spring-boot:run
```

## Configuration
- Port: 5000
- Address: 0.0.0.0
- Database: In-memory (no external database required)
