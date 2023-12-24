package com.example.insta.presentation;

import static com.example.insta.presentation.commands.Commands.*;

import com.example.insta.domain.user.User;
import com.example.insta.presentation.commands.Commands.UserRegistrationCommand;
import com.example.insta.service.UserRegistrationService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Annotations used?
// --------------------------------------------------------------------------------------------
// @RestController marks this class that handles HTTP requests and returns JSON responses.
// @RequestMapping(..) maps HTTP requests with the given path to the controller methods.
// @RequiredArgsConstructor Lombok annotation that generates a constructor with all required fields.
// @RequestBody marks a method parameter as the body of the HTTP request.

// @RequestBody: Http Body (Json/XML) -> HTTP Message Converter (Jackson/Deserialize) -> Command
// @PathVariable HTTP Path -> Method Parameters
// @RequestParam HTTP Query Params -> Method Parameters
// @ModelAttribute Bind any Request objects -> Command

// HTTP Message Converter ?
// --------------------------------------------------------------------------------------------
// Default Message Converter = Jackson
// HTTP Request => Deserialization (JSON -> Object)
// HTTP Response => Serialization (Object -> JSON)

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class UserRegistrationController {
  private final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationController.class);

  private final UserRegistrationService userRegistrationService;

  // HTTP Request:
  // POST /api/registration HTTP/1.1
  // Host: localhost:8080
  // Content-Type: application/json
  // Content-Length: xxx
  // {"email": "xxx", "password": "xxx", "firstName": "xxx", "lastName": "xxx"}

  // HTTP Response:
  // HTTP 1.1 CREATED
  // Location: /api/user/123
  // Body: { User }

  @PostMapping
  public ResponseEntity<User> register(@RequestBody UserRegistrationCommand command) {
    // TODO Just for testing, never ever log sensitive data !
    LOGGER.debug("User registration controller#register {}", command);

    var userRegistered = userRegistrationService.register(command);

    // Create Location Header
    // Location: /api/user/123
    URI uri = URI.create("/api/user/" + userRegistered.getId());
    return ResponseEntity.created(uri).body(userRegistered);
  }

  // HTTP Request:
  // GET /api/registration/verify?userId=123&tokenId=456" HTTP/1.1
  // Host: localhost:8080

  // HTTP Response:
  // HTTP 1.1 OK

  // public void verify(@PathVariable String userId)
  // public void verify(@RequestParam String userId, @RequestParam String tokenId)
  @GetMapping("/token")
  public void verify(@ModelAttribute UserVerificationCommand command) {
    LOGGER.debug("User registration controller#verify {}", command);

    // var command = new UserVerificationCommand(userId, tokenId);
    // userRegistrationService.verify(userId, tokenId);
    userRegistrationService.verify(command);
  }
}
