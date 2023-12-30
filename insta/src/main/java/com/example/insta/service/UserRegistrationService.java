package com.example.insta.service;

import static com.example.insta.domain.user.Role.USER;

import com.example.insta.domain.user.Profile;
import com.example.insta.domain.user.User;
import com.example.insta.email.EmailService;
import com.example.insta.persistence.UserRepository;
import com.example.insta.presentation.commands.Commands.UserRegistrationCommand;
import com.example.insta.presentation.commands.Commands.UserVerificationCommand;
import com.example.insta.security.PasswordService;
import com.example.insta.security.PasswordService.EncodedPassword;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// Purpose of this class?
// --------------------------------------------------------------------------------------------
// This class is a domain service class for registering users.

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Service to mark this class as a Spring service
// @RequiredArgsConstructor Lombok annotation that generates a constructor with all required fields.

// TODO Register Edge Case
// --------------------------------------------------------------------------------------------
// 1. Attempt:
// User A registers: wenz@spengergassse.at
// User does not exist
// User A receives Email: /api/register/verify?userId=123&tokenId=456
// User A does not click on the email verification link
// 2. Attempt:
// User A registers again: wenz@spengergasse.at
// User A exists, but not verified yet

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
  private final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationService.class);

  // Helps us to retrieve users from the database.
  private final UserQueryService userQueryService;

  // Helps us to hash passwords and check password strength.
  private final PasswordService passwordService;

  // Helps us to send emails.
  private final EmailService emailService;

  // Helps us to save users in the database.
  private final UserRepository userRepository;

  // Register User
  // --------------------------------------------------------------------------------------------
  public User register(UserRegistrationCommand command) {
    LOGGER.info("User registration with email {}", command.email());

    // 1. Check if email is not taken if not throw exception
    userQueryService.checkEmailNotTaken(command.email());

    // 2. Check password strength / hash password
    var encodedPassword = passwordService.encode(command.password());

    // 3. Instantiate/save user (with account disabled!) in DB
    var user = createUser(command, encodedPassword);
    var savedUser = userRepository.save(user);

    // 4. Send Email (possibly asynchronous, it means in the background)
    emailService.sendVerificationEmail(savedUser);

    LOGGER.info("User registration with email {} successful", command.email());
    return savedUser;
  }

  // Verify User
  // --------------------------------------------------------------------------------------------
  public void verify(UserVerificationCommand command) {
    LOGGER.info("User account verification with id {}", command.userId());

    // 1. Retrieve user if not throw exception
    User user = userQueryService.findById(command.userId());

    // 2. Verify user if not throw exception
    verifyUser(command, user);

    // 3. Save user in DB
    userRepository.save(user);

    LOGGER.info("User verification with id {} successful", command.userId());
  }

  // Private Helper Methods for Domain Layer Logic
  // --------------------------------------------------------------------------------------------

  private User createUser(UserRegistrationCommand command, EncodedPassword password) {
    var profile = new Profile(command.firstName(), command.lastName());
    var user = new User(command.email(), password, USER, profile);
    user.getAccount().generateEmailTokenFor(command.email());
    // user.getAccount().setEnabled(false); // its already false by default
    return user;
  }

  private void verifyUser(UserVerificationCommand command, User user) {
    user.getAccount().verifyEmailTokenFor(command.tokenId());
    user.getAccount().setEnabled(true);
  }
}
