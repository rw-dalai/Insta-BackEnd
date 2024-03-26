package com.example.insta.fixture;

import com.example.insta.domain.post.HashTag;
import com.example.insta.domain.post.Post;
import java.util.HashSet;
import java.util.Set;

public class PostFixture {

  public static final String USER_ID = "user123";
  public static final String POST_TEXT = "Hello, Spengergasse";
  public static final Set<HashTag> HASH_TAGS = new HashSet<>();

  public static Post createTextPost() {
    return new Post(USER_ID, POST_TEXT, null, HASH_TAGS);
  }
}
