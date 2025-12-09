# Media App gRPC

A microservices-based media application using gRPC for inter-service communication and REST API for client access.

## Architecture

This project consists of three main modules:

1. **proto** - Protocol Buffers definitions for gRPC services
2. **mediaserver** - gRPC server that handles video and creator management
3. **mediaclient** - REST API client that exposes HTTP endpoints and communicates with mediaserver via gRPC

## Prerequisites

- Java 21
- Maven 3.6+
- Postman (for API testing)

## Building the Project

```bash
mvn clean install
```

## Running the Application

### Step 1: Start the Media Server (gRPC Server)

Open a terminal and run:

```bash
cd mediaserver
mvn spring-boot:run
```

The gRPC server will start on port **9090**.

You should see output like:

```
gRPC Server started, listening on address: *, port: 9090
```

### Step 2: Start the Media Client (REST API Server)

Open a **new terminal** and run:

```bash
cd mediaclient
mvn spring-boot:run
```

The REST API server will start on port **8080** (default Spring Boot port).

You should see output like:

```
Tomcat started on port 8080
```

## API Endpoints

The mediaclient exposes the following REST endpoints:

### Video Endpoints

| Method | Endpoint             | Description        |
| ------ | -------------------- | ------------------ |
| POST   | `/api/videos/upload` | Upload a new video |
| GET    | `/api/videos/{id}`   | Get video by ID    |

### Creator Endpoints

| Method | Endpoint                    | Description                 |
| ------ | --------------------------- | --------------------------- |
| GET    | `/api/creators/{id}`        | Get creator by ID           |
| GET    | `/api/creators/{id}/videos` | Get all videos by a creator |

## Testing with Postman

### Import the Postman Collection

1. Open Postman
2. Click **Import**
3. Select **Raw text** and paste the JSON collection below
4. Click **Import**

### Postman Collection JSON

```json
{
  "info": {
    "name": "Media App gRPC API",
    "description": "REST API endpoints for Media App",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Videos",
      "item": [
        {
          "name": "Upload Video",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"title\": \"Introduction to gRPC\",\n  \"description\": \"A comprehensive guide to gRPC and Protocol Buffers\",\n  \"url\": \"https://example.com/videos/grpc-intro.mp4\",\n  \"durationSeconds\": 1200,\n  \"creator\": {\n    \"id\": \"creator-001\",\n    \"name\": \"John Doe\",\n    \"email\": \"john.doe@example.com\"\n  }\n}"
            },
            "url": {
              "raw": "http://localhost:8080/api/videos/upload",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "videos", "upload"]
            }
          },
          "response": []
        },
        {
          "name": "Get Video by ID",
          "request": {
            "method": "GET",
            "header": [],
            "url": {
              "raw": "http://localhost:8080/api/videos/:videoId",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "videos", ":videoId"],
              "variable": [
                {
                  "key": "videoId",
                  "value": "{{videoId}}",
                  "description": "The ID of the video (copy from upload response)"
                }
              ]
            }
          },
          "response": []
        }
      ]
    },
    {
      "name": "Creators",
      "item": [
        {
          "name": "Get Creator by ID",
          "request": {
            "method": "GET",
            "header": [],
            "url": {
              "raw": "http://localhost:8080/api/creators/:creatorId",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "creators", ":creatorId"],
              "variable": [
                {
                  "key": "creatorId",
                  "value": "creator-001",
                  "description": "The ID of the creator"
                }
              ]
            }
          },
          "response": []
        },
        {
          "name": "Get Creator Videos",
          "request": {
            "method": "GET",
            "header": [],
            "url": {
              "raw": "http://localhost:8080/api/creators/:creatorId/videos",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "creators", ":creatorId", "videos"],
              "variable": [
                {
                  "key": "creatorId",
                  "value": "creator-001",
                  "description": "The ID of the creator"
                }
              ]
            }
          },
          "response": []
        }
      ]
    }
  ]
}
```

## Testing Workflow

Follow these steps to test the application:

### 1. Upload a Video

**Request:**

```bash
POST http://localhost:8080/api/videos/upload
Content-Type: application/json

{
  "title": "Introduction to gRPC",
  "description": "A comprehensive guide to gRPC and Protocol Buffers",
  "url": "https://example.com/videos/grpc-intro.mp4",
  "durationSeconds": 1200,
  "creator": {
    "id": "creator-001",
    "name": "John Doe",
    "email": "john.doe@example.com"
  }
}
```

**Response:**

```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "title": "Introduction to gRPC",
  "description": "A comprehensive guide to gRPC and Protocol Buffers",
  "url": "https://example.com/videos/grpc-intro.mp4",
  "durationSeconds": 1200,
  "creator": {
    "id": "creator-001",
    "name": "John Doe",
    "email": "john.doe@example.com"
  }
}
```

**Note:** Copy the `id` from the response to use in subsequent requests.

### 2. Get Video by ID

**Request:**

```bash
GET http://localhost:8080/api/videos/{videoId}
```

Replace `{videoId}` with the ID from the upload response.

### 3. Get Creator by ID

**Request:**

```bash
GET http://localhost:8080/api/creators/creator-001
```

**Response:**

```json
{
  "id": "creator-001",
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

### 4. Get All Videos by Creator

**Request:**

```bash
GET http://localhost:8080/api/creators/creator-001/videos
```

**Response:**

```json
[
  {
    "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "title": "Introduction to gRPC",
    "description": "A comprehensive guide to gRPC and Protocol Buffers",
    "url": "https://example.com/videos/grpc-intro.mp4",
    "durationSeconds": 1200,
    "creator": {
      "id": "creator-001",
      "name": "John Doe",
      "email": "john.doe@example.com"
    }
  }
]
```

## Sample Test Data

Here are some additional test payloads you can use:

### Upload Video - Tutorial Series

```json
{
  "title": "Spring Boot Microservices Part 1",
  "description": "Building microservices with Spring Boot",
  "url": "https://example.com/videos/spring-boot-1.mp4",
  "durationSeconds": 1800,
  "creator": {
    "id": "creator-002",
    "name": "Jane Smith",
    "email": "jane.smith@example.com"
  }
}
```

### Upload Video - Short Tutorial

```json
{
  "title": "Quick gRPC Setup",
  "description": "5-minute guide to setting up gRPC",
  "url": "https://example.com/videos/grpc-quick.mp4",
  "durationSeconds": 300,
  "creator": {
    "id": "creator-001",
    "name": "John Doe",
    "email": "john.doe@example.com"
  }
}
```

## Architecture Flow

```
Client (Postman/Browser)
    ↓ HTTP/REST
mediaclient (Port 8080)
    ↓ gRPC
mediaserver (Port 9090)
    ↓
In-Memory Storage (ConcurrentHashMap)
```

## Troubleshooting

### Port Already in Use

If you get a "port already in use" error:

**For mediaserver (port 9090):**

```bash
# Find the process
netstat -ano | findstr :9090
# Kill the process (replace PID with actual process ID)
taskkill /PID <PID> /F
```

**For mediaclient (port 8080):**

```bash
# Find the process
netstat -ano | findstr :8080
# Kill the process
taskkill /PID <PID> /F
```

### gRPC Connection Error

If mediaclient cannot connect to mediaserver:

1. Ensure mediaserver is running first
2. Check that mediaserver is listening on port 9090
3. Verify no firewall is blocking the connection

### Build Errors

If you encounter build errors:

```bash
mvn clean install -U
```

The `-U` flag forces Maven to update dependencies.

## Project Structure

```
mediaappgrpc/
├── proto/                          # Protocol Buffers definitions
│   └── src/main/resources/
│       └── schema.proto           # gRPC service definitions
├── mediaserver/                    # gRPC Server
│   └── src/main/java/
│       └── org/example/mediaserver/
│           ├── dao/               # Data Access Objects
│           ├── grpc/              # gRPC Service Implementations
│           ├── mapper/            # Proto ↔ DAO Mappers
│           ├── repository/        # Data Repositories
│           └── service/           # Business Logic
└── mediaclient/                    # REST API Client
    └── src/main/java/
        └── org/example/mediaclient/
            ├── controller/        # REST Controllers
            ├── dto/               # Data Transfer Objects
            ├── mapper/            # Proto ↔ DTO Mappers
            └── service/           # gRPC Client Services
```

## Technologies Used

- **Java 21**
- **Spring Boot 4.0.0**
- **gRPC** - Inter-service communication
- **Protocol Buffers** - Data serialization
- **Maven** - Build tool
- **Lombok** - Boilerplate code reduction

## Notes

- This application uses **in-memory storage** (ConcurrentHashMap), so data will be lost when the server restarts
- Video IDs are auto-generated UUIDs
- Creator IDs must be provided by the client
- The application uses **plaintext** gRPC communication (not suitable for production)

## Next Steps

To make this production-ready, consider:

- Adding a persistent database (PostgreSQL, MongoDB)
- Implementing authentication and authorization
- Adding TLS/SSL for gRPC communication
- Implementing proper error handling and validation
- Adding logging and monitoring
- Containerizing with Docker
- Adding integration tests

## License

This project is for educational purposes.
