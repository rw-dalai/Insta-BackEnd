package com.example.insta.domain.user;

import static com.example.insta.foundation.AssertUtil.hasMaxText;

import lombok.Getter;

/**
 * Profile of a user.
 *
 * <p>Here we can place base profile information of a user.
 */

// This class in inlined in User.
@Getter
public class Profile {

  //   private Address address;

  private String firstName;
  private String lastName;

  // private Media avatar;

  public Profile(String firstName, String lastName /*Media avatar*/) {
    this.firstName = hasMaxText(firstName, 255, "firstName");
    this.lastName = hasMaxText(lastName, 255, "lastName");
    // That means the user has to upload an avatar if not we can make it nullable.
    // this.avatar = isNotNull(avatar, "avatar");
  }
}
