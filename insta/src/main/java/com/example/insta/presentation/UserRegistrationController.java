package com.example.insta.presentation;

import com.example.insta.domain.user.User;
import com.example.insta.presentation.commands.Commands.UserRegistrationCommand;
import com.example.insta.service.UserRegistrationService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST
// --------------------------------------------------------------------------------------------
// REST = Representational State Transfer
// - An architectural style for designing networked applications.
// - Popularized by Dr. Roy Fielding in his doctoral dissertation in 2000.
// https://www.ics.uci.edu/~fielding/pubs/dissertation/fielding_dissertation.pdf

// Representational = Representation of Data
// - Refers to the format in which data is presented. For instance, data could be represented as
// JSON, XML, HTML, or other formats.

// State = State of the Application
// - Relates to the current condition or data of an application at any given point in time.
// REST emphasizes that interactions should be stateless, meaning every request from
// a client contains all the necessary information for processing.

// Transfer = Transfer of Data
// - Describes the action of sending data from one entity to another.
// In REST, data gets transferred between a client and server using
// standard conventions and protocols, usually HTTP.

// Restful Url Design
// --------------------------------------------------------------------------------------------
// VERB        URI
// GET        /api/todo/         fetch all todos
// GET        /api/todo/{123}    fetch specific todo
// POST       /api/todo/         create todo
// PUT        /api/todo/{123}    update/replaces a specific todo
// DELETE     /api/todo/{123}    deletes a specific todo

// HTTP Anatomy
// --------------------------------------------------------------------------------------------
// https://www.webdevdrops.com/en/http-primer-for-frontend-developers-f091a2070637/
// An HTTP request consists of a request line, headers, and a body.

// Request Line:
// POST /api/registration HTTP/1.1

// Headers:
// Host: localhost:8080
// Content-Type: application/json
// Content-Length: 123

// Body:
// {"email": "xxx", "password": "xxx", "firstName": "xxx", "lastName": "xxx"}

// HTTP Status Codes
// --------------------------------------------------------------------------------------------
// 2xx: Success
// 4xx: Client Error
// 5xx: Server Error

// Annotations used?
// --------------------------------------------------------------------------------------------
// @RestController marks this class that handles HTTP requests and returns JSON responses.
// @RequestMapping(..) maps HTTP requests with the given path to the controller methods.
// @RequiredArgsConstructor Lombok annotation that generates a constructor with all required fields.
// @RequestBody marks a method parameter as the body of the HTTP request.

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class UserRegistrationController {
  private final UserRegistrationService userRegistrationService;

  // HTTP Request:
  // POST /api/registration HTTP/1.1
  // Host: localhost:8080
  // Content-Type: application/json
  // Content-Length: 123
  // {"email": "xxx", "password": "xxx", "firstName": "xxx", "lastName": "xxx"}

  // HTTP Response:
  // HTTP 1.1 CREATED
  // Location: /api/user/123
  // Body: { User }

  @PostMapping
  public ResponseEntity<User> register(@RequestBody UserRegistrationCommand command) {
    var userRegistered = userRegistrationService.register(command);

    // URI locationURI = ServletUriComponentsBuilder.fromCurrentRequestUri().path()
    String locationURI = "/api/user/" + userRegistered.getId();
    URI uri = URI.create(locationURI);

    return ResponseEntity.created(uri).body(userRegistered);
  }
}
