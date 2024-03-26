package com.example.insta.presentation.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.example.insta.domain.post.Post;
import com.example.insta.fixture.PostFixture;
import com.example.insta.presentation.views.PostViewMapper;
import org.junit.jupiter.api.Test;

public class PostViewMapperTest {

  // SUT_shouldXXX_whenXXX

  @Test
  public void toPostView_shouldMapPostToPostView() {

    // Given
    Post post = PostFixture.createTextPost();
    PostViewMapper mapper = PostViewMapper.INSTANCE;

    // When
    var postView = mapper.toPostView(post);

    // Then
    assertThat(postView, notNullValue());
    assertThat(postView.id(), equalTo(post.getId()));
  }
}
