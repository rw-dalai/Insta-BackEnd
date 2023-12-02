package com.example.insta.presentation.commands;

public class Commands {
  public record UserRegistrationCommand(String email, String password) {}
  ;
  //    public record UserVerificationCommand(String userId, String tokenId) {};
}
