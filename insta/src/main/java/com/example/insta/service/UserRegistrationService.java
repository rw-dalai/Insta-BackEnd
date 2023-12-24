package com.example.insta.service;

import static com.example.insta.domain.user.Role.USER;

import com.example.insta.domain.user.Profile;
import com.example.insta.domain.user.User;
import com.example.insta.email.EmailService;
import com.example.insta.persistence.UserRepository;
import com.example.insta.presentation.commands.Commands.UserRegistrationCommand;
import com.example.insta.presentation.commands.Commands.UserVerificationCommand;
import com.example.insta.security.PasswordService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Service to mark this class as a Spring service
// @RequiredArgsConstructor Lombok annotation that generates a constructor with all required fields.

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
  private final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationService.class);

  private final PasswordService passwordService;
  private final EmailService emailService;
  private final UserRepository userRepository;

  // TODO Edge Case
  // 1. Attempt:
  // User A registers: wenz@spengergassse.at
  // User does not exist
  // User A receives Email: /api/register/verify?userId=123&tokenId=456
  // User A does not click on the email verification link

  // 2. Attempt:
  // User A registers again: wenz@spengergasse.at
  // User A exists, but not verified yet

  public User register(UserRegistrationCommand command) {
    LOGGER.info("User registration with email {}", command.email());

    // 1. Check if email is already taken
    if (userRepository.existsByEmail(command.email()))
      throw new RuntimeException("Email already taken: " + command.email());

    // 2. Check password strength / hash password
    var encodedPassword = passwordService.encode(command.password());

    // 3. Instantiate/save user (with account disabled!) in DB
    var profile = new Profile(command.firstName(), command.lastName());
    var user = new User(command.email(), encodedPassword, USER, profile);
    var savedUser = userRepository.save(user);

    // 4. Send Email (possibly asynchronous, it means in the background)
    emailService.sendVerificationEmail(savedUser);

    LOGGER.info("User registration with email {} successful", command.email());
    return savedUser;
  }

  // public void verify(String userId, String tokenId)
  public void verify(UserVerificationCommand command) {
    LOGGER.info("User verification with id {} and token {}", command.userId(), command.tokenId());

    User user =
        userRepository
            .findById(command.userId())
            .orElseThrow(
                () -> new IllegalArgumentException("User not found with id" + command.userId()));

    user.getAccount().verifyToken(command.tokenId());
    user.getAccount().setEnabled(true);
    userRepository.save(user);

    LOGGER.info(
        "User verification with id {} and token {} successfully",
        command.userId(),
        command.tokenId());

    // Functional / Declarative Solution
    // Optional<User> userOptional = userRepository.findById(command.userId());
    // User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));

    // Imperative Solution
    // if (user.isEmpty()) {
    //  throw new IllegalArgumentException("User not found");
    // ...
    // }
  }
}
