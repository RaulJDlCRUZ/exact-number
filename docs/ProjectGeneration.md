# Project Generation

> This file could be understood as a Project Constitution Document, but simple

## Maven

Maven version should be 3.8 or higher, regardless of the OS.

```bash
$ mvn -v
Apache Maven 3.8.7
```

## Java

Java version is `21`:

```bash
$ java --version
openjdk 21.0.8 2025-07-15
OpenJDK Runtime Environment (build 21.0.8+9-Ubuntu-0ubuntu124.04.1)
OpenJDK 64-Bit Server VM (build 21.0.8+9-Ubuntu-0ubuntu124.04.1, mixed mode, sharing)

$ javac --version
javac --version
javac 21.0.8
```

## Spring Boot & Spring Initializr

> At September 16th 2025, the latest stable release (not snapshots or similar) of Spring Boot is **3.5.5**. Web used: https://start.spring.io/

- Project: Maven
- Languaje: Java
- Spring Boot: 3.5.5

### Project Metadata

- Group: `com.kangoo`
- Artifact: `cyl`
- Name: `cifras-y-letras`
- Description: Exact number simulator for Java
- Package name: `com.kangoo.cyl` (concatenation of Group + Artifact)
- Packaging: JAR
- Java: 21

### Project Dependencies

- Spring Web. _Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container._

### Application Properties

The file `src/main/resources/application.properties` is another configuration file, used, commonly, to determine the behaviour of the application. For example, can be used to define server settings, database connections, logging levels, security...

At the moment, this file just contains the application name, which is the same as "name" attribute at `pom.xml`.

## Initial Structure

If, initially, we want to start using a typicall 3-layer architecture (Presentation, Domain/Business and Persistance), we could follow this simple but common organization:

```
 ├─ application/        -> CONTROLLERS (REST, Web), DTOs, mappers
 │   └─ controller/
 │   └─ dto/
 │
 ├─ domain/             -> BUSINESS LOGIC
 │   └─ model/          -> Domain Entity (JPA or POJOs)
 │   └─ service/        -> Services (DOMAIN RULES, ALGORYTHMS)
 │
 ├─ infrastructure/     -> PERSISTANCE (external adapters)
 │   └─ repository/     -> DAO Interfaces, JPA Repositories
 │   └─ config/         -> Configuration (if needed, otherwise optional)
```