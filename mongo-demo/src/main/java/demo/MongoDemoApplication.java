package demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

// @SpringBootApplication
public class MongoDemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(MongoDemoApplication.class, args);
  }

  //    @Bean
  public CommandLineRunner run(UserRepository userRepository) {
    return args -> {
      var address = new Address("Spengergasse", "Vienna");
      var user = new User("u1", address);

      userRepository.deleteAll();
      userRepository.save(user);
    };
  }
}

// 1:1 Relationship
// Domain Model: Embedded
// Mongo Model: Embedded

// No mismatch between Domain Model and Mongo Model

// Domain Model
record User(@Id String id, Address address) {}

record Address(String street, String city) {}

// Repository
interface UserRepository extends MongoRepository<User, String> {}

// WHEN USE IT ?

// - Always Together:
//   Embed when two entities are always used together and do not make sense individually.

// - Part of:
//   When one entity “is part of” the other, it logically makes sense to embed it.

// - Read Performance:
//   Embedded documents can be read in a single database operation, providing a performance benefit.

// Java 16+
// final attributes, ctor, getter, toString, hashCode, equals
// record Address(String street, String city) { }

// @Data
// @AllArgsConstructor
// class Address
// {
//    private String street;
//
//    private String city;
// }

// class Address
// {
//    private final String street;
//
//    private final String city;
//
//    public Address2(String street, String city)
//    {
//        this.street = street;
//        this.city = city;
//    }
//
//    public String getStreet()
//    {
//        return street;
//    }
//
//    public String getCity()
//    {
//        return city;
//    }
//
//    @Override
//    public boolean equals(Object o)
//    {
//        if (this == o)
//            return true;
//        if (o == null || getClass() != o.getClass())
//            return false;
//        Address2 address = (Address2) o;
//        return Objects.equals(street, address.street) && Objects.equals(city, address.city);
//    }
//
//    @Override
//    public int hashCode()
//    {
//        return Objects.hash(street, city);
//    }
//
//    @Override
//    public String toString()
//    {
//        return "Address{" +
//               "street='" + street + '\'' +
//               ", city='" + city + '\'' +
//               '}';
//    }
// }
