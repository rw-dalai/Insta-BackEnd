package com.example.insta.domain.messenger;

import static com.example.insta.foundation.AssertUtil.*;
import static com.example.insta.foundation.EntityUtil.generateUUIDv4;
import static org.springframework.util.Assert.isTrue;

import com.example.insta.domain.BaseEntity;
import com.example.insta.domain.media.Media;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

/** A message from one user in a messenger entry. */

// We extend from BaseEntity because it's an own collection in MongoDB.
// We do not inline this class into MessengerEntry because it can grow out of bound.
@Getter
@ToString
@Document(collection = "message")
public class Message extends BaseEntity<String> {

  // What is the id messenger entry that this message belongs to?
  // Indexes speed up queries in MongoDB by providing efficient access to data.
  // https://stackoverflow.com/questions/1108/how-does-database-indexing-work
  @Indexed private String messengerEntryId;

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
  // Spring Data uses reflection to create an instance of this class.
  // https://www.youtube.com/watch?v=bhhMJSKNCQY
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
    this.messengerEntryId = isNotNull(messengerEntryId, "messengerEntryId");
    this.senderId = isNotNull(senderId, "senderId");
    this.text = hasMaxTextOrNull(text, 4096, "text");
    this.medias = hasMaxSizeOrNull(medias, 10, "medias");
  }
}
