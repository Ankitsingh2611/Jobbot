# JobBot

A Spring Boot application that helps me manage my job search. It collects Java job listings from the Remotive API, scores each one against my skills, and tracks the status of every application.

## Features
- Collects jobs through the Remotive API and skips duplicates
- Scores jobs by keywords: bonuses for Java, Spring Boot, JPA, and junior or fresher roles, and penalties for senior roles, long experience requirements, and MCA-only listings
- Tracks status (NEW, APPLIED, INTERVIEW, REJECTED) with automatic applied and follow-up dates
- Returns jobs ranked by score through a REST API

## Tech Stack
Java 21, Spring Boot, Spring Data JPA, Hibernate, MySQL, Maven

## Endpoints
| Method | Endpoint | Description |
|---|---|---|
| GET | /jobs | List all jobs |
| GET | /jobs/top | Jobs ranked by score |
| POST | /jobs | Add a job |
| PATCH | /jobs/{id}/status | Update application status |
| POST | /collect?keyword=java | Fetch new jobs |

## Run Locally
1. Create a MySQL database: `CREATE DATABASE jobbot;`
2. Set the environment variable `DB_PASSWORD` to your MySQL root password
3. Run `JobbotApplication`, then open `http://localhost:8080/jobs/top`

## Planned
- Additional job sources for junior Java roles in India
- Simple UI and Swagger documentation
- Deployment
