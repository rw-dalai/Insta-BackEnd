package com.example.insta.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

// Base class for all domain entities (that are persisted to the database)
public abstract class BaseEntity<PK extends Serializable> {
  @Id protected final PK id;

  // Automatically set the createdAt field when the entity is created.
  // Needs @EnableMongoAuditing in MongoConfig
  @CreatedDate private Instant createdAt;

  // Automatically set the lastModifiedAt field when the entity is updated.
  // Needs @EnableMongoAuditing in MongoConfig
  @LastModifiedDate private Instant lastModifiedAt;

  // Automatically set the version field when the entity is updated.
  // For optimistic locking.
  // https://stackoverflow.com/questions/129329/optimistic-vs-pessimistic-locking
  @Version private Long version;

  // ctor -----------------------------------------------------------------
  protected BaseEntity(PK id) {
    this.id = id;
  }

  // getters --------------------------------------------------------------
  public PK getId() {
    return id;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getLastModifiedAt() {
    return lastModifiedAt;
  }

  public Long getVersion() {
    return version;
  }

  // hash/equals ----------------------------------------------------------
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    BaseEntity that = (BaseEntity) o;

    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
