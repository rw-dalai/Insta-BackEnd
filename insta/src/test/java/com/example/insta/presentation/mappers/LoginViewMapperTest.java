package com.example.insta.presentation.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.example.insta.domain.post.Post;
import com.example.insta.domain.user.User;
import com.example.insta.fixture.PostFixture;
import com.example.insta.fixture.UserFixture;
import com.example.insta.presentation.views.LoginViewMapper;
import com.example.insta.presentation.views.Views.LoginView;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class LoginViewMapperTest {

  // SUT_shouldXXX_whenXXX

  @Test
  public void toLoginView_shouldMapParamsToLoginView() {

    // Given
    User user = UserFixture.createUser();
    // List<Post> posts = Stream.generate(() -> PostFixture.createTextPost()).limit(3).toList();
    List<Post> posts = Stream.generate(PostFixture::createTextPost).limit(3).toList();
    // List<Post> posts = Arrays.asList(PostFixture.createTextPost());
    LoginViewMapper mapper = LoginViewMapper.INSTANCE;

    // When
    LoginView loginView = mapper.toLoginView(user, posts);

    // Then
    assertThat(loginView, notNullValue());
    assertThat(loginView.user(), notNullValue());
    // assertThat(loginView.posts(), notNullValue());

  }
}
