package com.example.todo.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.example.todo.domain.Todo;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

// @NoArgsConstructor
// @AllArgsConstructor

@DataMongoTest
// @RequiredArgsConstructor
class TodoRepositoryTest {
  @Autowired private TodoRepository todoRepository;

  /*
  public TodoRepositoryTest(TodoRepository todoRepository)
  {
      this.todoRepository = todoRepository;
  }
   */

  @Test
  public void ensure_findById_returnsTodo() {
    // Given
    // MongoDB / Spring Data sets the Id
    var todo = new Todo("todo title", false);
    todoRepository.save(todo);

    // When
    Optional<Todo> todoReturned = todoRepository.findById(todo.getId());

    // Then
    Todo actual = todoReturned.get();
    assertThat(actual, notNullValue());
  }
}
