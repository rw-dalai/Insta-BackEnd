package com.example.insta.security;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Read more about password hashing !
// https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html#argon2id
// https://auth0.com/blog/hashing-passwords-one-way-road-to-security/

// General Information about Hashing
// --------------------------------------------------------------------
// Cryptographic hash functions are often used to store passwords in a database.
//    e.g. Argon2, Scrypt, Bcrypt, PBKDF2

// A cryptographic hash function is a hash function that is designed to be
//   computationally infeasible to reverse.

// A cryptographic hash function is a mathematical algorithm that maps
//   data of arbitrary size to a bit string of a fixed size.

// The values returned by a cryptographic hash function are called
//   hash values, hash codes, digests, or simply hashes.

// Not all hash functions are cryptographic hash functions.
//   A cryptographic hash function is designed to be slow to compute.

// How does BCrypt work?
// --------------------------------------------------------------------
// https://en.wikipedia.org/wiki/Bcrypt#Versioning_history
// The input to the bcrypt function is the password string (up to 72 bytes),
// a numeric cost, and a 16-byte salt value.
// The salt is typically a random value.
// The bcrypt function uses these inputs to compute a 24-byte hash.
// The final output of the bcrypt function is a string of the form:
// {bcrypt}$<id>$<cost>$<salt><digest>
// {bcrypt}$2<a/b/x/y>$[cost]$[22 character(base64) salt][31 character (base64) hash]
// {bcrypt}$2a$10$/YeAGfv.i/NgnUCYygA0/uDomrkxM4Ji5ksO3d5UIS7zU0S/1epOi
// --------------------------------------------------------------------
// Algorithm: {bcrypt}
// Version: $2a $<id>$<cost>$<salt><digest>
// Cost Factor: $10
// Salt: $/YeAGfv.i/NgnUCYygA0/u
// Hashed Value: DomrkxM4Ji5ksO3d5UIS7zU0S/1epOi

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Service to make this class a Spring Bean
// @RequiredArgsConstructor to inject PasswordEncoder from SecurityConfig

// outer class
@Service
@RequiredArgsConstructor
public class PasswordService {
  public static final int ZXCVBN_STRENGTH_THRESHOLD = 3;

  private final Zxcvbn zxcvbn = new Zxcvbn();
  // Injected by Spring
  private final PasswordEncoder passwordEncoder;

  /**
   * @param rawPassword the password to be encoded
   * @throws IllegalArgumentException if the password is too weak (zxcvbn score < 3)
   * @return the encoded password as a custom type {@link EncodedPassword}
   */
  public EncodedPassword encode(String rawPassword) {
    // 1. Password strength assessment
    Strength measure = zxcvbn.measure(rawPassword);
    if (measure.getScore() < ZXCVBN_STRENGTH_THRESHOLD)
      throw new IllegalArgumentException("Password to weak; score " + measure.getScore());

    // 2. Password hashing
    String encodePassword = passwordEncoder.encode(rawPassword);

    // 3. Return custom type
    return new EncodedPassword(encodePassword);
  }

  // inner class
  // static classes have no access to outer class
  @Getter
  public static class EncodedPassword {
    // the hashed password
    // e.g {bcrypt}$2a$10$/YeAGfv.i/NgnUCYygA0/uDomrkxM4Ji5ksO3d5UIS7zU0S/1epOi
    private final String encodedPassword;

    // Private constructors are only accessible from within the class and outer class
    private EncodedPassword(String hashedValue) {
      this.encodedPassword = hashedValue;
    }
  }
}
