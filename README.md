# Timer Productivity Dashboard

Java EE productivity timer application with live sessions, statistics dashboard, notes management and interactive reports.

A complete full-stack project built to practice enterprise backend architecture, REST APIs, persistence layer design and frontend integration.

---

## Features

### Timer Management
- Start timer session
- Stop active timer
- Resume paused timer
- End session
- Real-time live timer
- Session state persistence with LocalStorage

### Session History
- View all previous sessions
- Session duration tracking
- Delete sessions
- Add notes to completed sessions

### Statistics Dashboard
- Total sessions
- Total tracked time
- Average session duration
- Best session duration
- Filter statistics by custom date range
- Last sessions overview
- Interactive charts with Chart.js

### Backend Architecture
- RESTful APIs with JAX-RS
- DTO pattern
- Request builder pattern
- JPA persistence layer
- Entity management with Hibernate
- Structured response handling

---

## Tech Stack

### Backend
- Java EE / Jakarta EE
- JAX-RS
- JPA / Hibernate
- WildFly

### Frontend
- HTML5
- CSS3
- Vanilla JavaScript
- Chart.js

### Database
- Relational DB (configured through persistence.xml)

---

## Project Structure

```text
src/main/java
 ├── entity
 ├── service
 ├── restcontroller
 ├── dto
 ├── request
 └── util

src/main/webapp
 ├── index.html
 └── statistics.html
