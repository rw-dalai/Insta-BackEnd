package com.example.insta.service;

import com.example.insta.domain.user.User;
import com.example.insta.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Purpose of this class?
// --------------------------------------------------------------------------------------------
// This class is a helper class for retrieving users from the database.

@Service
@RequiredArgsConstructor
public class UserQueryService {

  private final UserRepository userRepository;

  public User findById(String userId) {
    // Find user by id or throw exception
    return userRepository.findById(userId).orElseThrow(() -> ofUserNotFound(userId));
  }

  public void checkEmailNotTaken(String email) {
    // Check if email is not taken if not throw exception
    if (userRepository.existsByEmail(email)) throw ofEmailTaken(email);
  }

  // TODO We need own exceptions; we do it when we start with Exception Handling
  private static RuntimeException ofUserNotFound(String userId) {
    return new IllegalArgumentException("User not found with id " + userId);
  }

  private static RuntimeException ofEmailTaken(String email) {
    return new IllegalArgumentException("Email already taken: " + email);
  }
}
