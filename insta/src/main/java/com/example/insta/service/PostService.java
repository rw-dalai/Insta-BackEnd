package com.example.insta.service;

import com.example.insta.domain.media.Media;
import com.example.insta.domain.post.HashTag;
import com.example.insta.domain.post.Post;
import com.example.insta.domain.user.User;
import com.example.insta.persistence.PostRepository;
import com.example.insta.presentation.commands.Commands.SendPostCommand;
import com.example.insta.presentation.views.PostViewMapper;
import com.example.insta.presentation.views.Views;
import com.example.insta.service.media.MediaService;
import java.util.List;
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
    LOGGER.debug("User {} send post {}", user.getEmail(), command);

    // 1. Save all Medias in GridFS, in case of an error roll back (delete) any saved media so far.
    return mediaService.saveMediasTransactional(
        mediasFile,
        command.mediasMeta(),
        (List<Media> medias) -> {
          // 2. Create and Save Post in DB
          Post post = createPost(user, command, medias);
          var savedPost = postRepository.save(post);
          // 3. Return post view
          LOGGER.info("User {} send post {} successfully", user.getEmail(), command);
          return mapper.toPostView(savedPost);
        });
  }

  private Post createPost(User user, SendPostCommand command, List<Media> medias) {
    return Post.builder()
        .userId(user.getId())
        .text(command.message())
        .medias(medias)
        .hashTags(Set.of(new HashTag("dummy hashtag")))
        .likes(Set.of())
        .build();
  }
}
