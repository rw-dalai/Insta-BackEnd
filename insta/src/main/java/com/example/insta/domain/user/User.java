package com.example.insta.domain.user;

import static com.example.insta.foundation.AssertUtil.isValidEmail;
import static com.example.insta.foundation.EntityUtil.generateUUIDv4;

import com.example.insta.domain.BaseEntity;
import com.example.insta.security.password.PasswordService.EncodedPassword;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Getter to generate getters for all fields
// @ToString to generate toString() method for all fields
// @Document to tell Spring that this is a MongoDB document
// @Indexed to tell Spring to create an index for this field
// @PersistenceCreator to tell Spring to use this constructor when creating a new user from DB

/** A user of the application. */

// User is an Aggregate Root.
// We extend from BaseEntity because it's an own collection in MongoDB.
@Getter
@ToString(callSuper = true)
@Document(collection = "user")
public class User extends BaseEntity<String> {
  // Indexes speed up queries in MongoDB by providing efficient access to data.
  // https://stackoverflow.com/questions/1108/how-does-database-indexing-work
  @Indexed(unique = true)
  private String email;

  private String password;

  // What is the user allowed to do
  //  private Role role;
  private List<Role> roles;

  // The user's profile information
  private Profile profile;

  // The user's social information like, friends, etc.
  private Social social;

  // The user's account information like enabled, email verification tokens, etc.
  private Account account;

  // ctor --------------------------------------------

  // Constructor for Spring Data to use when creating a new user from DB into memory.
  // Spring Data uses reflection to create an instance of this class.
  // https://www.youtube.com/watch?v=bhhMJSKNCQY
  @PersistenceCreator
  @JsonCreator
  protected User(String id) {
    super(id);
  }

  // Constructor for us developers to use when creating a new user in memory.
  public User(String email, EncodedPassword password, Role role, Profile profile) {
    super(generateUUIDv4());

    this.email = isValidEmail(email, "email");
    this.password = password.getEncodedPassword();
    this.roles = List.of(role);
    this.profile = profile;
    this.social = new Social();
    this.account = new Account();
  }
}
