# Server Health Monitoring Dashboard

A full-stack application for real-time server health monitoring, built with **Java Spring Boot** and **React.js**.

## Tech Stack

| Layer | Technology |
|-------|------------|
| Frontend | React.js, Recharts, Axios |
| Backend | Java 17, Spring Boot 3.2, Spring Data JPA |
| Database | PostgreSQL 15 |
| Build Tools | Maven, Vite |

## Features

- **Real-time Monitoring**: Live updates every 5 seconds
- **Server Metrics**: CPU, Memory, Disk, Network I/O
- **Threshold Alerts**: Automatic WARNING/CRITICAL status detection
- **Historical Trends**: Line charts showing performance over time
- **Responsive Design**: Dark theme, modern UI

## Project Structure

```
server-health-monitor/
├── backend/                 # Spring Boot REST API
│   ├── src/main/java/com/monitor/
│   │   ├── controller/      # REST endpoints
│   │   ├── service/         # Business logic
│   │   ├── repository/      # Data access (JPA)
│   │   ├── model/           # Entity classes
│   │   └── simulator/       # Test data generator
│   └── pom.xml
├── frontend/                # React application
│   ├── src/
│   │   ├── components/      # UI components
│   │   └── services/        # API client
│   └── package.json
└── docker-compose.yml       # PostgreSQL setup
```

## Prerequisites

- **Java 17+**
- **Node.js 18+**
- **Docker** (for PostgreSQL)
- **Maven**

## Setup Instructions

### 1. Install Maven

```bash
brew install maven
```

### 2. Start PostgreSQL Database

```bash
cd server-health-monitor
docker-compose up -d
```

### 3. Run Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### 4. Run Frontend (React)

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173` in your browser.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/metrics/latest` | Get latest metrics for all servers |
| GET | `/api/metrics/server/{id}` | Get metrics for specific server |
| GET | `/api/metrics/history/{id}?hours=1` | Get historical data |
| GET | `/api/metrics/alerts` | Get active alerts |

## Alert Thresholds

| Metric | Warning | Critical |
|--------|---------|----------|
| CPU | ≥ 70% | ≥ 90% |
| Memory | ≥ 75% | ≥ 90% |
| Disk | ≥ 80% | ≥ 95% |

## Skills Demonstrated

- **Java/Spring Boot**: REST API design, JPA/Hibernate, Dependency Injection
- **React.js**: Functional components, Hooks (useState, useEffect), Props
- **Database**: PostgreSQL, relational schema design, CRUD operations
- **DevOps**: Docker, containerization
- **Full-Stack Integration**: REST client/server, CORS configuration
