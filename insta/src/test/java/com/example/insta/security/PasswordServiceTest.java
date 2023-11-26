package com.example.insta.security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.insta.security.PasswordService.EncodedPassword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

// What to test ?
// --------------------------------------------------------------------------------------------
// 1. Test Password Strength
// - FAIL: test weak password (zxcvbn score <= 2)
// - SUCCESS: test strong password (zxcvbn score > 2)

// 2. Test Password Encoding
// - SUCCESS: test if password is encoded (-> hash)
// - SUCCESS: test if identical passwords are encoded (-> hash) with different salt

// Test Naming Conventions?
// --------------------------------------------------------------------------------------------
// method_shouldXXX_whenXXX
// SUT_ExpectedBehaviour_StateUnderTest
// https://matheus.ro/2017/09/24/unit-test-naming-convention/

// Assertion Libraries
// --------------------------------------------------------------------------------------------
// An assertion library is a library that provides a set of assertion methods, i.e. methods that
// verify something about the state of the system under test.
// - JUnit Asserts
// - Hamcrest Matchers
// - AssertJ Assertions

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

// Annotations used?
// --------------------------------------------------------------------------------------------
// @SpringBootTest to load Spring context
// @SpringBootTest(classes = { ... }) to load Spring context but only with specific beans
// @Import to load SecurityConfig beans into this test
// @TestInstance not to have to declare test methods as static
// @Autowired to inject PasswordService into this test
// @BeforeAll to run setup() once before all tests
// @Test to mark test methods

// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {PasswordService.class})
@Import(SecurityConfig.class)
public class PasswordServiceTest {
  // Test Fixtures
  public static final String STRONG_PASSWORD = "alle meine 99 java entchen lieben C#";
  public static final String WEAK_PASSWORD = "password5566";

  // We need to use @Autowired because JUNIT does not support constructor injection
  @Autowired PasswordService passwordService;

  @Test
  // SUT_ExpectedBehaviour_StateUnderTest
  public void encode_shouldFail_whenProvidingWeakPasswords() {
    // Given
    // PasswordService passwordService = new PasswordService();

    // When / Then
    assertThrows(IllegalArgumentException.class, () -> passwordService.encode(WEAK_PASSWORD));
  }

  @Test
  // SUT_ExpectedBehaviour_StateUnderTest
  public void encode_shouldPass_whenProvidingStrongPasswords() {
    // Given
    // PasswordService passwordService = new PasswordService();

    // When
    EncodedPassword encoded = passwordService.encode(STRONG_PASSWORD);

    // Then
    assertThat(encoded, is(notNullValue()));
  }

  @Test
  // SUT_ExpectedBehaviour_StateUnderTest
  public void encode_shouldReturnHashes_whenProvidingStrongPasswords() {
    // Given
    // PasswordService passwordService = new PasswordService();

    // When
    EncodedPassword password = passwordService.encode(STRONG_PASSWORD);

    // Then
    assertThat(password.getEncodedPassword(), is(not(equalTo(STRONG_PASSWORD))));
  }

  @Test
  public void encode_shouldProduceDifferentHashes_whenProvidingSamePasswords() {
    // Given
    String password = STRONG_PASSWORD;

    // When
    EncodedPassword firstEncodedPassword = passwordService.encode(password);
    EncodedPassword secondEncodedPassword = passwordService.encode(password);

    // Then
    assertThat(
        firstEncodedPassword.getEncodedPassword(),
        is(not(equalTo(secondEncodedPassword.getEncodedPassword()))));
  }
}
