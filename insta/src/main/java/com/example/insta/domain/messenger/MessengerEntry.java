package com.example.insta.domain.messenger;

import static com.example.insta.foundation.AssertUtil.hasMinSize;
import static com.example.insta.foundation.EntityUtil.generateUUIDv4;
import static org.springframework.util.Assert.notNull;

import com.example.insta.domain.BaseEntity;
import java.util.Set;
import lombok.Getter;
import lombok.ToString;

/** A conversation between two or more users. */

// MessengerEntry is an Aggregate Root.
// We extend from BaseEntity because it's an own collection in MongoDB.
@Getter
@ToString
public class MessengerEntry extends BaseEntity<String> {
  // Who created this conversation?
  private String creatorId;

  // Who is in this conversation?
  private Set<String> participantIds;

  // What is the description of this conversation?
  // private String description;

  // ctor --------------------------------------------

  // Constructor for Spring Data to use when creating a new user from DB into memory.
  protected MessengerEntry(String id) {
    super(id);
  }

  // Constructor for us developers to use when creating a new user in memory.
  public MessengerEntry(String creatorId, Set<String> participantIds) {
    super(generateUUIDv4());

    notNull(creatorId, "creatorId must not be null");
    hasMinSize(participantIds, 2, "participantIds must be at least 2");

    this.creatorId = creatorId;
    this.participantIds = participantIds;
  }
}
