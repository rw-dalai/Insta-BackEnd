package com.example.insta.domain.user;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Relationships of a user.
 *
 * <p>Here we can place the relationships of a user. Following, followers, blocked users, etc. <br>
 *
 * <p>EXAMPLE: relations: { "123": { "type": "INCOMING", "createdAt": "2021-01-01T00:00:00Z" } }
 * <br>
 * The user has one relation one is an incoming friend request from user with id 123.
 */

// This class in inlined in User.
public class Social {
  public record Relation(RelationType type, Instant createdAt) {}

  public enum RelationType {
    INCOMING,
    OUTGOING,
    ESTABLISHED
  }

  private final Map<String, Relation> relations = new HashMap<>();
}
