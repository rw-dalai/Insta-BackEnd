package com.example.insta.presentation.commands;

public class Commands {

  // --- Registration ---
  //  whiten input = email.trim().toLowerCase();
  public record UserRegistrationCommand(
      String email, String password, String firstName, String lastName) {
    public String email() {
      return email.trim().toLowerCase();
    }
  }
  ;

  // --- Verification ---
  public record UserVerificationCommand(String userId, String tokenId) {}
  ;
}
