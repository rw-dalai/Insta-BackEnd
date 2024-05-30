package com.example.insta.presentation;

import com.example.insta.service.media.MediaService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
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

// @RestController
// @RequestMapping("/api/media")
// public class MediaController {
//
//  private final Logger LOGGER = LoggerFactory.getLogger(MediaController.class);
//
//  private static final Path STORAGE_LOCATION = Path.of("/static/media");
//
//  @GetMapping("/{filename}")
//  public ResponseEntity<Resource> serveMedia(@PathVariable String filename) {
//
//    try {
//      // /static/media/dog.jpg
//      Path pathToFile = STORAGE_LOCATION.resolve(filename); /*.normalize();*/
//
//      // Resource resource = new FileSystemResource()
//      Resource resource = new ClassPathResource(pathToFile.toString());
//      String mimeType = Files.probeContentType(pathToFile);
//
//      LOGGER.debug("filename: {}, mimeType: {}", resource.getFile(), mimeType);
//
//      return ResponseEntity
//          // Status Code 200
//          .ok()
//
//          // We do not need to set the content length because it is automatically set by Spring
//          // .contentLength()
//
//          // Content Type is image/jpeg, image/png, video/mp4, etc.
//          // Its important so that the browser knows how to display the content.
//          .contentType(MediaType.parseMediaType(mimeType))
//          //          .contentType(MediaType.APPLICATION_JSON)
//
//          // Send the resource as the response body to the client
//          // Since we set the content type to a media type spring will not try to convert the
//          // resource to JSON
//          .body(resource);
//
//      // return resource;
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//  }
// }

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {
  private final MediaService mediaService;

  // On the frontend, we can use the following to display the image:
  // <img src="http://localhost:8080/api/media/1234">

  @GetMapping("/{mediaId}")
  public ResponseEntity<Resource> downloadMedia(@PathVariable ObjectId mediaId) {
    var result = mediaService.downloadMedia(mediaId);

    Resource resource = result.getFirst();
    String mimeType = result.getSecond();

    // STATUS CODE: 200 OK
    return ResponseEntity.ok()
        // CONTENT LENGTH: Not needed, Spring will set the content length automatically.
        // .contentLength(resource.contentLength())

        // CONTENT TYPE: We have to set the content type header, otherwise application/json set.
        .contentType(MediaType.parseMediaType(mimeType))

        // CACHE CONTROL: We set the cache control header to cache the resource for 365 days.
        .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS).cachePublic().immutable())
        .body(resource);

    // CACHE INFO:
    // Freshness: Defined by max-age. A resource is fresh if requested within this period.
    // Staleness: After max-age, the resource becomes stale (outdated).

    // max-age: Specifies how long the resource is considered fresh.
    // public: Allows the resource to be cached by both private and shared caches.
    // immutable: Indicates that the resource will not change.
  }
}
