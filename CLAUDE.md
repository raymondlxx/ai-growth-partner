# AI Growth Partner — Project Instructions

## Project Overview

**Name:** AI人生成长伴侣 (AI Life Growth Partner)
**Type:** Full-stack AI-powered SaaS platform
**Phase:** MVP (0–6 months)
**Team:** 5–7 people (product, engineering, design/ops)

## Product Vision

An AI-powered full-lifecycle growth ecosystem that helps users achieve self-iteration across career, cognition, emotion, and health dimensions. Core differentiation: "meta-path" concept (skill paths tied to life stages) + gamified tasks + AI mentor companionship.

## Core Feature Modules (Priority Order)

| Module | Description | Priority |
|--------|-------------|----------|
| AI Mentor | Conversational AI guidance with emotional computation | P0 |
| User Profile | Holographic multi-dimensional user画像 | P0 |
| Path Planning | AI-driven skill-to-career path engine | P0 |
| Task System 3.0 | Gamified XP/level/badge task quests | P0 |
| Growth Knowledge Base | Personal knowledge沉淀 + content co-creation | P1 |
| Social Network | Interest groups, mentor matching, growth rankings | P2 |

## Architecture

- **Backend:** Java/Kotlin + Spring Boot microservices
- **Frontend:** Next.js (Web) + Flutter/Taro (Mobile)
- **Database:** MySQL + Redis + MongoDB + ClickHouse + Milvus
- **AI:** GPT-4 / Claude via OpenAI-compatible API + RAG
- **Infrastructure:** Docker + Kubernetes (docker-compose for MVP)

## Project Structure

```
ai-growth-partner/
├── backend/              # Spring Boot microservices
│   ├── user-service/     # User & profile management
│   ├── task-service/     # Gamified task system
│   ├── path-service/      # Path planning engine
│   ├── content-service/   # Knowledge base & content
│   ├── social-service/    # Social features
│   ├── ai-gateway/        # AI middleware (OpenAI/Anthropic)
│   └── common/            # Shared libraries, DTOs, utils
├── frontend/              # Next.js web app
│   ├── app/               # App router pages
│   ├── components/        # Reusable UI components
│   ├── features/          # Feature modules
│   └── lib/               # Utilities, API clients
├── infra/                 # Docker, k8s manifests
├── docs/                  # Product documentation (already exists)
└── openspec/              # Change management (already exists)
```

## Tech Stack Conventions

- **Java:** 17 LTS, Spring Boot 3.x, Maven/Gradle (Kotlin DSL)
- **Node.js:** 20+, TypeScript strict mode, ESM
- **Frontend:** Next.js 14 App Router, Tailwind CSS, shadcn/ui, TanStack Query
- **Database:** MySQL 8 (transactional), MongoDB (documents), Redis (cache/session)
- **AI:** LangChain4j (Java) for backend AI orchestration, OpenAI SDK for Node.js
- **API:** RESTful with OpenAPI 3.0 specs,统一响应封装 `{code, data, message}`
- **Auth:** JWT with refresh tokens, RBAC

## Coding Standards

- **Immutability:** Always create new objects, never mutate existing ones
- **File size:** <400 lines per file, <50 lines per function
- **Error handling:** All exceptions caught, user-friendly messages in API responses
- **Testing:** TDD with 80%+ coverage, unit + integration + e2e
- **Commits:** Conventional Commits (`feat:`, `fix:`, `docs:`, `refactor:`, `test:`)

## MVP Scope (Phase 1 — 0 to 6 months)

**Goal:** Validate PMF, DAU>1000, retention>30%, budget ¥200K–400K

### Must Have (MVP)

1. User registration/login (JWT, email + OAuth)
2. Holographic user profile (basic dimensions)
3. AI path recommendation (3 predefined career paths)
4. Gamified task system (XP, levels, badges)
5. AI mentor chat (GPT-4 powered, context-aware)
6. Basic knowledge base (RAG-powered)

### Nice to Have (Post-MVP)

- Social features (groups, rankings)
- Mobile apps
- Advanced gamification
- Content co-creation

## OpenSpec Workflow

This project uses OpenSpec for change management:

- `/opsx:explore` — Investigate problems, think through ideas (read-only)
- `/opsx:propose` — Create change proposal with design and tasks
- `/opsx:apply` — Implement tasks from a change
- `/opsx:archive` — Finalize and archive completed changes

## Key Metrics (MVP)

- DAU > 1000
- Monthly retention > 30%
- Task completion rate > 60%
- AI mentor NPS > 40
- Bug-free rate > 99%

## Contact

Product Owner: (to be filled)
Tech Lead: (to be filled)
