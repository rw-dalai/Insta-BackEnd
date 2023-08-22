package com.example.todo.presentation;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.example.todo.domain.Todo;
import com.example.todo.persistence.TodoRepository;
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

  @BeforeAll
  public void serverSetup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @BeforeEach
  public void setup() {
    todoFixture = new Todo("title", false);
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
        when().get("/api/todo/{id}", todoFixture.getId().toString()).then().extract().response();
    // spotless:on

    // Then
    Todo todoFetched = response.as(Todo.class);
    assertThat(response.statusCode(), equalTo(200));
    assertThat(todoFetched.getId().toString(), equalTo(todoFixture.getId().toString()));
  }
}
