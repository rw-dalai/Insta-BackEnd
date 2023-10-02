package com.example.insta.domain.post;

import static com.example.insta.foundation.AssertUtil.*;
import static com.example.insta.foundation.EntityUtil.generateUUIDv4;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import com.example.insta.domain.BaseEntity;
import com.example.insta.domain.media.Media;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.lang.Nullable;

/**
 * A post from one user.
 *
 * <p>A post can contain text or media or both. Others can comment and like a post.
 */

// Post is an Aggregate Root.
// We extend from BaseEntity because it's an own collection in MongoDB.
@Getter
@ToString
public class Post extends BaseEntity<String> {
  // Who created this post?
  // Indexes speed up queries in MongoDB by providing efficient access to data.
  // https://stackoverflow.com/questions/1108/how-does-database-indexing-work
  @Indexed(unique = true)
  private String userId;

  // The text of the post.
  // Nullable because a post can only contain text or media.
  private @Nullable String text;

  // The images of the post.
  // Nullable because a post can only contain text or media.
  // Let's use a list to keep the order of the images.
  private @Nullable List<Media> medias;

  // The hashtags in the post.
  // Let's use a set to avoid duplicates.
  private Set<HashTag> hashTags;

  // If HashTag is an entity, we could also use @DBRef to reference it.
  // private @DBRef Set<HashTag> hashTags;

  // The likes of the post.
  // Let's use a set to avoid duplicates.
  private Set<Like> likes;

  // ctor --------------------------------------------

  // Constructor for Spring Data to use when creating a new user from DB into memory.
  protected Post(String id) {
    super(id);
  }

  // Constructor for us developers to use when creating a new user in memory.
  public Post(
      String userId, @Nullable String text, @Nullable List<Media> medias, Set<HashTag> hashTags) {
    super(generateUUIDv4());

    notNull(userId, "userId must not be null");
    isTrue(text != null || medias != null, "text or medias must not be null");
    hasMaxTextOrNull(text, 4096, "text must be less or equal 4096 character");
    hasMaxSizeOrNull(medias, 10, "medias must be less or equal 10");
    hasMaxSize(hashTags, 10, "hashTags must be less or equal 10");

    this.userId = userId;
    this.text = text;
    this.medias = medias;
    this.hashTags = hashTags;
    this.likes = new HashSet<>();
  }
}
