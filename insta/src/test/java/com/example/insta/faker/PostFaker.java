package com.example.insta.faker;

import com.example.insta.domain.media.Media;
import com.example.insta.domain.post.HashTag;
import com.example.insta.domain.post.Post;
import com.github.javafaker.Faker;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostFaker {

  private static final int NUM_HASH_TAGS = 3;

  private static final Faker faker = new Faker();

  public static Post createPost(String userId) {

    var text = faker.lorem().sentence();
    List<Media> medias = null;
    var hashTags = fakeHashTags(NUM_HASH_TAGS);

    var post = new Post(userId, text, medias, hashTags);

    // setBaseEntityField(user, "createdAt", Instant.now());
    // setBaseEntityField(user, "lastModifiedAt", Instant.now());
    // setBaseEntityField(user, "version", null);

    return post;
  }

  private static Set<HashTag> fakeHashTags(int n) {
    return Stream.generate(() -> new HashTag(faker.lorem().word()))
        .limit(n)
        .collect(Collectors.toSet());
  }

  public static List<Post> createPosts(String userId, int n) {
    return Stream.generate(() -> createPost(userId)).limit(n).toList();
  }

  // for testing
  public static void main(String[] args) {

    var posts = createPosts("userId", 10);
    posts.forEach(System.out::println);
  }
}
