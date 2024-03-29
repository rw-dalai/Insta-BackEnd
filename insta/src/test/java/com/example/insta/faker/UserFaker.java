package com.example.insta.faker;

import com.example.insta.domain.user.Profile;
import com.example.insta.domain.user.Role;
import com.example.insta.domain.user.User;
import com.example.insta.security.password.PasswordService;
import com.github.javafaker.Faker;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// POJO
public class UserFaker {

  // Please change this password in your application
  private static final String DEFAULT_PASSWORD = "spengergasse";

  private static final Faker faker = new Faker();

  private static final PasswordEncoder encoder =
      PasswordEncoderFactories.createDelegatingPasswordEncoder();

  private static final PasswordService passwordService = new PasswordService(encoder);

  // createUser() -> User
  public static User createUser() {
    // rene@gmx.at
    var email = faker.internet().emailAddress();

    // password
    var password = passwordService.encode(DEFAULT_PASSWORD);

    // role
    // Role role = faker.random().nextBoolean() ? Role.USER : Role.ADMIN;
    var role = Role.USER;

    // profile
    var profile = fakeProfile();

    // user
    var user = new User(email, password, role, profile);

    // setBaseEntityField(user, "createdAt", Instant.now());
    // setBaseEntityField(user, "lastModifiedAt", Instant.now());
    // setBaseEntityField(user, "version", null);

    return user;
  }

  private static Profile fakeProfile() {
    var firstName = faker.name().firstName();
    var lastName = faker.name().lastName();
    var profile = new Profile(firstName, lastName);
    return profile;
  }

  public static List<User> createUsers(int n) {
    return Stream.generate(UserFaker::createUser).limit(n).toList();
  }

  // for testing
  public static void main(String[] args) {

    var users = createUsers(10);
    users.forEach(System.out::println);
  }
}
