package com.example.insta.domain.post;

import static com.example.insta.foundation.AssertUtil.hasMaxText;

// What is a record class in Java?
// https://www.developer.com/java/java-record-class/

/** Hashtag of a post. */

// This class in inlined in Post.
public record HashTag(String value) {
  // Constructor with validation
  public HashTag {
    hasMaxText(value, 50, "hash tag must not be longer than 50 characters");
  }
}
