package demo;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

// @SpringBootApplication
public class MongoDemoApplication4 {
  public static void main(String[] args) {
    SpringApplication.run(MongoDemoApplication4.class, args);
  }

  //    @Bean
  public CommandLineRunner run4(UserRepository4 userRepository, TodoRepository4 todoRepository) {
    return args -> {
      var user = new User4("u1");
      var todo1 = new Todo4("t1", user.id(), "Java");
      var todo2 = new Todo4("t2", user.id(), "Java");

      userRepository.deleteAll();
      todoRepository.deleteAll();

      todoRepository.saveAll(List.of(todo1, todo2));
      userRepository.save(user);

      var userBack = userRepository.findById("u1").get();
      System.out.println(userBack);

      // We have to manually query for the todos !
      var todosBack = todoRepository.findByUserId(userBack.id());
      System.out.println(todosBack);
    };
  }
}

// 1:n Relationship
// Domain Model: -
// Mongo Model: Referenced (via id)

// No mismatch between Domain Model and Mongo Model

// - Question: I am not solving any problem with the list in the Class?
// - Question2: I do not need any control over the size/items in the Class?
// - Hint: I am modelling the relationship between two aggregates?
// - If the answer yes to all, this approach might be for you.

// Domain Model
record User4(@Id String id) {}

record Todo4(@Id String id, String userId, String name) {}

// Repository
interface UserRepository4 extends MongoRepository<User4, String> {}

interface TodoRepository4 extends MongoRepository<Todo4, String> {
  List<Todo4> findByUserId(String userId);
}

// WHEN TO USE IT (and not @DBRef)

// - Simplicity:
//   Direct references are simpler to implement and manage.

// - Control Over Related Entity Retrieval:
//   Explicitly querying related entities provides control over data retrieval and performance.
