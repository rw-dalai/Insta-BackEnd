package com.example.insta.domain.user;

import static com.example.insta.foundation.AssertUtil.hasMaxText;

import com.example.insta.domain.media.Media;
import org.springframework.util.Assert;

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

  public void setFirstName(String firstName) {
    hasMaxText(firstName, 255, "firstName must be less or equal 255 character");
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    hasMaxText(lastName, 255, "lastName must be less or equal 255 character");
    this.lastName = lastName;
  }

  public void setAvatar(Media avatar) {
    Assert.notNull(avatar, "avatar must not be null");
    this.avatar = avatar;
  }
}
