package com.example.insta.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.example.insta.domain.user.Profile;
import com.example.insta.domain.user.Role;
import com.example.insta.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

// https://hamcrest.org/JavaHamcrest/tutorial

@DataMongoTest
public class UserRepositoryTest {
  @Autowired private UserRepository userRepository;

  @Test
  // public void ensure_saveUser_works()
  public void saveUser_shouldWork() {
    // GWT / AAA

    // Given (Arrange)
    var user = new User("wenz@spengergasse.at", "password", Role.USER, new Profile("Rene", "Wenz"));

    // When (Act)
    var userSaved = userRepository.save(user);

    // Then (Assert)
    assertThat(userSaved, notNullValue());
  }

  // test audit fields
  // test find by id
  // test find by email
}
