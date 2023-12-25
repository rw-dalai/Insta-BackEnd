package com.example.insta.security.token;

import static com.example.insta.foundation.AssertUtil.isTrue;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

// Purpose of this class?
// --------------------------------------------------------------------------------------------
// This class contains utility methods for tokens.
// It contains methods to generate and verify tokens.
// It should only be used as a static utility class.
// It is used by EmailVerificationToken. See EmailVerificationToken class.

// Data types used?
// --------------------------------------------------------------------------------------------
// String in UTF-8      (all character sets in this world)
// byte[]               (a sequence/array of bytes to story any data in raw format)
// String in Base64     (only A–Za–z0–9+/=)

// Bouncy Castle?
// --------------------------------------------------------------------------------------------
// Bouncy Castle provides implementations of cryptographic algorithms.

// Keccak-256?
// --------------------------------------------------------------------------------------------
// Keccak-256 is a cryptographic hash function.
// It is the winner of the SHA-3 competition. (SHA-3 = Secure Hash Algorithm 3)

public abstract class TokenUtil {

  // Constants -------------------------------------------------------------
  private static final String PROVIDER_NAME = BouncyCastleProvider.PROVIDER_NAME; // "BC"
  private static final String HASH_ALGO_NAME = "Keccak-256";

  // Static initializer block ------------------------------------------------
  static {
    // This block is executed when the class is loaded.
    // Register Bouncy Castle as a security provider if it is not already registered.
    if (Security.getProvider(PROVIDER_NAME) == null)
      Security.addProvider(new BouncyCastleProvider());
  }

  // DTO (Data Transfer Object) ---------------------------------------------
  protected record GeneratedTokenValues(String tokenId, String hashedTokenIdBase64) {}

  // Generate Token --------------------------------------------------------
  // `generateEmailToken` Moved to `EmailVerificationToken` class
  // `generateXXXToken` Moved to `XXXToken` class

  /**
   * Generate a secure 128-bit token as UUIDv4 and hash it with Keccak-256.
   *
   * @return tokenId and it's hashed tokenId
   * @throws RuntimeException if the hashing algorithm is not available
   * @throws RuntimeException if the security provider is not available
   */
  protected static GeneratedTokenValues generateToken() {
    // 1. Generate 128-bit cryptographic random value as UUIDv4 called `tokenId`
    String tokenId = UUID.randomUUID().toString();

    // 2. Hash the tokenId with Keccak-256
    byte[] hashedTokenId = hashTokenId(tokenId);

    // 3. Convert the hashed tokenId to a base64 string
    String hashedTokenIdBase64 = Base64.getEncoder().encodeToString(hashedTokenId);

    // 4. Return the tokenId and its hashed tokenId.
    return new GeneratedTokenValues(tokenId, hashedTokenIdBase64);
  }

  // Verify Token ----------------------------------------------------------
  /**
   * Verify a token by checking if it is not expired and if the hashes match.
   *
   * @param token the token to verify e.g. EmailVerificationToken, PasswordResetToken
   * @param tokenId the tokenId to verify
   * @throws IllegalArgumentException if the token is null or expired or the hashes do not match
   */
  public static void verifyToken(Token token, String tokenId) {
    // 1. Is the token not expired ?
    isTrue(isTokenNonExpired(token), "token is expired");

    // 2. Do the hashes match ?
    isTrue(areHashesEqual(token, tokenId), "token do not match");
  }

  private static boolean isTokenNonExpired(Token token) {
    // actual < expired
    return Instant.now().isBefore(token.getExpiresAt());

    // if (! Instant.now().isBefore(token.getExpiresAt()))
    //  throw new IllegalStateException("...");

    // if (Instant.now().isAfter(token.getExpiresAt()))
    //   throw new IllegalStateException("...");
  }

  /**
   * Check if the hashes match.
   *
   * @param token the token to verify e.g. EmailVerificationToken, PasswordResetToken
   * @param tokenId the tokenId to verify
   * @return true if the hashes match
   */
  private static boolean areHashesEqual(Token token, String tokenId) {
    // 1. Hash the tokenId with Keccak-256
    byte[] hashedTokenId = hashTokenId(tokenId);

    // 2. Decode the hashed tokenId in the token from Base64
    byte[] hashedTokenIdSaved = Base64.getDecoder().decode(token.getEncodedValue());

    // 3. Check for equality in constant time !
    return MessageDigest.isEqual(hashedTokenId, hashedTokenIdSaved);

    /*
    DO NOT DO THIS: Vulnerable to Timing Attacks !

    byte[] hashedTokenId = hashTokenId(tokenId);
    String hashedTokenIdString = new String(hashedTokenId, StandardCharsets.UTF_8);

    // Check for equality in non-constant time !
    return tokenId.equals(hashedTokenIdString);
     */
  }

  // Hash Token ------------------------------------------------------------
  /**
   * Hash the tokenId with Keccak-256.
   *
   * @param tokenId the tokenId to hash
   * @return the hashed tokenId as byte[]
   * @throws RuntimeException if the hashing algorithm is not available
   * @throws RuntimeException if the security provider is not available
   */
  private static byte[] hashTokenId(String tokenId) {
    try {
      // 1. Use Keccak-256 from Bouncy Castle
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO_NAME, PROVIDER_NAME);

      // 2. String -> byte[]
      byte[] tokenIdBytes = tokenId.getBytes(StandardCharsets.UTF_8);

      // 3. Hash the tokenId
      byte[] hashedTokenId = md.digest(tokenIdBytes);

      // 4. Return the hashed tokenId
      return hashedTokenId;
    } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
      throw new RuntimeException("Hashing toking failed", e);
    }
  }
}
