# AI Growth Partner - MVP Technical Solution

## 1. Project Structure Overview

```
ai-growth-partner/
├── backend/                      # Spring Boot microservices
│   ├── user-service/             # User & profile management (MySQL)
│   ├── task-service/             # Gamified task system (MySQL + Redis)
│   ├── path-service/             # Path planning engine (MySQL)
│   ├── content-service/          # Knowledge base (MongoDB)
│   ├── ai-gateway/               # AI middleware (OpenAI/Anthropic)
│   └── common/                   # Shared DTOs, utils, dependencies
├── frontend/                     # Next.js 14 web application
│   ├── app/                      # App router pages
│   ├── components/               # Reusable UI components
│   ├── features/                 # Feature modules
│   └── lib/                      # API clients, utilities
├── infra/                        # Infrastructure as code
│   ├── docker-compose.yml        # Local dev environment
│   ├── nginx/                    # API gateway configuration
│   └── .env.example              # Environment variable template
├── docs/                         # Product & technical documentation
├── .github/workflows/            # CI/CD pipelines
└── openspec/                     # Change management
```

## 2. Service Architecture

```
                           ┌──────────────────────────────────────────────────┐
                           │                   nginx (Port 80)                │
                           │  /api/user/*   → user-service:8080              │
                           │  /api/tasks/*  → task-service:8081               │
                           │  /api/paths/*  → path-service:8082               │
                           │  /api/knowledge/* → content-service:8083         │
                           │  /api/ai/*     → ai-gateway:8084                 │
                           └──────────────────────────────────────────────────┘
                                          │
        ┌─────────────────────────────────┼─────────────────────────────────┐
        │                                 │                                 │
        ▼                                 ▼                                 ▼
┌───────────────┐              ┌───────────────┐              ┌───────────────┐
│ user-service  │              │ task-service  │              │ path-service  │
│    :8080      │              │    :8081      │              │    :8082      │
│   (MySQL)     │              │ (MySQL+Redis) │              │   (MySQL)     │
└───────────────┘              └───────────────┘              └───────────────┘

        │                                 │                                 │
        ▼                                 ▼                                 ▼
┌───────────────┐              ┌───────────────┐              ┌───────────────┐
│content-service│              │  ai-gateway   │              │   (Shared)    │
│    :8083      │              │    :8084      │              │   MySQL +      │
│  (MongoDB)    │              │ (OpenAI/Claude│              │   Redis +      │
└───────────────┘              └───────────────┘              │   MongoDB      │
                                                              └───────────────┘
```

## 3. Database Schema Summary

### MySQL (user-service, task-service, path-service)

#### user-service
```sql
-- users table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    nickname VARCHAR(100),
    avatar_url VARCHAR(500),
    status TINYINT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- user_profiles table (multi-dimensional user画像)
CREATE TABLE user_profiles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    dimension VARCHAR(50) NOT NULL,  -- career, cognition, emotion, health
    level INT DEFAULT 1,
    xp BIGINT DEFAULT 0,
    data JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- user_badges table
CREATE TABLE user_badges (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    badge_id VARCHAR(100) NOT NULL,
    badge_name VARCHAR(200),
    earned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

#### task-service
```sql
-- tasks table
CREATE TABLE tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(50),           -- daily, weekly, quest, milestone
    xp_reward INT DEFAULT 0,
    difficulty VARCHAR(20),          -- easy, medium, hard, legendary
    status VARCHAR(20) DEFAULT 'active',
    path_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- user_tasks table (user-task relationship)
CREATE TABLE user_tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    task_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',  -- pending, in_progress, completed, failed
    progress INT DEFAULT 0,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,
    xp_earned INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (task_id) REFERENCES tasks(id)
);
```

#### path-service
```sql
-- skill_paths table
CREATE TABLE skill_paths (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100),           -- career, skill, certification
    difficulty VARCHAR(20),
    total_duration_days INT,
    status VARCHAR(20) DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- path_steps table
CREATE TABLE path_steps (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    path_id BIGINT NOT NULL,
    step_order INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration_days INT,
    FOREIGN KEY (path_id) REFERENCES skill_paths(id)
);

-- user_path_progress table
CREATE TABLE user_path_progress (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    path_id BIGINT NOT NULL,
    current_step INT DEFAULT 1,
    status VARCHAR(20) DEFAULT 'active',
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (path_id) REFERENCES skill_paths(id)
);
```

### MongoDB (content-service)

```javascript
// knowledge_articles collection
{
  _id: ObjectId,
  title: String,
  content: String,           // Markdown content
  author_id: ObjectId,
  tags: [String],
  category: String,
  visibility: String,       // public, private, shared
  created_at: Date,
  updated_at: Date,
  view_count: Number,
  embedding: [Number]       // For vector search
}

// user_notes collection
{
  _id: ObjectId,
  user_id: ObjectId,
  title: String,
  content: String,
  linked_tasks: [ObjectId],
  linked_paths: [ObjectId],
  created_at: Date,
  updated_at: Date
}
```

### Redis

- **Session storage**: `session:{user_id}` - User session data
- **Cache**: `cache:{resource}:{id}` - Frequently accessed data
- **XP/Ranking**: `leaderboard:xp` - Sorted set for XP rankings
- **Task cooldown**: `cooldown:task:{task_id}` - Task completion cooldowns

## 4. API Gateway Routing

| Path Pattern | Upstream Service | Port | Description |
|-------------|------------------|------|-------------|
| `GET /api/user/*` | user-service | 8080 | User registration, login, profile |
| `GET /api/tasks/*` | task-service | 8081 | Task CRUD, XP operations |
| `GET /api/paths/*` | path-service | 8082 | Skill paths, recommendations |
| `GET /api/knowledge/*` | content-service | 8083 | Knowledge base, notes |
| `GET /api/ai/*` | ai-gateway | 8084 | AI chat, recommendations |
| `GET /health` | nginx | - | Health check (no upstream) |

## 5. Development Workflow

### Git Branch Strategy

```
main (production-ready)
 ├── develop (integration)
 │    ├── feature/user-authentication
 │    ├── feature/task-gamification
 │    ├── feature/ai-mentor-chat
 │    ├── fix/task-progress-bug
 │    └── docs/api-specification
 └── hotfix/critical-security-patch
```

### Branch Naming Conventions

- `feature/{description}` - New features
- `fix/{description}` - Bug fixes
- `docs/{description}` - Documentation updates
- `refactor/{description}` - Code refactoring
- `test/{description}` - Test additions

### Commit Format

Follow [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<scope>): <subject>

feat(user-service): add JWT authentication
fix(task-service): resolve task completion logic
docs(api): update endpoint documentation
refactor(content-service): extract RAG utilities
test(ai-gateway): add OpenAI integration tests
```

### Pull Request Process

1. **Create feature branch** from `develop`
2. **Implement** with TDD approach
3. **Open PR** with description, linked issues
4. **Code review** - minimum 1 approval required
5. **CI pass** - all tests and linting must pass
6. **Merge** via squash merge
7. **Delete** feature branch after merge

### TDD Approach

1. Write failing test first
2. Implement minimal code to pass test
3. Refactor while keeping tests green
4. Repeat for each feature

## 6. CI/CD Pipeline

GitHub Actions workflow defined in `.github/workflows/ci.yml`:

```
Push to main
    │
    ▼
┌─────────────────────────────────────────┐
│  1. Checkout code                       │
│  2. Setup Java 17                      │
│  3. Setup Node 20                       │
│  4. Backend: mvn clean verify           │
│  5. Frontend: npm test (CI mode)        │
│  6. Upload test reports                │
└─────────────────────────────────────────┘

Pull Request
    │
    ▼
┌─────────────────────────────────────────┐
│  All above steps PLUS:                  │
│  - Frontend linting (npm run lint)      │
│  - Backend checkstyle                   │
│  - Dependency audit                     │
└─────────────────────────────────────────┘
```

## 7. How to Contribute

### Setting Up Development Environment

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-org/ai-growth-partner.git
   cd ai-growth-partner
   ```

2. **Start infrastructure services**
   ```bash
   cd infra
   cp .env.example .env
   docker-compose up -d
   ```

3. **Setup backend**
   ```bash
   cd backend
   # Import as Maven project in IDE
   # Or use command line:
   mvn clean install
   mvn spring-boot:run -pl user-service  # Run individual service
   ```

4. **Setup frontend**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

### Code Standards

- **Java**: Follow Spring Boot conventions, use Lombok, Immutables
- **TypeScript**: Strict mode, functional style, no `any`
- **File sizes**: <400 lines per file, <50 lines per function
- **Testing**: 80%+ coverage, unit + integration tests
- **API responses**: Unified format `{code: 0, data: {...}, message: "success"}`

### Running Tests

```bash
# Backend
cd backend
mvn clean test

# Frontend
cd frontend
npm test
npm run lint
```

### Submitting Changes

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/my-feature`
3. Make changes with tests
4. Commit using conventional format
5. Push and open PR against `develop` branch
6. Address review feedback
7. Merge after approval

### Getting Help

- Read the [CLAUDE.md](./CLAUDE.md) for project overview
- Check [docs](./docs/) for product specifications
- Open an issue for bugs or feature requests
