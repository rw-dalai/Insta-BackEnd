package com.example.insta.presentation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// ClasspathResource
// A class path resource is a resource that is loaded from the classpath. e.g. src/main/resources

// FileSystemResource
// A file system resource is a resource that is loaded from the local file system

// UrlResource
// A URL resource is a resource that is loaded from a URL e.g. http://example.com,
// file://example.com

@RestController
@RequestMapping("/api/media")
public class MediaController {

  private final Logger LOGGER = LoggerFactory.getLogger(MediaController.class);

  private static final Path STORAGE_LOCATION = Path.of("/static/media");

  @GetMapping("/{filename}")
  public ResponseEntity<Resource> serveMedia(@PathVariable String filename) {

    try {
      // /static/media/dog.jpg
      Path pathToFile = STORAGE_LOCATION.resolve(filename); /*.normalize();*/

      // Resource resource = new FileSystemResource()
      Resource resource = new ClassPathResource(pathToFile.toString());
      String mimeType = Files.probeContentType(pathToFile);

      LOGGER.debug("filename: {}, mimeType: {}", resource.getFile(), mimeType);

      return ResponseEntity
          // Status Code 200
          .ok()

          // We do not need to set the content length because it is automatically set by Spring
          // .contentLength()

          // Content Type is image/jpeg, image/png, video/mp4, etc.
          // Its important so that the browser knows how to display the content.
          .contentType(MediaType.parseMediaType(mimeType))
          //          .contentType(MediaType.APPLICATION_JSON)

          // Send the resource as the response body to the client
          // Since we set the content type to a media type spring will not try to convert the
          // resource to JSON
          .body(resource);

      // return resource;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
