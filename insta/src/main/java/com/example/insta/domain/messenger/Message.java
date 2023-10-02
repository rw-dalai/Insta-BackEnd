package com.example.insta.domain.messenger;

import static com.example.insta.foundation.AssertUtil.hasMaxSizeOrNull;
import static com.example.insta.foundation.AssertUtil.hasMaxTextOrNull;
import static com.example.insta.foundation.EntityUtil.generateUUIDv4;
import static org.springframework.util.Assert.isTrue;

import com.example.insta.domain.BaseEntity;
import com.example.insta.domain.media.Media;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.lang.Nullable;

/** A message from one user in a messenger entry. */

// We extend from BaseEntity because it's an own collection in MongoDB.
// We do not inline this class into MessengerEntry because it can grow out of bound.
@Getter
@ToString
public class Message extends BaseEntity<String> {

  // Indexes speed up queries in MongoDB by providing efficient access to data.
  // https://stackoverflow.com/questions/1108/how-does-database-indexing-work
  // What is the id messenger entry that this message belongs to?
  @Indexed(unique = true)
  private String messengerEntryId;

  // Who sent this message?
  private String senderId;

  // What is the text of this message?
  // Nullable because a message can only contain text or media.
  private @Nullable String text;

  // What are the media of this message?
  // Nullable because a message can only contain text or media.
  private @Nullable List<Media> medias;

  // ctor --------------------------------------------

  // Constructor for Spring Data to use when creating a new user from DB into memory.
  protected Message(String id) {
    super(id);
  }

  // Constructor for us developers to use when creating a new user in memory.
  public Message(
      String messengerEntryId,
      String senderId,
      @Nullable String text,
      @Nullable List<Media> medias) {
    super(generateUUIDv4());

    isTrue(text != null || medias != null, "text or medias must not be null");
    hasMaxTextOrNull(text, 4096, "text must be less or equal 4096 character");
    hasMaxSizeOrNull(medias, 10, "medias must be less or equal 10");

    this.messengerEntryId = messengerEntryId;
    this.senderId = senderId;
    this.text = text;
    this.medias = medias;
  }
}
