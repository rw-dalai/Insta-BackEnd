package com.example.insta.domain.post;

import java.time.Instant;
import java.util.Objects;

// What is a record class in Java?
// https://www.developer.com/java/java-record-class/

/** Like of a post or comment. */

// This class in inlined in Post or Comment.
public record Like(String userId, Instant createdAt) {

  // Likes are equal if they have the same user Id.
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Like like = (Like) o;
    return Objects.equals(userId, like.userId);
  }

  // If you override equals() you should also override hashCode()
  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }
}
