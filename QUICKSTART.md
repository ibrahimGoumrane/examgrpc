# Quick Start Guide

## Prerequisites

- Java 21 installed
- Maven installed
- Two terminal windows

## Step-by-Step Instructions

### 1. Build the Project (One Time)

```bash
cd C:\Users\ibrahim\Desktop\projectexam\mediaappgrpc
mvn clean install
```

Wait for "BUILD SUCCESS" message.

### 2. Start the Media Server (Terminal 1)

```bash
cd C:\Users\ibrahim\Desktop\projectexam\mediaappgrpc\mediaserver
mvn spring-boot:run
```

**Wait for:** `gRPC Server started, listening on address: *, port: 9090`

### 3. Start the Media Client (Terminal 2)

Open a **NEW** terminal window:

```bash
cd C:\Users\ibrahim\Desktop\projectexam\mediaappgrpc\mediaclient
mvn spring-boot:run
```

**Wait for:** `Tomcat started on port 8080`

### 4. Test with Postman

1. **Import Collection:**

   - Open Postman
   - Click **Import**
   - Select the file: `C:\Users\ibrahim\Desktop\projectexam\mediaappgrpc\postman_collection.json`
   - Click **Import**

2. **Run First Request:**

   - Open the collection "Media App gRPC API"
   - Select **Videos → Upload Video**
   - Click **Send**
   - You should get a response with a video ID

3. **Test Other Endpoints:**
   - The video ID is automatically saved
   - Try **Videos → Get Video by ID**
   - Try **Creators → Get Creator by ID** (use `creator-001`)
   - Try **Creators → Get Creator Videos**

## Quick Test with cURL

### Upload a Video

```bash
curl -X POST http://localhost:8080/api/videos/upload ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"Test Video\",\"description\":\"A test video\",\"url\":\"https://example.com/test.mp4\",\"durationSeconds\":600,\"creator\":{\"id\":\"creator-001\",\"name\":\"John Doe\",\"email\":\"john@example.com\"}}"
```

### Get Creator

```bash
curl http://localhost:8080/api/creators/creator-001
```

### Get Creator Videos

```bash
curl http://localhost:8080/api/creators/creator-001/videos
```

## Stopping the Servers

Press `Ctrl+C` in each terminal window.

## Troubleshooting

**Problem:** Port 8080 already in use

```bash
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

**Problem:** Port 9090 already in use

```bash
netstat -ano | findstr :9090
taskkill /PID <PID> /F
```

**Problem:** Connection refused

- Make sure mediaserver (port 9090) is started BEFORE mediaclient (port 8080)

## Architecture

```
Postman/Browser
      ↓
mediaclient:8080 (REST API)
      ↓ (gRPC)
mediaserver:9090 (gRPC Server)
      ↓
In-Memory Storage
```

## Available Endpoints

| Method | Endpoint                    | Description               |
| ------ | --------------------------- | ------------------------- |
| POST   | `/api/videos/upload`        | Upload a new video        |
| GET    | `/api/videos/{id}`          | Get video by ID           |
| GET    | `/api/creators/{id}`        | Get creator by ID         |
| GET    | `/api/creators/{id}/videos` | Get all videos by creator |

## Sample Request Body

```json
{
  "title": "My Video Title",
  "description": "Video description",
  "url": "https://example.com/video.mp4",
  "durationSeconds": 1200,
  "creator": {
    "id": "creator-001",
    "name": "John Doe",
    "email": "john@example.com"
  }
}
```

## Notes

- Data is stored in memory - it will be lost when servers restart
- Video IDs are auto-generated UUIDs
- Creator IDs must be provided by you
- Start mediaserver BEFORE mediaclient
