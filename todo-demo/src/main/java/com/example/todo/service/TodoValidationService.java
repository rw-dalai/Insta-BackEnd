package com.example.todo.service;

import com.example.todo.domain.Todo;
import com.example.todo.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

// Domain Service (Domain Logic, Domain Validation)

// The service is a bit overkill for this example
// Could be moved to the `TodoService` as a private method

@Service
@RequiredArgsConstructor
public class TodoValidationService {
  private final TodoRepository todoRepository;

  // How to handle Optional
  // var todo = todoOptional.orElse(new Todo());
  // var todo = todoOptional.orElseGet(() -> new Todo());
  // var todo = todoOptional.orElseThrow(() -> new IllegalArgumentException("Todo with id " + todoId
  // + " does not exist"));

  // Should throw Service Exceptions
  public Todo getTodoById(String todoId) {
    return todoRepository
        .findById(new ObjectId(todoId))
        .orElseThrow(
            () -> new IllegalArgumentException("Todo with id " + todoId + " does not exist"));
  }
}
