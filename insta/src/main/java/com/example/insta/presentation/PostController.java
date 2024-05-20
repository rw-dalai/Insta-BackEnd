package com.example.insta.presentation;

import static com.example.insta.foundation.MediaUtil.getNonNullMultiPart;

import com.example.insta.presentation.commands.Commands.SendPostCommand;
import com.example.insta.security.web.SecurityUser;
import com.example.insta.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
  private final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationController.class);

  private final PostService postService;

  //    @RequestPart(name = "command") SendPostCommand command
  // ----------------------------------------------------------------
  //    Content-Disposition: form-data; name="command"; filename="blob"
  //    Content-Type: application/json
  //    {"message":"default
  // message","mediasMeta":[{"filename":"beach.webp","mimeType":"image/webp","size":252624,"width":3060,"height":2040},{"filename":"beach2.jpg","mimeType":"image/jpeg","size":307594,"width":975,"height":655}],"type":"[Post/API] Send Message"}

  // HTTP POST ContentType = "application/form-data
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public void sendPost(
      @AuthenticationPrincipal SecurityUser principal,
      @RequestPart(name = "command") SendPostCommand command,
      @RequestPart(name = "medias", required = false) @Nullable MultipartFile[] medias) {
    this.postService.sendPost(principal.getUser(), command, getNonNullMultiPart(medias));
  }
}
