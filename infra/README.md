# AI Growth Partner - Infrastructure

Docker Compose-based local development environment for the AI Growth Partner MVP.

## Prerequisites

- **Docker** 20.x or later
- **Docker Compose** 2.x or later
- **Java** 17 LTS (for running backend services locally in IDE)
- **Node.js** 20.x or later (for frontend development)
- **Maven** 3.9+ or **Gradle** 8+ (for backend build)

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                      nginx (API Gateway)                     │
│              localhost:80 → /api/{service}/*                 │
└─────────────────────────────────────────────────────────────┘
        │           │           │           │           │
    ┌───┴───┐   ┌───┴───┐   ┌───┴───┐   ┌───┴───┐   ┌───┴───┐
    │ user  │   │ task  │   │ path  │   │content│   │  ai   │
    │ :8080 │   │ :8081 │   │ :8082 │   │ :8083 │   │ :8084 │
    └───┬───┘   └───┬───┘   └───┬───┘   └───┬───┘   └───┬───┘
        │           │           │           │           │
    ┌───┴───────────┴───────────┴───────────┴───────────┴───┐
    │                     MySQL :3306                        │
    │                   Redis :6379                          │
    │                 MongoDB :27017                         │
    └─────────────────────────────────────────────────────────┘
```

## Services

| Service | Port | Description | Database |
|---------|------|-------------|----------|
| nginx | 80 | API Gateway / Reverse Proxy | - |
| mysql | 3306 | User data, task data, path data | MySQL 8 |
| redis | 6379 | Session, cache | Redis 7 |
| mongodb | 27017 | Knowledge base documents | MongoDB 7 |

## Quick Start

### 1. Start all infrastructure services

```bash
cd infra
cp .env.example .env
# Edit .env and fill in your API keys
docker-compose up -d
```

### 2. Verify services are running

```bash
docker-compose ps
curl http://localhost/health
```

### 3. Stop all services

```bash
docker-compose down
# To remove volumes (data loss):
docker-compose down -v
```

## Running Backend Services

Each Spring Boot service can be run independently in your IDE.

### Service Ports

| Service | Port | Maven Module |
|---------|------|-------------|
| user-service | 8080 | backend/user-service |
| task-service | 8081 | backend/task-service |
| path-service | 8082 | backend/path-service |
| content-service | 8083 | backend/content-service |
| ai-gateway | 8084 | backend/ai-gateway |

### Running in IDE

1. Ensure Docker services are running (`docker-compose up -d`)
2. Import backend folder as Maven project in your IDE
3. Run each service with its respective port configuration
4. Each service requires environment variables - use the `.env` file values

### Running via Command Line

```bash
cd backend

# Run specific service
mvn clean compile -pl user-service -am
mvn spring-boot:run -pl user-service

# Run all services (separate terminals)
mvn spring-boot:run -pl user-service
mvn spring-boot:run -pl task-service
mvn spring-boot:run -pl path-service
mvn spring-boot:run -pl content-service
mvn spring-boot:run -pl ai-gateway
```

## Running Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on http://localhost:3000 and proxies API requests to nginx on port 80.

## Environment Variables

Copy `.env.example` to `.env` and configure:

```bash
cp .env.example .env
```

### Required Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `MYSQL_ROOT_PASSWORD` | MySQL root password | `rootpassword` |
| `MYSQL_PASSWORD` | MySQL app password | `aigrowth123` |
| `MONGO_PASSWORD` | MongoDB admin password | `mongopass123` |
| `OPENAI_API_KEY` | OpenAI API key | `sk-...` |
| `JWT_SECRET` | JWT signing secret (256-bit) | `your-secret-key` |

## Useful Commands

### View logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f mysql
docker-compose logs -f redis
```

### Restart a service

```bash
docker-compose restart nginx
```

### Connect to databases

```bash
# MySQL
mysql -h localhost -P 3306 -u root -p

# MongoDB
mongosh -h localhost:27017 -u admin -p
```

### Database initialization

MySQL database `ai_growth_partner` is auto-created on first start.
For schema initialization, run SQL scripts in each service's `db/migration` folder.

## Production Deployment Notes

This docker-compose setup is for **local development only**.

For production:
- Use Kubernetes manifests in `infra/k8s/`
- Configure proper secrets management
- Use managed database services (Cloud SQL, Redis Cloud, MongoDB Atlas)
- Set up TLS termination
- Configure proper logging and monitoring

## Troubleshooting

### Services not starting

```bash
docker-compose down
docker-compose up -d --force-recreate
docker-compose logs [service-name]
```

### Port conflicts

If ports 3306, 6379, or 27017 are in use:

```bash
# Check what's using the port
lsof -i :3306

# Change ports in docker-compose.yml if needed
```

### Database connection issues

```bash
# Verify MySQL is ready
docker-compose exec mysql mysqladmin ping -h localhost

# Check MySQL logs
docker-compose logs mysql
```
