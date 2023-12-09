package com.example.insta.fixture;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

import com.example.insta.domain.user.Profile;
import com.example.insta.domain.user.Role;
import com.example.insta.domain.user.User;
import com.example.insta.security.PasswordService;
import com.example.insta.security.PasswordService.EncodedPassword;

// What is a Test Fixture?
// --------------------------------------------------------------------------------------------
// A test fixture is a fixed state of a set of objects used as a baseline for running tests.
// The purpose of a test fixture is to ensure that there is a well known and fixed environment
// in which tests are run so that results are repeatable.
// Examples of fixtures:
// - loading a database with a specific, known set of data
// - copying a specific known set of files
// - preparation of input data and set-up/creation of fake or mock objects
// - loading a test fixture may be expensive, so it is usually done before a number of tests are
// run,
//   and similarly, cleaning up after a test may also be expensive and is often done after a number
//   of tests as well, but in some cases may be done after each test has been performed.

public class UserFixture {
  public static final String EMAIL = "wenz@spengergasse.at";
  public static final String PASSWORD = "spengergasse";
  public static final String FIRST_NAME = "Rene";
  public static final String LAST_NAME = "Wenz";

  private static final PasswordService passwordService =
      new PasswordService(createDelegatingPasswordEncoder());

  private static final EncodedPassword encodedPassword = passwordService.encode(PASSWORD);

  public static User createUser() {
    var profile = new Profile(FIRST_NAME, LAST_NAME);
    var user = new User(EMAIL, encodedPassword, Role.USER, profile);
    return user;
  }
}
