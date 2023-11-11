package com.example.insta.persistence;

import static com.example.insta.domain.user.Role.USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.insta.config.MongoConfig;
import com.example.insta.domain.user.Profile;
import com.example.insta.domain.user.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;

// JUnit Asserts
// Hamcrest
// AssertJ

// https://hamcrest.org/JavaHamcrest/tutorial

// What to test ?
// --------------------------------------------------------------------------------------------
// 1. SUCCESS: test if user is saved
// 2. SUCCESS: test if audit fields are set automatically
// 3. SUCCESS: test if user is found by id
// 4. SUCCESS: test if user is found by email
// 5. FAIL: test if user is saved with duplicate email
// 6. FAIL: test if user is saved with old version

// Annotations used?
// --------------------------------------------------------------------------------------------
// @DataMongoTest to load Spring context but not the whole application (only MongoDB)
// @Import to import MongoConfig

@DataMongoTest
@Import(MongoConfig.class)
public class UserRepositoryTest {
  @Autowired private UserRepository userRepository;

  public static final String MAIL = "wenz@spengergasse.at";

  private User userSaved;

  @BeforeEach
  public void setup() {
    // Given
    var profile = new Profile("Rene", "Wenz");
    var user = new User(MAIL, "password", USER, profile);

    userRepository.deleteAll();
    userSaved = userRepository.save(user);
  }

  @Test
  // public void ensure_saveUser_works()
  public void saveUser_shouldWork() {
    // GWT / AAA

    // Given (Arrange)
    // var profile = new Profile("Rene", "Wenz");
    // var user = new User("wenz@spengergasse.at", "password", USER, profile);

    // When (Act)
    // var userSaved = userRepository.save(user);

    // Then (Assert)
    assertThat(userSaved, notNullValue());
  }

  @Test
  public void auditFields_shouldBeSetAutomatically() {
    // Given
    // var profile = new Profile("Rene", "Wenz");
    // var user = new User("wenz@spengergasse.at", "password", USER, profile);

    // When
    // var userSaved = userRepository.save(user);

    // Then
    assertThat(userSaved.getCreatedAt(), notNullValue());
    assertThat(userSaved.getLastModifiedAt(), notNullValue());
    assertThat(userSaved.getVersion(), notNullValue());
    assertThat(userSaved.getVersion(), equalTo(0L));
  }

  @Test
  // public void givenExistingUser_whenFindById_thenReturnsUser() {
  public void findById_shouldReturnUser_whenUserExists() {
    // When
    var userFound = userRepository.findById(userSaved.getId());

    // Then
    assertThat(userFound.isPresent(), is(true));
    assertThat(userFound.get().getId(), equalTo(userSaved.getId()));
  }

  @Test
  public void findByEmail_shouldReturnUser_whenUserExists() {
    // Given
    // var profile = new Profile("Rene", "Wenz");
    // var user = new User("wenz@spengergasse.at", "password", USER, profile);
    // var userSaved = userRepository.save(user);

    // When
    Optional<User> userFound = userRepository.findByEmail(userSaved.getEmail());

    // Then
    assertThat(userFound.isPresent(), is(true));
    assertThat(userFound.get().getEmail(), equalTo(userSaved.getEmail()));
  }

  @Test
  public void saveUser_shouldFail_withDuplicateEmail() {
    // Given
    var profile = new Profile("Rene", "Wenz");
    var duplicatedUser = new User(MAIL, "password", USER, profile);

    // When / Then
    assertThrows(DuplicateKeyException.class, () -> userRepository.save(duplicatedUser));
  }

  @Test
  public void saveUser_shouldFail_withOldVersion() {
    // When
    // version = 0
    var userRead1 = userRepository.findById(userSaved.getId()).get();
    var userRead2 = userRepository.findById(userSaved.getId()).get();

    // When
    // userRead1 version = 0; DB version = 0 -> version 1
    userRepository.save(userRead1);

    // userRead2 version = 0; DB version = 1
    // org.springframework.dao.OptimisticLockingFailureException:
    // Cannot save entity 86fc48b6-f930-4330-a096-97af35039c2b with version 1 to collection user;
    // Has it been modified meanwhile
    assertThrows(OptimisticLockingFailureException.class, () -> userRepository.save(userRead2));
  }
}
