package demo;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.repository.MongoRepository;

@SpringBootApplication
public class MongoDemoApplication3 {
  public static void main(String[] args) {
    SpringApplication.run(MongoDemoApplication3.class, args);
  }

  @Bean
  public CommandLineRunner run3(UserRepository3 userRepository, TodoRepository3 todoRepository) {
    return args -> {
      var todo1 = new Todo3("t1", "Java");
      var todo2 = new Todo3("t2", "C#");
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

// - Question: What is the problem I am trying to solve with the List in the Class?
// - Question2: Do I need to have tight control over the items in the Class?
// - Hint: I do want to model the relationship of an entity?
// - If the answer is yes, this approach might be for you.
// - Note: For large or frequently accessed lists, this approach might not be optimal.
// - Indexing: Ensure that the referenced ID fields are indexed in MongoDB for performance.

// Domain Model
record User3(@Id String id, @DBRef(lazy = true) List<Todo3> todos) {}

record Todo3(@Id String id, String name) {}

// Repository
interface UserRepository3 extends MongoRepository<User3, String> {}

interface TodoRepository3 extends MongoRepository<Todo3, String> {}

// WHEN TO USE IT ?

// - Unbounded Growth:
//   If a list of related documents can grow without bound
//   (document limit in MongoDB is 16MB)

// - Has Many:
//   The relationship between the documents is more like a "has" relationship.

// - Data Normalization
//   When you want to avoid duplication and keep data normalized.

// - Loose Coupling:
//   When documents don’t always need to be retrieved/updated together.

// CONTROL OVER LIST ITEMS:

// - Limit the List Size:
//   Implement constraints to manage the number of references or related entities.
//   (e.g. 10 todos.)

// - Validate Related Entities:
//   Ensure that each referenced entity adheres to specific rules in the list.
//   (e.g. todos are not similar to each other, or any other related status).

// PLEASE KEEP IN MIND:

// - CASCADE OPERATIONS need to be done manually !!!
//   But be careful of Cascade Save/Update/Delete operations.
//   You have to do it manually!
//   (e.g. If you delete a User, Spring Data MongoDB will not delete the referenced todos.)

// - ALTERNATIVE: List<String> todoIds !!!
//   You can always use a List of Ids instead of a List of Entities.
//   And fetch the entities manually to avoid the overhead of @DBRef magic.
//   https://www.mongodb.com/docs/manual/reference/database-references/
//   record User3(@Id String id, List<String> todoIds) {}
//   record Todo3(@Id String id, String name) {}
