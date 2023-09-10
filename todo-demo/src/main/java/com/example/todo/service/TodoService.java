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
import com.example.todo.presentation.commands.TodoCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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

  public List<Todo> findAllTodos() {
    LOGGER.info("Find todos");

    return todoRepository.findAll();
  }

  public Todo findTodo(String todoId) {
    LOGGER.info("Find todo with id {}", todoId);

    Todo todo = todoValidationService.getTodoById(todoId);

    LOGGER.info("Successfully found todo with id {}", todoId);
    return todo;
  }

  public Todo createTodo(TodoCommand command) {
    LOGGER.info("Create new todo with command {}", command);

    // Convert command into domain object
    // has no id at this point in time
    Todo todo = new Todo(command.title(), command.completed());
    todoRepository.save(todo);

    LOGGER.info("Successfully created new todo");

    // includes the id now, befause of Spring Data
    return todo;
  }

  public Todo updateTodo(String todoId, TodoCommand command) {
    LOGGER.info("Update todo with id {} and command {}", todoId, command);

    Todo todo = todoValidationService.getTodoById(todoId);

    todo.setTitle(command.title());
    todo.setCompleted(command.completed());

    todoRepository.save(todo);
    LOGGER.info("Successfully updated todo");

    return todo;
  }

  public void deleteTodo(String todoId) {
    LOGGER.info("Delete todo with id {}", todoId);

    // boolean exsists = todoRepository.existsById(new ObjectId(todoId));
    // Optional<Todo> todo = todoRepository.findById(new ObjectId(todoId));

    // Check the existence before we delete the entity otherwise the delete would silent ignore it
    todoValidationService.checkTodoById(todoId);
    todoRepository.deleteById(new ObjectId(todoId));

    LOGGER.info("Successfully updated todo");
  }
}
