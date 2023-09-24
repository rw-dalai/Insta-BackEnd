package com.example.insta.domain.user;

import static com.example.insta.foundation.EntityHelpers.generateUUIDv4;

import com.example.insta.domain.BaseEntity;
import org.springframework.data.mongodb.core.index.Indexed;

public class User extends BaseEntity<String> {
  // Indexes speed up queries in MongoDB by providing efficient access to data.
  @Indexed(unique = true)
  private String email;

  private String password;

  // What is the user allowed to do
  private Role role;

  // Profile will be embedded in User in MongoDB
  // Therefore the _Domain Model_ and the _MongoDB Model_ are the same (perfect match)
  private Profile profile;

  // ctor --------------------------------------------

  // Constructor for Spring Data to use when creating a new user
  protected User(String id) {
    super(id);
  }

  // Constructor for us to use when creating a new user
  public User(String email, String password, Role role, Profile profile) {
    super(generateUUIDv4());

    // TODO ..
  }
}
