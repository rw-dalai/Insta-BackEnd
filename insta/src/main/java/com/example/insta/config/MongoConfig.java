package com.example.insta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Configuration to tell Spring that this is a configuration class which contains beans
// @EnableMongoAuditing to tell Spring to enable auditing for MongoDB entities (@CreatedDate, etc.)

// What is a Bean?
// --------------------------------------------------------------------------------------------
// A Bean is a Spring managed object (instance of a class).
// Spring manages the lifecycle of a bean. From creation to destruction.
// Spring can inject a Bean into another Bean (-> Dependency Injection).
// e.g. PasswordService is a Bean (@Service), and we can inject the PasswordEncoder (@Bean) into it.
// There are usually two ways to create a Bean:
// 1. Annotate a class with @Component, @Service, @Repository, @Controller, @RestController, etc.
// 2. Annotate a method with @Bean in a @Configuration class and return an object from that method.

@Configuration
@EnableMongoAuditing
public class MongoConfig {}
