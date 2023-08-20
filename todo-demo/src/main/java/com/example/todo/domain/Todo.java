package com.example.todo.domain;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

// @NoArgsConstructor
// @Getter
// @Setter
// @ToString
// @EqualsAndHashCode
@Data
public class Todo {
  @Id private ObjectId id;

  private String title;

  private boolean completed;

  // For Spring Data
  public Todo() {}

  // For us
  public Todo(String title, boolean completed) {
    this.title = title;
    this.completed = completed;
  }
}
