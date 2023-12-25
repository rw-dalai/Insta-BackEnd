package com.example.insta.domain.user;

import static com.example.insta.foundation.AssertUtil.isNotNull;
import static com.example.insta.security.token.EmailVerificationToken.generateEmailToken;
import static com.example.insta.security.token.TokenUtil.verifyToken;
import static java.time.Instant.now;

import com.example.insta.security.token.EmailVerificationToken;
import java.time.Duration;
import lombok.Getter;
import lombok.Setter;

// One Time Token
// ------------------------------------------------------------
// - unguessable token value (truly random 128 bit entropy)
// - createdAt
// - expiresAt

// How to verify an email address using a One Time Token
// ------------------------------------------------------------
// 1. Create One Time Token
// 2. Hash One Time Token with Keccak-256 and store into the DB
// 3. One Time Token is sent to the user via email
// /api/registration/token?userId=123&tokenId=456
// 4. User clicks on the link in the email and the One Time Token is sent to the server
// 5. The server verifies the One Time Token by hashing it against the hashed version in the DB
// 6. If the hashes match and it is not expired then the email is verified

/**
 * Account of a user.
 *
 * <p>Here we can place account information of a user. <br>
 * If account is enabled, the user can log in. <br>
 * If account is disabled, the user cannot log in. <br>
 *
 * <p>A user must verify their email address before they can log in.
 */

// This class in inlined in User.
@Getter
public class Account {
  @Setter private boolean enabled = false; // false -> true

  private EmailVerificationToken emailToken;

  public static final Duration EMAIL_VERIFICATION_DURATION = Duration.ofHours(24);

  //  public static final int EMAIL_VERIFICATION_DURATION = 24;

  public String generateEmailTokenFor(String email) {
    isNotNull(email, "email");

    // var token = generateEmailToken(email, now().plus(24, ChronoUnit.DAYS));
    var token = generateEmailToken(email, now().plus(EMAIL_VERIFICATION_DURATION));
    this.emailToken = token.emailToken();

    return token.tokenId();
  }

  public String verifyEmailTokenFor(String tokenId) {
    isNotNull(tokenId, "tokenId");
    isNotNull(emailToken, "token");

    verifyToken(emailToken, tokenId);

    String verifiedEmail = emailToken.getEmailToVerify();
    this.emailToken = null;

    return verifiedEmail;
  }

  public String getEmailToVerify() {
    isNotNull(emailToken, "token");

    return emailToken.getEmailToVerify();
  }
}

// @Getter
// public class Account {
//  @Setter private boolean enabled = false; // false -> true
//
//  private String tokenId = UUID.randomUUID().toString(); // UUID -> null
//
//  public void verifyToken(String tokenId) {
//    isNotNull(this.tokenId, "tokenId");
//    isTrue(this.tokenId.equals(tokenId), "Tokens do not match");
//
//        if (!this.tokenId.equals(tokenId)) {
//          throw new ...
//        }
//
//    this.tokenId = null;
//  }
// }
