package com.example.insta.domain.user;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Relationships of a user.
 *
 * <p>Following, followers, blocked users, etc. <br>
 */

// This class in inlined in User.
public class Social {
  // A map of user ids to relations (key=fromUserId, value=relation)
  // Example: { "123": { "type": "INCOMING", "createdAt": "2023-01-01T00:00:00Z" } }
  // In this example our user has a friend request from user with id 123.
  private final Map<String, Relation> relations = new HashMap<>();

  public record Relation(RelationType type, Instant createdAt) {}

  public enum RelationType {
    INCOMING,
    OUTGOING,
    ESTABLISHED
  }
}
