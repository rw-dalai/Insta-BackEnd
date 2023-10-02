package com.example.insta.domain.user;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Relationships of a user.
 *
 * <p>Here we can place the relationships of a user. Following, followers, blocked users, etc.
 *
 * <p>EXAMPLE: relations: { "123": { "type": "INCOMING", "createdAt": "2021-01-01T00:00:00Z" },
 * "456": { "type": "OUTGOING", "createdAt": "2021-01-01T00:00:00Z" }, } In this example, the user
 * has two relationships. The first one is an incoming relationship with the user whose id is "123".
 * The second one is an outgoing relationship with the user whose id is "456".
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
