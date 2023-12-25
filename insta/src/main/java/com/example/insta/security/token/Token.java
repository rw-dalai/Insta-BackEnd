package com.example.insta.security.token;

import java.time.Instant;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

// Purpose of this class?
// --------------------------------------------------------------------------------------------
// This class is the base class for all tokens.
// It contains the common fields for all tokens.

// Annotations used?
// --------------------------------------------------------------------------------------------
// @SuperBuilder Lombok annotation that generates a builder with all fields from superclasses.
// @Getter to generate getters for all fields

// Why SuperBuilder again?
// --------------------------------------------------------------------------------------------
// Our Subclass implements the Builder Pattern.
// So this class needs to implement the Builder Pattern as well.

@SuperBuilder
@Getter
public abstract class Token {
  // value as string encoded in base64 and hashed with Keccak-256
  private final String encodedValue;

  // When was the token created?
  private final Instant createdAt;

  // When does the token expire?
  private final Instant expiresAt;
}
