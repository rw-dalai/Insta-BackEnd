package com.example.insta.security.token;

import static com.example.insta.security.token.TokenUtil.generateToken;

import java.time.Instant;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;

// Purpose of this class?
// --------------------------------------------------------------------------------------------
// This class represents an email verification token.
// It is used when a user needs to verify their email address.

// Annotations used?
// --------------------------------------------------------------------------------------------
// @SuperBuilder Lombok annotation that generates a builder with all fields from superclasses.
// @Getter to generate getters for all fields
// @Transient to tell Spring to not store this field in the database

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
public class VerificationEmailToken extends Token {
  private final String verificationEmail;

  // TODO Strings are bad for security. Will be explain later.
  @Transient private String verificationEmailToken;

  @PersistenceCreator
  public VerificationEmailToken(
      String verificationEmail, String encodedValue, Instant createdAt, Instant expiresAt) {
    super(encodedValue, createdAt, expiresAt);
    this.verificationEmail = verificationEmail;
  }

  // Static factory method
  public static VerificationEmailToken generateEmailToken(String email, Instant expiresAt) {
    // Generate a secure 128-bit token as UUIDv4 and hash it with Keccak-256.
    var tokenValues = generateToken();

    // Build the token object with the builder design pattern
    return VerificationEmailToken.builder()
        .encodedValue(tokenValues.hashedTokenIdBase64())
        .verificationEmail(email)
        .verificationEmailToken(tokenValues.tokenId())
        .createdAt(Instant.now())
        .expiresAt(expiresAt)
        .build();
  }
}
