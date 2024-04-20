package com.example.insta.service;

import com.example.insta.domain.post.Post;
import com.example.insta.domain.user.User;
import com.example.insta.persistence.PostRepository;
import com.example.insta.presentation.views.LoginViewMapper;
import com.example.insta.presentation.views.Views.LoginView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  private final LoginViewMapper mapper = LoginViewMapper.INSTANCE;

  private final PostRepository postRepository;

  // private final MessengerRepository messengerRepository;

  public LoginView login(User user) {
    LOGGER.debug("User login {}", user);

    // Fetch all associated data for that user from repositories (e.g. posts, messengers, etc.).
    List<Post> posts = postRepository.findByUserId(user.getId());
    // List<Messenger> messenger = messengerRepository.findByParticipantId(user.getId());

    LoginView loginView = mapper.toLoginView(user, posts);
    LOGGER.debug("User login sucessfull {}", loginView);
    return loginView;
  }
}
