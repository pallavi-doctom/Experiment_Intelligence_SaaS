# Experiment Intelligence SaaS - Development Plan

## Development Workflow

From this point forward, we will commit code incrementally while building — not at the end of a phase.
**Rule:** 1 logical change = 1 commit = 1 push.

Every time we finish a small unit of work (an entity, a migration, an endpoint, a config), we immediately:
```bash
git add auth-service/...
git add experiment-service/...
# (Use targeted staging to prevent accidentally committing unrelated files)
git commit -m "type(scope): description"
git push
```

### Commit Message Format
`type(scope): description`

- **Types:** `feat`, `fix`, `infra`, `ci`, `refactor`, `test`
- **Scopes:** `auth`, `experiment`, `analytics`, `infra`, `ci`

**Examples (Auth Service phase):**
- `feat(auth): add organization entity`
- `feat(auth): add user entity and repository`
- `feat(auth): add flyway migration for auth_db`
- `feat(auth): implement password hashing with BCrypt`
- `feat(auth): implement JWT token generation`
- `feat(auth): add POST /auth/register endpoint`
- `feat(auth): add POST /auth/login endpoint`
- `feat(auth): add JwtAuthFilter for Spring Security 6`
- `feat(auth): add RBAC configuration`

### Important Rules:
1. Never bundle more than one logical unit into a single commit.
2. Always commit before switching to a different task.
3. Never commit secrets, passwords, JWT keys, or `.env` files — use environment variables.
4. After every commit, push immediately.

This applies to all remaining phases: Auth Service, Experiment Service, Analytics Service, Containerization, and Hardening.

---

This document outlines the 6 logical phases for developing the Experiment Intelligence multi-tenant SaaS platform.

## Phase 1: Project Scaffold and Infrastructure
- **Objective:** Establish the project structure and foundational infrastructure.
- **Tasks:**
  - Initialize the Maven multi-module project
  - Create service modules:
    - `auth-service`
    - `experiment-service`
    - `analytics-service`
  - Configure `docker-compose.yml` with isolated MySQL databases:
    - `auth_db`
    - `experiment_db`
    - `analytics_db`
  - Configure Spring Boot applications with separate ports:
    - 8081 -> Auth Service
    - 8082 -> Experiment Service
    - 8083 -> Analytics Service
  - Set up the initial GitHub Actions CI pipeline

## Phase 2: Authentication Service and Tenant Identity
- **Objective:** Implement tenant registration, authentication, and role-based access control.
- **Tasks:**
  - Create database entities:
    - `Organization`
    - `User`
  - Add Flyway migrations for the `auth_db`
  - Implement tenant signup API that provisions a new organization and admin user
  - Implement login API with BCrypt password hashing
  - Implement JWT generation and validation
    - JWT must contain the following claims:
      - `user_id`
      - `organization_id`
      - `role`
  - Configure Spring Security RBAC

## Phase 3: Experiment Service and Core Experiment Operations
- **Objective:** Allow tenants to define experiments and record experiment runs.
- **Tasks:**
  - Create entities:
    - `Experiment`
    - `ExperimentRun`
    - `Metric`
  - Implement strict tenant isolation by filtering all queries using `organization_id`
  - Implement APIs for:
    - creating experiments
    - listing experiments
    - recording experiment runs
    - recording experiment metrics

## Phase 4: Analytics Service and Experiment Insights
- **Objective:** Provide aggregated experiment analytics for tenants.
- **Tasks:**
  - Implement analytics endpoints
  - Retrieve experiment data using REST API calls to `experiment-service`
  - Compute aggregated statistics such as:
    - experiment success rate
    - run counts
    - metric averages
  - Expose reporting endpoints

## Phase 5: Containerization and CI/CD
- **Objective:** Package services for deployment and automate builds.
- **Tasks:**
  - Create a Dockerfile for each microservice
  - Extend `docker-compose.yml` to run the full platform
  - Configure GitHub Actions pipeline to:
    - build Maven modules
    - run tests
    - build Docker images

## Phase 6: Hardening and Observability
- **Objective:** Prepare the system for production-level stability and monitoring.
- **Tasks:**
  - Add Spring Boot Actuator health endpoints
  - Implement structured logging
  - Validate JWT security and RBAC enforcement
  - Add integration tests for cross-service workflows
    - Example integration flow:
      `register -> login -> create experiment -> run experiment -> analytics`
