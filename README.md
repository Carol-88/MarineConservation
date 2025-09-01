This project is a **Spring Boot application for marine conservation management** that integrates a MySQL database and organizes conservation events, projects, species, and habitats. 
## Project Overview

Marine Conservation is a web-based system designed to manage data on conservation events, projects, marine species, and natural habitats. The application helps organizations and researchers track initiatives and environmental information efficiently.[^4][^5][^6][^7][^2][^3][^1]

## Features

- **Spring Boot** backend for rapid development and reliable architecture.[^2]
- **MySQL** database integration, supporting automatic creation if the database does not exist.[^3]
- **JPA/Hibernate** for ORM and schema generation.[^3]
- Management of marine species, habitats, conservation projects, and events with extensible domain models.[^5][^6][^7][^4]


## Getting Started

### Prerequisites

- **Java 17** or newer.
- **MySQL Server** running locally on port 3306.
- **Maven** (wrapper scripts provided: `mvnw` for Unix, `mvnw.cmd` for Windows).[^8][^9]


### Database Setup

Update your MySQL server with the following credentials or change them in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/marine-conservation?createDatabaseIfNotExist=true 
spring.datasource.username=root
spring.datasource.password=ironhack
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```


### Building and Running

Use the Maven wrapper to build and start the application:

#### On Unix/Linux/macOS:

```sh
./mvnw spring-boot:run
```


#### On Windows:

```bat
mvnw.cmd spring-boot:run
```


### Running Tests

Compile and execute unit tests using:

```sh
./mvnw test
```


## Project Structure

- `src/main/java/marine/conservation/ConservationApplication.java` — Spring Boot main entry point.[^2]
- `src/main/java/marine/conservation/ConservationEvent.java` — Conservation event entity.[^4]
- `src/main/java/marine/conservation/ConservationProject.java` — Conservation project entity.[^5]
- `src/main/java/marine/conservation/Habitat.java` — Habitat entity.[^6]
- `src/main/java/marine/conservation/MarineSpecie.java` — Marine species entity.[^7]
- `src/main/resources/application.properties` — Configuration for database, JPA, and application settings.[^3]
- `pom.xml` — Maven configuration and dependencies.[^1]


## Configuration

- **Application Name**: `marineConservation`[^3]
- **Database**: Configurable in `application.properties`; defaults to `root` user and `ironhack` password.[^3]
- **JPA**: Schema is generated on application start (`create-drop`), showing SQL queries for debugging.[^3]


## License

Licensed under the Apache License, Version 2.0.[^9][^8]

***

<div style="text-align: center">⁂</div>

[^1]: pom.xml

[^2]: ConservationApplication.java

[^3]: application.properties

[^4]: ConservationEvent.java

[^5]: ConservationProject.java

[^6]: Habitat.java

[^7]: MarineSpecie.java

[^8]: mvnw

[^9]: mvnw.cmd

[^10]: ConservationApplicationTests.java

