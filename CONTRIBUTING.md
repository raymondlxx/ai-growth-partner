# Contributing to AI Growth Partner

Thank you for your interest in contributing to AI Growth Partner!

## Development Environment Setup

### Prerequisites

- Docker 20.x or later
- Docker Compose 2.x or later
- Java 17 LTS
- Node.js 20.x or later
- Maven 3.9+ or Gradle 8+

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-org/ai-growth-partner.git
   cd ai-growth-partner
   ```

2. **Start infrastructure services**
   ```bash
   cd infra
   cp .env.example .env
   # Edit .env with your API keys
   docker-compose up -d
   ```

3. **Start backend services**
   ```bash
   cd backend
   mvn clean install
   # Run services in IDE or via Maven
   mvn spring-boot:run -pl user-service
   ```

4. **Start frontend**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

## Branch Naming

Use descriptive branch names with prefixes:

| Prefix | Purpose | Example |
|--------|---------|---------|
| `feature/` | New features | `feature/user-authentication` |
| `fix/` | Bug fixes | `fix/task-completion-bug` |
| `docs/` | Documentation | `docs/api-specification` |
| `refactor/` | Code refactoring | `refactor/extract-common-utils` |
| `test/` | Test additions | `test/add-ai-gateway-tests` |
| `hotfix/` | Critical production fixes | `hotfix/security-patch` |

## Commit Messages

Follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

```
<type>(<scope>): <subject>

Types:
  feat    - New feature
  fix     - Bug fix
  docs    - Documentation changes
  style   - Code style changes (formatting, semicolons, etc)
  refactor - Code refactoring
  test    - Adding or updating tests
  chore   - Maintenance tasks
```

### Examples

```bash
feat(user-service): add JWT refresh token support
fix(task-service): resolve XP calculation for completed tasks
docs(api): update endpoint documentation for user profile
refactor(content-service): extract RAG utilities to common module
test(ai-gateway): add integration tests for OpenAI API
```

## Pull Request Process

### Before Opening a PR

1. **Sync with latest `develop`**
   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b feature/my-feature
   git rebase develop
   ```

2. **Run tests locally**
   ```bash
   # Backend
   cd backend && mvn clean test

   # Frontend
   cd frontend && npm test -- --ci
   ```

3. **Run linting**
   ```bash
   cd frontend && npm run lint
   cd backend && mvn checkstyle:check
   ```

### PR Requirements

- [ ] Branch name follows conventions
- [ ] Commit messages follow conventional format
- [ ] Code passes all tests
- [ ] Linting passes with no errors
- [ ] New code has test coverage (>80%)
- [ ] Documentation updated if needed
- [ ] Linked to relevant issue

### PR Description Template

```markdown
## Summary
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
Describe how this was tested

## Checklist
- [ ] My code follows the style guidelines
- [ ] I have performed a self-review
- [ ] I have commented my code where needed
- [ ] I have updated the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix is effective
- [ ] New and existing tests pass locally
```

### Code Review Requirements

- **Minimum 1 approval** from maintainers
- **All CI checks must pass**
- **No unresolved conversations**
- **Linear history** (squash merge preferred)

### After Approval

1. Maintainer will squash and merge
2. Delete feature branch:
   ```bash
   git push origin --delete feature/my-feature
   ```

## Coding Standards

### Java / Spring Boot

- Use **Lombok** for boilerplate reduction
- **Immutability**: Use final fields, builder patterns
- **File size**: <400 lines per file
- **Function size**: <50 lines per function
- **Error handling**: All exceptions caught, user-friendly messages
- **Logging**: Use appropriate log levels (INFO, DEBUG, ERROR)

### TypeScript / Next.js

- **Strict mode** enabled
- **No `any`** type usage
- **Functional style** preferred
- **File size**: <400 lines
- **Function size**: <50 lines
- **Naming**: camelCase for variables/functions, PascalCase for components

### API Design

```json
// Unified response format
{
  "code": 0,
  "data": { ... },
  "message": "success"
}

// Error response
{
  "code": 40001,
  "data": null,
  "message": "Invalid parameter: email format"
}
```

## Testing Requirements

- **Unit tests**: Core business logic
- **Integration tests**: Database, API endpoints
- **Coverage target**: 80%+ line coverage
- **Naming**: `<ClassName>.test.ts` / `<ClassName>Test.java`

## Getting Help

- Open an issue for bugs or feature requests
- Check existing documentation in `docs/`
- Review `CLAUDE.md` for project context

## License

By contributing, you agree that your contributions will be licensed under the project license.
