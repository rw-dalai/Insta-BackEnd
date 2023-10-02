package com.example.insta.domain.media;

import static com.example.insta.foundation.AssertUtil.hasMaxText;
import static org.springframework.util.Assert.isTrue;

import java.time.Instant;

// What is a record class in Java?
// https://www.developer.com/java/java-record-class/

/**
 * Media Information.
 *
 * <p>The media information of a media in MongoDB. The binary data of the media is stored in MongoDB
 * GridFS.
 */

// This class in inlined in Post or Message.
public record Media(
    String id,
    Instant createdAt,
    String filename,
    String mimeType,
    long size,
    int width,
    int height) {
  // Constructor with validation
  public Media {
    hasMaxText(filename, 255, "filename must be less or equal 255 character");
    hasMaxText(mimeType, 50, "mimeType must be less or equal 50 character");
    isTrue(size > 0, "size must be greater than 0");
    isTrue(width > 0, "width must be greater than 0");
    isTrue(height > 0, "height must be greater than 0");
  }
}
