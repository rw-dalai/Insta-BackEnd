package com.example.insta.service;

import com.example.insta.domain.user.User;
import com.example.insta.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

  private final UserRepository userRepository;

  public User findById(String userId) {
    return userRepository.findById(userId).orElseThrow(() -> ofUserNotFound(userId));
  }

  public void checkEmailNotTaken(String email) {
    if (userRepository.existsByEmail(email)) {
      throw ofEmailTaken(email);
    }
  }

  // TODO We need own exceptions; we do it when we start with Exception Handling
  private static RuntimeException ofUserNotFound(String userId) {
    return new IllegalArgumentException("User not found with id " + userId);
  }

  private static RuntimeException ofEmailTaken(String email) {
    return new IllegalArgumentException("Email already taken: " + email);
  }
}
