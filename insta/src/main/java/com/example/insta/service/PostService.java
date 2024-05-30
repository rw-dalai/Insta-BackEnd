package com.example.insta.service;

import com.example.insta.domain.post.HashTag;
import com.example.insta.domain.post.Post;
import com.example.insta.domain.user.User;
import com.example.insta.persistence.PostRepository;
import com.example.insta.presentation.commands.Commands.SendPostCommand;
import com.example.insta.presentation.views.PostViewMapper;
import com.example.insta.presentation.views.Views;
import com.example.insta.service.media.MediaService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {
  private final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

  private final PostRepository postRepository;
  private final MediaService mediaService;
  private final PostViewMapper mapper = PostViewMapper.INSTANCE;

  public Views.PostView sendPost(User user, SendPostCommand command, MultipartFile[] mediasFile) {
    LOGGER.debug("User {} send post {}", user, command);

    // This is a sugared helper method that does the saving of media of us, in a case of an error,
    // its rollbacks.
    var postView =
        mediaService.saveMediasWithTransaction(
            mediasFile,
            command.mediasMeta(),
            (medias) -> {
              // After we have successfully saved the all media files to GridFS ...
              // 1- We build a post
              var post =
                  Post.builder()
                      .userId(user.getId())
                      .text(command.message())
                      .medias(medias)
                      .hashTags(Set.of(new HashTag("dummy hashtag")))
                      .likes(Set.of())
                      .build();
              // 2- Save the post to the DB
              var savedPost = postRepository.save(post);
              // 3- Return a post view
              return mapper.toPostView(savedPost);
            });

    LOGGER.info("User {} send post {} successfully", user, command);
    return postView;
  }
}
