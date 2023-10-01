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
      todosBack.forEach(System.out::println);
    };
  }
}

// 1:n Relationship
// Domain Model: -
// Mongo Model: Referenced (via id)

// No mismatch between Domain Model and Mongo Model

// - Question: I am not solving any problem with the list in the Class?
// - Question2: I do not need any control over the size/items in the Class?
// - Hint: I want to model the relationship of an entity or aggregate?
// - If the answer yes to some, this approach might be for you.
// - Note: This is the most common approach to model relationships in MongoDB.
// - Indexing: Ensure that the referenced ID fields are indexed in MongoDB for performance.

// Domain Model
//  Since User4 does not contain a list of Todo4 items, it remains a simple entity,
//  potentially making it more performant and straightforward.
record User4(@Id String id) {}

record Todo4(@Id String id, String userId, String name) {}

// Repository
interface UserRepository4 extends MongoRepository<User4, String> {}

interface TodoRepository4 extends MongoRepository<Todo4, String> {
  List<Todo4> findByUserId(String userId);
}

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

// PLEASE KEEP IN MIND:
//
// - QUERYING RELATED DOCUMENTS:
//   Querying related documents needs to be done manually.
//   But this can be an advantage also, as it allows for control over the queries and performance.

// - INDEXING:
//   Ensure that the referenced ID fields are indexed in MongoDB for performance.
