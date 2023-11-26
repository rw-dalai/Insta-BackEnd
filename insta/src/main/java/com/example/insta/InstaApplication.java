package com.example.insta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// What makes Spring so special?
// --------------------------------------------------------------------------------------------
// Spring is a framework that helps us to build server-side applications.
// It is an open-source framework created by Rod Johnson in 2003.
// Amazon, Netflix, Uber, etc. are using Java/Spring in their backend.
// ---------------------------------------------------------------------------------------------
// Convention over Configuration: Spring provides sensible defaults (e.g. Security).
// Standalone: Enables building applications that run with an embedded server (e.g. Tomcat).
// Dependency Injection: Enables loose coupling between components (e.g. constructor injection).
// Testable: Provides support for integration and unit testing (e.g. JUnit, Mockito).
// Spring Boot Starters: Offers ready-to-use dependencies (e.g. Spring Data JPA, Spring Security).
// Microservices Readiness: Ideally suited for building microservices (e.g. Spring Cloud).
// Data Access: Supports a wide range of SQL and NoSQL data access technologies (e.g. MongoDB)

// @SpringBootApplication is a convenience annotation that adds all the following:
// --------------------------------------------------------------------------------------------
// - @Configuration
//   It marks this class as a source of beans (@Bean).
// - @EnableAutoConfiguration
//   Tells Spring to auto-configure the application based on dependencies e.g. build.gradle.
// - @ComponentScan
//   Tells Spring to scan for other components, configuration and services in the package.

@SpringBootApplication
public class InstaApplication {
  public static void main(String[] args) {
    // Starts the Spring application.
    SpringApplication.run(InstaApplication.class, args);
  }
}
