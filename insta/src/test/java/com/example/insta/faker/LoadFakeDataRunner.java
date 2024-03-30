package com.example.insta.faker;

import com.example.insta.InstaApplication;
import org.springframework.boot.SpringApplication;

// Bridge between Spring Boot and Faker
public class LoadFakeDataRunner {
  private static final int FAKED_USERS = 3;
  private static final int FAKED_POST = 10;

  public static void main(String[] args) {
    System.out.println("Loading fake data...");

    try (var context = SpringApplication.run(InstaApplication.class, args)) {
      var faker = context.getBean(FakeDataLoader.class);

      // Challenge: Load it from the command line
      faker.loadFakeData(FAKED_USERS, FAKED_POST);
    }
  }
}
