package com.example.insta.domain.user;

import static com.example.insta.foundation.AssertUtil.hasMaxText;
import static org.springframework.util.Assert.notNull;

import com.example.insta.domain.media.Media;

/**
 * Profile of a user.
 *
 * <p>Here we can place base profile information of a user.
 */

// This class in inlined in User.
public class Profile {

  // private Address address;

  private String firstName;
  private String lastName;
  private Media avatar;

  public Profile(String firstName, String lastName, Media avatar) {
    hasMaxText(firstName, 255, "firstName must be less or equal 255 character");
    hasMaxText(lastName, 255, "lastName must be less or equal 255 character");
    notNull(avatar, "avatar must not be null");

    this.firstName = firstName;
    this.lastName = lastName;
    this.avatar = avatar;
  }
}
