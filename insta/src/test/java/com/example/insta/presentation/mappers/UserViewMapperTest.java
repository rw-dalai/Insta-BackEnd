package com.example.insta.presentation.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.example.insta.domain.user.User;
import com.example.insta.fixture.UserFixture;
import com.example.insta.presentation.views.UserViewMapper;
import com.example.insta.presentation.views.Views.UserView;
import org.junit.jupiter.api.Test;

public class UserViewMapperTest {

  // SUT_shouldXXX_whenXXX
  @Test
  public void toUserView_shouldMapUserToUserView() {

    // Given
    User user = UserFixture.createUser();
    UserViewMapper mapper = UserViewMapper.INSTANCE;

    // When
    UserView userView = mapper.toUserView(user);

    // Then
    assertThat(userView, notNullValue());
    assertThat(userView.id(), equalTo(user.getId()));
  }
}
