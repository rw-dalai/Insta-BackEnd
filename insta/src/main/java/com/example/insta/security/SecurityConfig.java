package com.example.insta.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Configuration to tell Spring that this is a configuration class which contains beans (@Bean)
// @Bean to tell Spring that this method returns a bean

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
public class SecurityConfig {
  // https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html#argon2id
  // https://auth0.com/blog/hashing-passwords-one-way-road-to-security/
  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    // return new BCryptPasswordEncoder();
    // return new Argon2PasswordEncoder();
  }
}
