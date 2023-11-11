package com.example.insta.security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.insta.security.PasswordService.EncodedPassword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// What to test ?
// --------------------------------------------------------------------------------------------
// 1. Test Password Strength
// - FAIL: test weak password (zxcvbn score <= 2)
// - SUCCESS: test strong password (zxcvbn score > 2)

// 2. Test Password Encoding
// - SUCCESS: test if password is encoded (-> hash)
// - SUCCESS: test if identical passwords are encoded (-> hash) with different salt

// Annotations used?
// --------------------------------------------------------------------------------------------
// @SpringBootTest to load Spring context
// @SpringBootTest(classes = { ... }) to load Spring context with specific classes
// @TestInstance not to have to declare test methods as static
// @Autowired to inject PasswordService into this test
// @BeforeAll to run setup() once before all tests
// @Test to mark test methods

// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {PasswordService.class, SecurityConfig.class})
public class PasswordServiceTest {
  // Test Fixtures
  public static final String STRONG_PASSWORD = "alle meine 99 java entchen lieben C#";
  public static final String WEAK_PASSWORD = "password5566";

  // We need to use @Autowired because JUNIT does not support constructor injection
  @Autowired PasswordService passwordService;

  // If the PasswordEncoder is not injected by Spring, then use this setup() method
  // @Autowired private PasswordEncoder passwordEncoder;
  //    @BeforeAll
  //    public void setup() {
  //        passwordService = new PasswordService(passwordEncoder);
  //    }

  @Test
  public void encode_shouldFail_whenProvidingWeakPasswords() {
    // Given
    // PasswordService passwordService = new PasswordService();

    // When / Then
    assertThrows(IllegalArgumentException.class, () -> passwordService.encode(WEAK_PASSWORD));
  }

  @Test
  public void encode_shouldPass_whenProvidingStrongPasswords() {
    // Given
    // PasswordService passwordService = new PasswordService();

    // When
    EncodedPassword encoded = passwordService.encode(STRONG_PASSWORD);

    // Then
    assertThat(encoded, is(notNullValue()));
  }

  @Test
  public void encode_shouldReturnHashes_whenProvidingStrongPasswords() {
    // Given
    // PasswordService passwordService = new PasswordService();

    // When
    EncodedPassword password = passwordService.encode(STRONG_PASSWORD);

    // Then
    assertThat(password.getEncodedPassword(), is(not(equalTo(STRONG_PASSWORD))));
  }

  @Test
  public void encode_shouldProduceDifferentHashesForSamePassword() {
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
