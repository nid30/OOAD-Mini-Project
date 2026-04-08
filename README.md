# SkillShare Application

A Spring Boot-based skill-sharing platform that enables students to register, offer skills, book mentorship sessions, provide feedback, and receive notifications.

## Features

- **Student Management**: User registration, login, and skill management
- **Mentorship Sessions**: Book and manage mentorship sessions with mentors
- **Feedback System**: Submit and view feedback for mentorship sessions
- **Notifications**: Real-time notifications using Observer pattern
- **Search**: Find mentors by skill

## Tech Stack

- **Backend**: Spring Boot 4.0.4
- **Database**: MySQL
- **Frontend**: Thymeleaf + HTML/CSS
- **Build Tool**: Maven
- **Java Version**: 17

## Project Structure

```
skillshareapp/
├── src/main/java/com/skillshare/skillshareapp/
│   ├── model/          # Data models (Student, Feedback, Notification, etc.)
│   ├── service/        # Business logic (StudentService, SessionService, etc.)
│   ├── repository/     # Data access layer
│   └── controller/     # REST controllers
├── src/main/resources/
│   ├── templates/      # Thymeleaf HTML templates
│   ├── static/css/     # CSS files
│   └── application.properties
└── pom.xml
```

## Prerequisites

- Java 17 or higher
- MySQL 8.0+
- Maven 3.8+

## Setup

### 1. Clone the Repository
```bash
git clone https://github.com/nid30/OOAD-Mini-Project.git
cd OOAD-Mini-Project
```

### 2. Configure MySQL Database

Create a MySQL database:
```sql
CREATE DATABASE skillshare;
```

Update `src/main/resources/application.properties` with your credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/skillshare
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build the Project

```bash
./mvnw clean package
```

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

### 5. Access the Application

Open your browser and navigate to:
```
http://localhost:8080
```


## License

This project is for educational purposes.
