package demo;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.repository.MongoRepository;

@SpringBootApplication
public class MongoDemoApplication3 {
  public static void main(String[] args) {
    SpringApplication.run(MongoDemoApplication3.class, args);
  }

  //    @Bean
  public CommandLineRunner run3(UserRepository3 userRepository, TodoRepository todoRepository) {
    return args -> {
      var todo1 = new Todo("t1", "Java");
      var todo2 = new Todo("t2", "C#");
      var user = new User3("u1", List.of(todo1, todo2));

      userRepository.deleteAll();
      todoRepository.deleteAll();

      todoRepository.saveAll(List.of(todo1, todo2));
      userRepository.save(user);

      var userBack = userRepository.findById("u1").get();

      // Spring Data MongoDB will fetch the referenced todos for us.
      userBack.todos().forEach(System.out::println);
    };
  }
}

// 1:n Relationship
// Domain Model: Embedded
// Mongo Model: Referenced (via DBRef)

// Mismatch between Domain Model and Mongo Model.
// The mismatch is resolved by Spring Data MongoDB.

// Domain Model

// - Question: Do I really need a list of todos in the User class?
// - Question2: My list is growing and growing. Is this a problem?
//   If the answer is both yes, this approach is for you.

record User3(@Id String id, @DBRef(lazy = true) List<Todo> todos) {}

record Todo(@Id String id, String name) {}

// Repository
interface UserRepository3 extends MongoRepository<User3, String> {}

interface TodoRepository extends MongoRepository<Todo, String> {}

// WHEN TO USE IT (@DBRef)?

// - Unbounded Growth:
//   If a list of related documents can grow without bound,
//   referencing is preferred to avoid hitting the document size limit in MongoDB (currently 16MB).

// - Data Normalization
//   When you want to avoid duplication and keep data normalized.
//   Changes to the referenced document are automatically reflected wherever it’s referenced.

// - Loose Coupling:
//   When entities are more loosely coupled, or when they don’t always need to be retrieved
// together.

// CONTROL OVER LIST SIZE/ELEMENTS

// - Limit the List Size:
//   Implement constraints to manage the number of references or related entities,
//   such as limiting a user to having a maximum.
//   (e.g. 10 todos.)

// - Validate Related Entities:
//   Ensure that each referenced entity adheres to specific rules or
//   prerequisites before establishing a reference.
//   (e.g. todos are not similar to each other.)
