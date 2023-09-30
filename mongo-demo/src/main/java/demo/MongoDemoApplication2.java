package demo;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

@SpringBootApplication
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

// Domain Model

// - Question: Do I really need a list of todos in the User class?
// - Question2: My list is small and bounded and the data is part of the User.
//   If the answer is yes, this approach is for you.

record User2(@Id String id, List<Address2> address) {}

record Address2(String street, String city) {}

// Repository
interface UserRepository2 extends MongoRepository<User2, String> {}

// WHEN TO USE IT ?

// - Bounded Growth:
//   It's practical when the list has a reasonable and bounded size.

// - Atomic Operations:
//   Useful when you need to perform atomic operations on the entire entity,
//   including its sub-documents.

// - Frequent Access Together:
//   When the embedded entities are often accessed together with the parent entity.
