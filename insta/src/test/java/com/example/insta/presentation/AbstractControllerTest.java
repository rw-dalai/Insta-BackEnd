package com.example.insta.presentation;

import com.example.insta.persistence.UserRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractControllerTest {
  @LocalServerPort private int port;

  @Autowired private UserRepository userRepository;

  @BeforeAll
  public void serverSetup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;

    userRepository.deleteAll();
  }
}
