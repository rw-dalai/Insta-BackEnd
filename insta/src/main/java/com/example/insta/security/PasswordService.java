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

// AI crack passwords
// https://tech.co/news/ai-can-guess-your-password
// https://www.homesecurityheroes.com/ai-password-cracking/

// NIST Password Guidelines Summary
// https://blog.netwrix.com/2022/11/14/nist-password-guidelines/

// NIST Password Guidelines Publication
// https://pages.nist.gov/800-63-3/sp800-63b.html

// ZXCVBN Library
// https://github.com/dropbox/zxcvbn

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
//   Why ? To make brute-force attacks infeasible.

// What is the purpose of the salt ?
//   The salt is a random string that is added to the password before hashing.
//   The salt is stored in clear text in the database together with the hashed password.
//   The salt prevents rainbow table attacks and identical passwords of having the same hash.

// How does BCrypt work?
// --------------------------------------------------------------------
// https://en.wikipedia.org/wiki/Bcrypt#Versioning_history
// The input to the bcrypt function is the password string (up to 72 bytes),
// a numeric cost, and a 16-byte salt value.
// The salt is typically a random value.
// The bcrypt function uses these inputs to compute a 24-byte hash.
// The final output of the bcrypt function is a string of the form:
// {bcrypt}$<id>$<cost>$<salt><hash>
// {bcrypt}$2<a/b/x/y>$[cost]$[22 character(base64) salt][31 character (base64) hash]
// --------------------------------------------------------------------
// Example:
// {bcrypt}$2a$10$/YeAGfv.i/NgnUCYygA0/uDomrkxM4Ji5ksO3d5UIS7zU0S/1epOi
// Algorithm: {bcrypt}
// Version: $2a $<id>
// Cost Factor: $10
// Salt: $/YeAGfv.i/NgnUCYygA0/u
// Hashed Value: DomrkxM4Ji5ksO3d5UIS7zU0S/1epOi

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Service to make this class a Spring Bean
// @RequiredArgsConstructor Lombok annotation that generates a constructor with all required fields.

// <<Outer Class>>
@Service
@RequiredArgsConstructor
public class PasswordService {
  public static final int ZXCVBN_STRENGTH_THRESHOLD = 3;

  private final Zxcvbn zxcvbn = new Zxcvbn();
  // Inject PasswordEncoder from SecurityConfig
  private final PasswordEncoder passwordEncoder;

  // constructor injection
  //  public PasswordService(PasswordEncoder passwordEncoder) {
  //    this.passwordEncoder = passwordEncoder;
  //  }

  // setter injection
  //  public void setPasswordEncoder(PasswordEncoder encoder) {
  //    this.passwordEncoder = passwordEncoder;
  //  }

  /**
   * Checks password strength and encodes it with a cryptographic hashing functions
   *
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

  // <<Inner Class>>
  // In Java we can have classes inside classes.
  // Inner classes can be "non-static" or "static".
  // - Non-static classes have access to outer class members.
  // - Static classes have no access to outer class members.
  @Getter
  public static class EncodedPassword {
    // the hashed password
    // e.g {bcrypt}$2a$10$/YeAGfv.i/NgnUCYygA0/uDomrkxM4Ji5ksO3d5UIS7zU0S/1epOi
    private final String encodedPassword;

    // Private constructors are only accessible from within the class and the <<Outer Class>>.
    // So only `PasswordService` can create `EncodedPassword` objects.
    private EncodedPassword(String hashedValue) {
      this.encodedPassword = hashedValue;
    }
  }
}
