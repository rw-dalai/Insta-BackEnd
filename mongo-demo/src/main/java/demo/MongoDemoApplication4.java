package demo;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

@SpringBootApplication
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

      // We have to manually query for the todos !
      var todosBack = todoRepository.findByUserId(userBack.id());

      System.out.println(userBack);
    };
  }
}

// 1:n Relationship
// Domain Model: -
// Mongo Model: Referenced (via id)

// No mismatch between Domain Model and Mongo Model

// Domain Model

// - Question: Do I really need a list of todos in the User class?
//   If the answer is both no, this approach is for you.

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

//  - Control Over Related Entity Retrieval:
//    Explicitly querying related entities provides granular control over data retrieval and
//    may be useful to circumvent unnecessary data fetch operations.

//  - Performance Considerations:
//    Direct references might be preferable when optimal read performance is a priority.
//    Directly querying the related entities using the user ID often involves fewer resources than
// resolving DBRefs.

//  - Absence of Cascading Operations:
//    When there is no necessity to cascade operations (like delete) or
//    manage complex lifecycle synchronizations between entities,
//    direct references are often simpler and more manageable.
