# Spring Boot 3.0 Security with JWT Implementation
This project demonstrates the implementation of security using Spring Boot 3.0 and JSON Web Tokens (JWT). It includes the following features:

## Features
* User registration and login with JWT authentication
* Password encryption using BCrypt
* Role-based authorization with Spring Security
* Customized access denied handling
* Logout mechanism
* Refresh token
* sending emails to the user using smtp.gmail.com

## Technologies
* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* Database: The application uses an H2 database by default. For production, configure your preferred database in the `application.properties` file.


📚 Database Overview

This project features a complex relational database designed to support a rich educational platform. The system uses Hibernate for ORM and includes the following key relationships:

    Users are generated and assigned

    Each user is enrolled in multiple Courses

    Each course contains several Lectures

    Each lecture includes Flashcards and Quizzes

    Quizzes are composed of multiple Questions

    Each question offers several Multiple Choice Answers

In addition to the entities listed above, the database includes even more interconnected entities to support advanced functionality and scalability.

You can view the full database structure in the attached image, which illustrates the depth and complexity of the schema. The actual entities are implemented using Hibernate in the project.

 
![Screenshot 2025-05-20 155517](https://github.com/user-attachments/assets/bf4d00cc-dc1b-4f20-824d-d280fd7903c5)



### 🛠️ Database Setup

1. **Install and start PostgreSQL**  
   Make sure PostgreSQL is installed and running on your system.

2. **Create the database**  
   ```sql
   CREATE DATABASE jwt_security;
   ```

3. **Configure database credentials**  
   Open the configuration file at `src/main/resources/application.yml` and update it as follows:

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/jwt_security
       username: your_db_username
       password: your_db_password
   ```

---

### 🚀 Running the Application

1. **Build the project using Maven**  
   ```bash
   mvn clean install
   ```

2. **Run the application**  
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application**  
   Open your browser and go to:  
   [http://localhost:8080](http://localhost:8080)

---

### 📄 API Documentation

Swagger UI is available at:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

### 🔐 Environment Variables

For sensitive information like API keys or email credentials, use environment variables instead of hardcoding them.

Update your `application.yml`:

```yaml
spring:
  mail:
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
```

Then set the environment variables in your terminal:

```bash
export MAIL_USERNAME=your_email
export MAIL_PASSWORD=your_password
```

> 💡 Tip: Add the above `export` lines to your shell config file (`~/.bashrc`, `~/.zshrc`, etc.) to persist them.




