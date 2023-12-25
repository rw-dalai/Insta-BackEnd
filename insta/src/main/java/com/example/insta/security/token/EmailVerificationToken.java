package com.example.insta.security.token;

import static com.example.insta.security.token.TokenUtil.generateToken;

import java.time.Instant;
import lombok.*;
import lombok.experimental.SuperBuilder;

// Purpose of this class?
// --------------------------------------------------------------------------------------------
// This class represents an email verification token.
// It is used when a user needs to verify their email address.

// Annotations used?
// --------------------------------------------------------------------------------------------
// @SuperBuilder Lombok annotation that generates a builder with all fields from superclasses.
// @Getter to generate getters for all fields

// What is the Builder Pattern?
// --------------------------------------------------------------------------------------------
// The Builder Pattern is a creational design pattern.
// It is used to create objects that have a lot of fields.
// Like StringBuilders, StringBuffers, etc.

// Examples of a Builder Pattern
// --------------------------------------------------------------------------------------------
// String world = new StringBuilder()
//  .append("Hello")
//  .append("World")
//  .toString();

// EmailVerificationToken token = EmailVerificationToken.builder()
//  .encodedValue(..)
//  .createdAt(..)
//  .expiresAt(..)
//  .emailToVerify(..)
//  .build();

// Static factory method?
// --------------------------------------------------------------------------------------------
// A static factory method is a static method that returns an object.
// We not call `new EmailVerificationToken()` directly,
// but instead call `EmailVerificationToken.generateEmailToken(..)`.
// Why? 1) Because it is more readable. 2) Because we can return a custom type.
// In our case we return a DTO (Data Transfer Object) that contains the token and the tokenId.

@SuperBuilder
@Getter
public class EmailVerificationToken extends Token {
  private final String emailToVerify;

  // DTO (Data Transfer Object)
  public record GeneratedEmailTokenResult(EmailVerificationToken emailToken, String tokenId) {}

  // Static factory method
  public static GeneratedEmailTokenResult generateEmailToken(String email, Instant expiresAt) {
    // Generate a secure 128-bit token as UUIDv4 and hash it with Keccak-256.
    var tokenValues = generateToken();

    // Build the token object with the builder design pattern
    var token =
        EmailVerificationToken.builder()
            .encodedValue(tokenValues.hashedTokenIdBase64())
            .createdAt(Instant.now())
            .expiresAt(expiresAt)
            .emailToVerify(email)
            .build();

    // Return the DTO
    return new GeneratedEmailTokenResult(token, tokenValues.tokenId());
  }
}
