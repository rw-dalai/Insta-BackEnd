package com.example.insta.domain.post;

import static com.example.insta.foundation.AssertUtil.hasMaxText;
import static com.example.insta.foundation.AssertUtil.isNotNull;
import static com.example.insta.foundation.EntityUtil.generateUUIDv4;

import com.example.insta.domain.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/** A comment from one user in a post. */

// We extend from BaseEntity because it's an own collection in MongoDB.
// We do not inline this class into Post because it can grow out of bound.
@Getter
@ToString
@Document(collection = "comment")
public class Comment extends BaseEntity<String> {

  // Which post does this comment belong to?
  // Indexes speed up queries in MongoDB by providing efficient access to data.
  // https://stackoverflow.com/questions/1108/how-does-database-indexing-work
  @Indexed private String postId;

  // Who created this comment?
  private String userId;

  // The text of the comment.
  private String text;

  // The likes of the comment.
  // Let's use a set to avoid duplicates.
  private Set<Like> likes;

  // ctor --------------------------------------------

  // Constructor for Spring Data to use when creating a new user from DB into memory.
  // Spring Data uses reflection to create an instance of this class.
  // https://www.youtube.com/watch?v=bhhMJSKNCQY
  protected Comment(String id) {
    super(id);
  }

  // Constructor for us developers to use when creating a new user in memory.
  public Comment(String postId, String userId, String text) {
    super(generateUUIDv4());

    this.postId = isNotNull(postId, "postId");
    this.userId = isNotNull(userId, "userId");
    this.text = hasMaxText(text, 4096, "text");
    this.likes = new HashSet<>();
  }
}
