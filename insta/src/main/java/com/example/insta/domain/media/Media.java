package com.example.insta.domain.media;

import static com.example.insta.foundation.AssertUtil.hasMaxText;
import static com.example.insta.foundation.AssertUtil.hasMinSize;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.Instant;
import java.util.List;
import org.bson.types.ObjectId;

// What is a record class in Java?
// https://www.developer.com/java/java-record-class/

/**
 * Media Information.
 *
 * <p>The media information of a media in MongoDB.<br>
 * The binary data of the media is stored in MongoDB GridFS.
 */

// api/media/dog.jpg
// <img src="/api/media/{id}">
// <img src="/api/media/dog.jpg">
// <img src="/api/media/uuidv4">

// This class in inlined in Post or Message.
public record Media(
    // JsonSerialize is needed to convert ObjectId to String
    @JsonSerialize(using = ToStringSerializer.class) ObjectId id,
    Instant createdAt,
    String filename,
    String mimeType, // e.g. image/jpeg, image/png, video/mp4
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

  public static List<ObjectId> toMediaIds(List<Media> medias) {
    return medias.stream().map(Media::id).toList();
  }
}
