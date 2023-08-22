package com.example.todo.service;

// POJO vs Bean
// ------------
// POJO
// Plain Old Java Object

// Spring Bean
// Managed Instance by the Spring Container

// Circular Dependency
// Bean A <-> Bean B

// Spring Stereotypes
// ------------------
// @Bean
// @Repository
// @Service
// @Controller // return htmls
// @RestController // return data (e.g. json)
// @Component

// What Services exist?
// --------------------
// Application Service (Business Use Case)
// Domain Service (Domain Logic, Domain Validation)
// Infrastructure Service (e.g. EmailService, FileService, ...)

import com.example.todo.domain.Todo;
import com.example.todo.persistence.TodoRepository;
import com.example.todo.presentation.commands.CreateTodoCommand;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// Application Service (Business Use Case)

@Service
@RequiredArgsConstructor
public class TodoService {
  private final Logger LOGGER = LoggerFactory.getLogger(TodoService.class);

  private final TodoRepository todoRepository;
  private final TodoValidationService todoValidationService;

  // Constructor Injection
  //    public TodoService(TodoRepository todoRepository)
  //    {
  //        this.todoRepository = todoRepository;
  //    }

  public Todo findTodo(String todoId) {
    LOGGER.info("Find todo with id {}", todoId);

    Todo todo = todoValidationService.getTodoById(todoId);

    LOGGER.info("Successfully found todo with id {}", todoId);

    return todo;
  }

  public Todo createTodo(CreateTodoCommand command) {
    LOGGER.info("Create new todo with {}", command);

    // command -> domain
    Todo todo = new Todo(command.title(), command.completed());
    todoRepository.save(todo);

    LOGGER.info("Successfully created new todo");

    return todo;
  }
}
