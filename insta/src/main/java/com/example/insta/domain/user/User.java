package com.example.insta.domain.user;

import static com.example.insta.foundation.AssertUtil.isValidEmail;
import static com.example.insta.foundation.EntityUtil.generateUUIDv4;

import com.example.insta.domain.BaseEntity;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;

/** A user of the application. */

// User is an Aggregate Root.
// We extend from BaseEntity because it's an own collection in MongoDB.
@Getter
@ToString
public class User extends BaseEntity<String> {
  // Indexes speed up queries in MongoDB by providing efficient access to data.
  // https://stackoverflow.com/questions/1108/how-does-database-indexing-work
  @Indexed(unique = true)
  private String email;

  private String password;

  // What is the user allowed to do
  private Role role;

  // The user's profile information
  private Profile profile;

  // The user's social information like, friends, etc.
  private Social social;

  // The user's account information like enabled, email verification tokens, etc.
  private Account account;

  // ctor --------------------------------------------

  // Constructor for Spring Data to use when creating a new user from DB into memory.
  protected User(String id) {
    super(id);
  }

  // Constructor for us developers to use when creating a new user in memory.
  public User(String email, String password, Role role, Profile profile) {
    super(generateUUIDv4());

    isValidEmail(email, "email must be a valid email address");

    this.email = email;
    this.password = password;
    this.role = role;
    this.profile = profile;
    this.social = new Social();
    this.account = new Account();
  }
}
