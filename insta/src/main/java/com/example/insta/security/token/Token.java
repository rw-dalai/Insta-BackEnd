package com.example.insta.security.token;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

// Properties of a One Time Token
// ------------------------------------------------------------
// A good one-time token for cryptographic purposes should possess several key properties:

// 1. Unpredictability and Randomness:
// The token should be generated using a process that is highly unpredictable and random
// making it impossible for attackers to guess or predict the next valid token.
// -> UUIDv4 has 122-bit of entropy (128-bit in total - 6 bits for version and variant)
// -> UUIDv4 is generated using a cryptographically secure random number generator
// -> See: `SecureRandom` class vs `Random` class

// 2. Uniqueness: Each token should be unique to prevent reuse attacks.
// The probability of generating two identical tokens should be extremely low.
// -> UUIDv4 has 122-bit of entropy
// Generating 1 billion UUIDs per second for 100 years would create a duplicate probability of 50%.
// https://en.wikipedia.org/w/index.php?title=Universally_unique_identifier&oldid=755882275#Random_UUID_probability_of_duplicates

// 3. Secure Transmission and Storage:
// Tokens should be transmitted over secure channels (like HTTPS)
// and stored securely using appropriate cryptographic methods.
// -> We use Keccak-256 to hash the token
// -> We use Base64 to encode the token
// -> We use HTTPS to transmit the token

// 4. Limited Lifetime:
// Tokens should have a short validity period, reducing the window of opportunity
// for an attacker to use a stolen or intercepted token.
// -> We use a 24h validity period

// 5. Size and Complexity:
// A sufficiently large token size and complexity to avoid brute-force attacks.
// -> UUIDv4 has 122-bit of entropy

// 6. Integrity and Non-repudiation:
// Ensuring that the token cannot be tampered with during transmission or storage,
// and that it's verifiable to have come from a legitimate source.
// -> We use HTTPS to transmit the token
// -> We use Keccak-256 to hash the token

// 7. Confidentiality:
// If the token carries sensitive information,
// it should be encrypted or hashed to prevent unauthorized access.
// -> We use Keccak-256 to hash the token

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
@AllArgsConstructor
public abstract class Token {
  // value as string encoded in base64 and hashed with Keccak-256
  private final String encodedValue;

  // When was the token created?
  private final Instant createdAt;

  // When does the token expire?
  private final Instant expiresAt;
}
