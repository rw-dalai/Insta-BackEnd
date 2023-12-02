package com.example.insta.service;

import com.example.insta.domain.user.User;
import com.example.insta.email.EmailService;
import com.example.insta.persistence.UserRepository;
import com.example.insta.presentation.commands.Commands.UserRegistrationCommand;
import com.example.insta.security.PasswordService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
  private final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationService.class);

  private final PasswordService passwordService;
  private final EmailService emailService;
  private final UserRepository userRepository;

  //    public void register(String email, String password)
  public User register(UserRegistrationCommand command) {
    // Logging

    // 1. Check if email is already taken
    // UserRepository.existsByEmail -> true/false
    // -> true -> Exception

    // 2. Check password strength / hash password
    // PasswordService.encode -> EncodedPassword

    // 3. Instantiate a user (with account disabled!)
    // new User(..)

    // 4. Send Email
    // EmailService.sendVerificationEmail(user)

    // 5. Save user into the Database
    // UserRepository.save(..)

    return null;
  }
}
