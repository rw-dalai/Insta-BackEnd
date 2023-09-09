package com.example.todo.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

// Anaemic Domain Model
// ---------------------
// Its a class with getters and setters and no business logic
// Our Todo class is an example of an Anaemic Domain Model

// VS

// Rich Domain Model
// -----------------
// Its a class with getters and setters and business logic

// @NoArgsConstructor
// @Getter
// @Setter
// @ToString
// @EqualsAndHashCode
@Data
public class Todo {
  // JsonSerialize is needed to convert ObjectId to String
  @JsonSerialize(using = ToStringSerializer.class)
  @Id
  private ObjectId id;

  private String title;

  private boolean completed;

  // In MongoDB, we can have nested objects
  // private Address address;

  // For Spring Data, thus make it `protected`
  protected Todo() {}

  // For us, thus make it `public`
  public Todo(String title, boolean completed) {
    this.title = title;
    this.completed = completed;
  }
}
