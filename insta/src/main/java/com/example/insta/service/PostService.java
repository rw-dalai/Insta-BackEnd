package com.example.insta.service;

import com.example.insta.domain.user.User;
import com.example.insta.presentation.commands.Commands.SendPostCommand;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {
  private final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

  public void sendPost(User user, SendPostCommand command, MultipartFile[] medias) {

    LOGGER.debug("{} {} {}", user, command, Arrays.toString(medias));

    //

  }
}
