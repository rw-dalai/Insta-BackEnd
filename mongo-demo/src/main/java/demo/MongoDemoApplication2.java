package demo;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

// @SpringBootApplication
public class MongoDemoApplication2 {
  public static void main(String[] args) {
    SpringApplication.run(MongoDemoApplication2.class, args);
  }

  //    @Bean
  public CommandLineRunner run2(UserRepository2 userRepository) {
    return args -> {
      var address1 = new Address2("Spengergasse", "Vienna");
      var address2 = new Address2("Donaustadt", "Vienna");
      var user = new User2("u1", List.of(address1, address2));

      userRepository.deleteAll();
      userRepository.save(user);
    };
  }
}

// 1:n Relationship
// Domain Model: Embedded
// Mongo Model: Embedded

// No mismatch between Domain Model and Mongo Model

// - Question: My list is small and bounded and the data is part of the Class
// - Hint: I want to model the relationship of a value object?
// - If the answer is yes, this approach might be for you.

// Domain Model
record User2(@Id String id, List<Address2> address) {}

record Address2(String street, String city) {}

// Repository
interface UserRepository2 extends MongoRepository<User2, String> {}

// WHEN TO USE IT ?

// - Bounded Growth:
//   It's practical when the List has a reasonable and bounded size.

// - Part of:
//   When one “is part of” the other, it logically makes sense to embed it.

// - Read Performance:
//   Embedded documents can be read in a single database operation, providing a performance benefit.

// - Atomic Operations:
//   Useful when you need to perform atomic update operations on the entire document.

// - Frequent Access Together:
//   When the embedded document are often accessed together with the parent.
