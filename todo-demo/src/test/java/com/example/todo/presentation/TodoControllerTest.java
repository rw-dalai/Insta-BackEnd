package com.example.todo.presentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.example.todo.domain.Todo;
import com.example.todo.persistence.TodoRepository;
import com.example.todo.presentation.commands.TodoCommand;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoControllerTest {
  @LocalServerPort private int port;

  @Autowired private TodoRepository todoRepository;

  // Test Fixture
  // A test fixture is a fixed state of a set of objects used as a baseline for running tests.
  private Todo todoFixture;
  private TodoCommand commandFixture;

  @BeforeAll
  public void serverSetup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @BeforeEach
  public void setup() {
    todoFixture = new Todo("title", false);
    commandFixture = new TodoCommand("title2", true);

    // Spring Data / MongoDB assign the id
    todoRepository.save(todoFixture);
  }

  @AfterEach
  public void tearDown() {
    todoRepository.deleteAll();
  }

  @Test
  public void whenFetchingTodoById_thenReturnsCorrectTodo() {

    // spotless:off
    // Given // When
    Response response =
        when()
          .get("/api/todo/{id}", todoFixture.getId().toString())
        .then()
          .extract().response();
    // spotless:on

    // Then
    Todo todoFetched = response.as(Todo.class);
    assertThat(response.statusCode(), equalTo(200));
    assertThat(todoFetched.getId().toString(), equalTo(todoFixture.getId().toString()));
  }

  @Test
  public void whenCreatingTodo_thenReturnsCreatedTodo() {

    // spotless:off
    // Given // When
    Response response =
        given()
          .contentType("application/json")
          .body(commandFixture)
        .when()
          .post("/api/todo")
          .then()
          .extract().response();
    // spotless:on

    // Then
    Todo todoCreated = response.as(Todo.class);
    assertThat(response.statusCode(), equalTo(201));
    assertTodo(todoCreated, commandFixture);
  }

  @Test
  public void whenUpdatingTodo_thenReturnsUpdatedTodo() {

    // spotless:off
    // Given // When
    Response response =
        given()
          .contentType("application/json")
          .body(commandFixture)
        .when()
          .put("/api/todo/" + todoFixture.getId().toString())
          .then()
          .extract().response();
    // spotless:on

    // Then
    Todo todoUpdated = response.as(Todo.class);
    assertThat(response.statusCode(), equalTo(200));
    assertTodo(todoUpdated, commandFixture);
  }

  @Test
  public void whenDeletingTodo_thenReturnsSuccessNoContent() {

    // spotless:off
    // Given // When
    Response response =
        given()
            .contentType("application/json")
            .body(commandFixture)
          .when()
            .delete("/api/todo/" + todoFixture.getId().toString())
            .then()
            .extract().response();
    // spotless:on

    // Then
    assertThat(response.statusCode(), equalTo(204));
  }

  // --- Assertion Helper ---

  private void assertTodo(Todo actual, TodoCommand command) {
    assertThat(actual.getId().toString(), notNullValue());
    assertThat(actual.getTitle(), is(command.title()));
    assertThat(actual.isCompleted(), is(command.completed()));
  }
}
