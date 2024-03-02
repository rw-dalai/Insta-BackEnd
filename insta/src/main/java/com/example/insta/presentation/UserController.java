package com.example.insta.presentation;

import com.example.insta.domain.user.User;
import com.example.insta.security.web.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// GET /api/user
// JSON { USER }

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationController.class);

  @GetMapping
  public User login(@AuthenticationPrincipal SecurityUser principal) {
    LOGGER.debug("User controller#login {}", principal);
    // userService.login(principal.getUser())
    return principal.getUser();
  }
}
