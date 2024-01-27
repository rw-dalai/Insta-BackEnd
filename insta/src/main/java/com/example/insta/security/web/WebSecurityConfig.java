package com.example.insta.security.web;

import com.example.insta.domain.user.Role;
import com.example.insta.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

// Inversion of Control (IoC)
// --------------------------------------------------------------------------------------------
// IoC design principle in which the control of objects is transferred to the framework.
// The framework manages the creation of objects, the objects' lifecycle, and object dependencies.
// IoC uses DI (Dependency Injection) to implement IoC.
// IoC uses Live Cycle Events to give the developer more control over the application.

// Dependency Injection (DI)
// --------------------------------------------------------------------------------------------
// DI is a mechanism to implement IoC.
// The container injects the dependency into the object.
// (e.g. PasswordEncoder -> PasswordService)
// _Constructor Injection_ is the preferred way that a bean is injected into another bean.
// _Setter Injection_ is another way that a bean is injected into another bean.

// Dependency Tree
// --------------------------------------------------------------------------------------------
// Spring constructs a dependency tree of all beans.
// The dependency tree is used to determine the order in which beans are created.
//    Password Encoder
//           ↓
//    Password Service
//           ↓
//  User Registration Service

// POJO vs Bean
// --------------------------------------------------------------------------------------------
// A Plain Old Java Object (POJO) is an ordinary Java object
// A Bean is a Spring managed object (instance of a class).

// What is a Bean?
// --------------------------------------------------------------------------------------------
// A Bean is a Spring managed object (instance of a class).
// Spring manages the lifecycle of a bean. From creation to destruction.
// Spring can inject a Bean into another Bean (-> Dependency Injection).
// e.g. PasswordService is a Bean (@Service), and we can inject the PasswordEncoder (@Bean) into it.
// There are usually two ways to create a Bean:
// 1. Annotate a class with @Component, @Service, @Repository, @Controller, @RestController, etc.
// 2. Annotate a method with @Bean in a @Configuration class and return an object from that method.

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Configuration to tell Spring that this is a configuration class which contains beans (@Bean)
// @Bean to tell Spring that this method returns a bean

// AAA-Triad
// Authentication
// Authorization
// Accounting

// CIA-Triad
// Confidentiality
// Integrity
// Availability

// Authentication Mechanism
// Basic Authentication
// Cookie Based / Persistence Tokens

// Auth2 / JWT
// Passwordless Authentication (FIDO)
// LDAP
// OpenId

// Credentials

// GrantedAuthority
// "Permission" or "Right"

// Role
// GrantedAuthority
// ROLE_XXX
// "ROLE_ADMIN"
// "ROLE_USER"

// 1. UserDetails (Interface)
//   Represents a principal (user) for Spring Security
//   GrantedAuthorities
//   disabling: email is not verified, admin disables an account
//   locking: number of failed login attempts

// 2. UserDetailsService (Interface)
//   To load user data from a specific source (database) during authentication.
//   Returns a UserDetails to Spring Security

// 3. Security Config
//    Configure SecurityFilterChain

@Configuration
public class WebSecurityConfig {

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return new DaoUserDetailsService(userRepository);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // AUTHENTICATION
    http.httpBasic(Customizer.withDefaults());
    http.formLogin(formLogin -> formLogin.disable());

    // AUTHORIZATION
    http.authorizeHttpRequests(
        (authorize) ->
            authorize
                .requestMatchers("/api/registration/**")
                .permitAll()
                .requestMatchers("/api/user/**")
                .hasRole(Role.USER.toString())
                .anyRequest()
                .authenticated());

    // CSRF
    http.csrf(csrf -> csrf.disable());

    // CORS
    http.cors(cors -> cors.disable());

    // SESSIONS
    http.sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    DefaultSecurityFilterChain filterChain = http.build();
    return filterChain;
  }
}
