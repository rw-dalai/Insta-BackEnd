package com.example.insta.fixture;

import static com.example.insta.fixture.UserFixture.*;

import com.example.insta.presentation.commands.Commands.UserRegistrationCommand;

public class UserCommandFixture {
  public static final UserRegistrationCommand USER_REGISTRATION_COMMAND =
      new UserRegistrationCommand(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME);

  // ...
}
