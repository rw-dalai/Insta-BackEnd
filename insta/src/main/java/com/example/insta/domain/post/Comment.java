package com.example.insta.domain.post;

import static com.example.insta.foundation.AssertUtil.hasMaxText;
import static com.example.insta.foundation.EntityUtil.generateUUIDv4;
import static org.springframework.util.Assert.notNull;

import com.example.insta.domain.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;

/** A comment from one user in a post. */

// We extend from BaseEntity because it's an own collection in MongoDB.
// We do not inline this class into MessengerEntry because it can grow out of bound.
@Getter
@ToString
public class Comment extends BaseEntity<String> {
  // Who created this comment?
  // Indexes speed up queries in MongoDB by providing efficient access to data.
  // https://stackoverflow.com/questions/1108/how-does-database-indexing-work
  @Indexed(unique = true)
  private String userId;

  // The text of the comment.
  private String text;

  // The likes of the comment.
  // Let's use a set to avoid duplicates.
  private Set<Like> likes;

  // ctor --------------------------------------------

  // Constructor for Spring Data to use when creating a new user from DB into memory.
  protected Comment(String id) {
    super(id);
  }

  // Constructor for us developers to use when creating a new user in memory.
  public Comment(String userId, String text) {
    super(generateUUIDv4());

    notNull(userId, "userId must not be null");
    hasMaxText(text, 4096, "text must be less or equal 4096 character");

    this.userId = userId;
    this.text = text;
    this.likes = new HashSet<>();
  }
}
