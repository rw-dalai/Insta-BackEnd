package com.example.insta.domain.user;

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
public class Account {
  private boolean enabled;
  // private EmailVerificationToken verificationToken;

  // TODO: Handle verificationToken
}
