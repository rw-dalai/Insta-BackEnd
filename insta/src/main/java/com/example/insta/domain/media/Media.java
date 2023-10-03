package com.example.insta.domain.media;

import static com.example.insta.foundation.AssertUtil.hasMaxText;
import static com.example.insta.foundation.AssertUtil.hasMinSize;

import java.time.Instant;

// What is a record class in Java?
// https://www.developer.com/java/java-record-class/

/**
 * Media Information.
 *
 * <p>The media information of a media in MongoDB.<br>
 * The binary data of the media is stored in MongoDB GridFS.
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
    hasMaxText(filename, 255, "filename");
    hasMaxText(mimeType, 50, "mimeType");
    hasMinSize(size, 1, "size");
    hasMinSize(width, 1, "width");
    hasMinSize(height, 1, "height");
  }
}
