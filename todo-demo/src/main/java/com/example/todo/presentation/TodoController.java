package com.example.todo.presentation;

import com.example.todo.domain.Todo;
import com.example.todo.presentation.commands.TodoCommand;
import com.example.todo.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// 3 Layer Architecture
// --------------------------------------------

// Presentation Layer
// ------------------
//   Encapsulates HTTP Request and Response
//   Authentication
//   Authorization
//   Get Command Object (DTO)
//   Returns View (DTO)

// Service Layer
// -------------
//   Encapsulates Business Use Cases
//   Authorization
//   Cross Concern Business Logic
//   Cross Concern Business Validation
//   Transaction
//     MongoDB 4.2
//     ACID
//   Logging
//   Exception Translation
//   Service Exceptions
//   Get Command Object (DTO)
//   Returns View (DTO)

// Repository Layer
// ----------------
//   Encapsulates Data Access
//   Repository Exceptions

// Domain Layer
// ------------
//  Encapsulates Domain Model and Logic
//  Domain Validation
//  Domain Exceptions

// REST
// --------------------------------------------

// REST = Representational State Transfer
// - An architectural style for designing networked applications.
// - Popularized by Dr. Roy Fielding in his doctoral dissertation in 2000.
// https://www.ics.uci.edu/~fielding/pubs/dissertation/fielding_dissertation.pdf

// Representational = Representation of Data
// - Refers to the format in which data is presented. For instance, data could be represented as
// JSON, XML, HTML, or other formats.

// State = State of the Application
// - Relates to the current condition or data of an application at any given point in time. REST
// emphasizes that interactions should be stateless, meaning every request from a client contains
// all the necessary information for processing.

// Transfer = Transfer of Data
// - Describes the action of sending data from one entity to another. In REST, data gets transferred
// between a client and server using standard conventions and protocols, usually HTTP.

// Key Principles:
// 1. Stateless Interactions
// - Every request should be independent and carry all information needed for its processing.

// 2. Client-Server Architecture
// - A separation between the client (UI/UX) and server (data storage and retrieval).

// 3. Cacheable
// - Server responses can be cached on the client side to improve performance.

// Any Format (XML, JSON, TEXT, ..)

// Restful Url Design
// VERB        URI
// GET        /api/todo          fetch all todos
// GET        /api/todo/{123}    fetch specific todo
// POST       /api/todo          create todo
// PUT        /api/todo/{123}    update a specific todo
// DELETE     /api/todo/{123}    deletes a specific todo

// DTO Data Transfer Object
// Classic DTO, transfers data from one layer to another
// Command, necessary data in order to execute a business use case

// What Status Code to return ? :-)
// https://camo.githubusercontent.com/c26df6d372790e9f24d7e16d2cfa16a142985109b237bbc0f482c47a717019fe/68747470733a2f2f7261776769746875622e636f6d2f666f722d4745542f687474702d6465636973696f6e2d6469616772616d2f6d61737465722f6874747064642e66736d2e706e67

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {
  private final TodoService todoService;

  // HTTP Request
  // GET /api/todo
  // Body: none

  // HTTP Response
  // HTTP Status: 200
  // Body: [{ ... }, { ... }]

  // curl http://localhost:8080/api/todo
  @GetMapping
  public List<Todo> findAllTodo() {
    return this.todoService.findAllTodos();
  }

  // HTTP Request
  // GET /api/todo/123
  // Body: none

  // HTTP Response
  // HTTP Status: 200
  // Body: { "id": "123", "title": "make the homework", "completed": false }

  // curl -v localhost:8080/api/todo/123
  @GetMapping("/{id}")
  public Todo findTodo(@PathVariable("id") String todoId) {
    return todoService.findTodo(todoId);
  }

  // HTTP Request
  // POST /api/todo
  // Body: { "title": "make the homework", "completed": false }

  // HTTP Response
  // HTTP Status: 201
  // Body: { "id": "123", "title": "make the homework", "completed": false }

  // curl -X POST http://localhost:8080/api/todo
  // -H "Content-Type: application/json"
  // -d '{"title": "make the kitchen", "completed": false}'
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  // public Todo createTodo(@RequestBody Todo todo)
  public Todo createTodo(@RequestBody TodoCommand command) {
    return todoService.createTodo(command);
  }

  // HTTP Request
  // PUT /api/todo/123
  // Body: { "title": "make the kitchen", "completed": true }

  // HTTP Response
  // HTTP Status: 200
  // Body: { "id": "123", "title": "make the kitchen", "completed": true }

  // curl -X PUT http://localhost:8080/api/todo/123
  // -H "Content-Type: application/json"
  // -d '{"title": "make the kitchen", "completed": true}'
  @PutMapping("/{id}")
  public Todo updateTodo(@PathVariable String id, @RequestBody TodoCommand command) {
    return todoService.updateTodo(id, command);
  }

  // HTTP Request
  // PUT /api/todo/123
  // Body: None

  // HTTP Response
  // HTTP Status: 204
  // Body: None

  // curl -X DELETE http://localhost:8080/api/todo/123
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTodo(@PathVariable String id) {
    todoService.deleteTodo(id);
  }
}
