package com.example.insta.presentation;

import static io.restassured.RestAssured.given;

import com.example.insta.presentation.commands.Commands.UserRegistrationCommand;
import io.restassured.response.Response;

public abstract class ApiHelpers {
  public static final String API_REGISTRATION = "/api/registration";

  public static Response registerUser(UserRegistrationCommand command) {
    // spotless:off
    Response response =
        given()
            .contentType("application/json")
            .body(command)
            .when()
            .post(API_REGISTRATION)
            .then()
            .extract()
            .response();
    // spotless:on

    return response;
  }
}
