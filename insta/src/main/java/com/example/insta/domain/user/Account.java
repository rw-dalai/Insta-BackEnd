package com.example.insta.domain.user;

import com.example.insta.foundation.AssertUtil;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

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

  private String tokenId = UUID.randomUUID().toString(); // UUID -> null

  public void verifyToken(String tokenId) {
    AssertUtil.isNotNull(this.tokenId, "tokenId");
    AssertUtil.isTrue(this.tokenId.equals(tokenId), "Tokens do not match");

    this.tokenId = null;
  }
}
