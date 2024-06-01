package com.example.insta.service.media;

import static com.example.insta.foundation.AssertUtil.isTrue;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.example.insta.domain.media.Media;
import com.example.insta.presentation.commands.Commands.MediaMetaCommand;
import com.mongodb.client.gridfs.model.GridFSFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// What is GridFS?
// ---------------
// https://www.mongodb.com/docs/manual/core/gridfs/
// GridFS is a specification for storing and retrieving files
// that exceed the BSON-document size limit of 16 MB.

// How GridFS works?
// -----------------
// GridFS works by dividing the large file into smaller chunks, typically of size 255 KB,
// and storing each of those chunks as a separate document in MongoDB.

// This means that instead of storing a large file as a single document,
// GridFS creates multiple smaller documents.

// In GridFS, two collections are created to handle this data:
// - fs.files: This collection is used to store the file's metadata (we store filename and mimetype)
// - fs.chunks: This collection contains the actual content of the file, stored in multiple
// documents.

@Service
@RequiredArgsConstructor
public class MediaService {
  private final Logger LOGGER = LoggerFactory.getLogger(MediaService.class);
  private static final MediaMetaCommandMapper mapper = MediaMetaCommandMapper.INSTANCE;

  // Spring's gateway to the MongoDB GridFS
  private final GridFsTemplate gridFsTemplate;

  // Save Medias -------------------------------------------------------------

  public List<Media> saveMedias(MultipartFile[] mediaFiles, MediaMetaCommand[] mediaMetas) {
    List<Media> medias = new ArrayList<>(mediaFiles.length);

    try {
      // Frontend must send the same number of media files and media metas
      isTrue(
          mediaFiles.length == mediaMetas.length,
          "mediaFiles and mediaMetas must have the same length");

      LOGGER.info("Saving {} medias", mediaFiles.length);

      for (int i = 0; i < mediaFiles.length; i++) {
        medias.add(saveMedia(mediaFiles[i], mediaMetas[i]));
      }

      return medias;

    } catch (Exception e) {
      LOGGER.error("Could not save all {} medias", medias.size());
      rollback(medias);
      throw e;
    }
  }

  public Media saveMedia(MultipartFile mediaFile, MediaMetaCommand mediaMeta) {
    try {
      // Frontend must send the same filename in mediaMeta and mediaFile
      isTrue(
          Objects.equals(mediaFile.getOriginalFilename(), mediaMeta.filename()),
          "mediaFile and mediaMeta must have the same filename");

      LOGGER.info("Saving media: {} with size {}", mediaMeta.filename(), mediaMeta.size());

      // Stores the media in fs.files (metadata) and fs.chunks(binary) collections.
      ObjectId mediaId =
          gridFsTemplate.store(
              mediaFile.getInputStream(), mediaMeta.filename(), mediaMeta.mimeType());

      // Maps the MediaMetaCommand to a Media
      return mapper.toMedia(mediaMeta, mediaId);

    } catch (Exception e) {
      throw new MediaServiceException("Failed to save media", e);
    }
  }

  // Retrieve Media ----------------------------------------------------------

  // This method returns a Pair containing both the Resource and the mime type of the media.
  public Pair<Resource, String> retrieveMedia(ObjectId mediaId) {
    try {
      LOGGER.info("Downloading media: {}", mediaId);

      // Gets the GridFSFile from fs.files (metadata)
      GridFSFile file = gridFsTemplate.findOne(query(where("_id").is(mediaId)));

      // Gets the GridFsResource from fs.chunks (binary data)
      GridFsResource resource = gridFsTemplate.getResource(file);

      // Returning a Pair of Resource and MimeType is useful for the controller
      // to set the content type
      return Pair.of(resource, getMimeType(file));

    } catch (Exception e) {
      throw new MediaServiceException("Failed to download media", e);
    }
  }

  // Delete Medias -----------------------------------------------------------

  // Deletes all medias from MongoDB GridFS in a _bulk_operation_
  public void deleteMedias(List<Media> medias) {
    deleteMediasById(Media.toMediaIds(medias));
  }

  // Deletes all mediaIds from MongoDB GridFS in a _bulk_operation_
  public void deleteMediasById(List<ObjectId> mediaIds) {
    try {
      LOGGER.info("Deleting {} medias", mediaIds.size());
      mediaIds.forEach(mediaId -> LOGGER.info("Deleting media: {}", mediaId));

      gridFsTemplate.delete(query(where("_id").in(mediaIds)));

    } catch (Exception e) {
      throw new MediaServiceException("Failed to delete medias", e);
    }
  }

  // Deletes a single media from MongoDB GridFS
  public void deleteMediaById(ObjectId mediaId) {
    try {
      LOGGER.info("Deleting media: {}", mediaId);
      gridFsTemplate.delete(query(where("_id").is(mediaId)));

    } catch (Exception e) {
      throw new MediaServiceException("Failed to delete media", e);
    }
  }

  // Transaction Helper ------------------------------------------------------

  // This is a helper method to execute a transaction with a list of medias.
  // e.g. save a post along with its medias in a single transaction.
  public <T> T saveMediasTransactional(
      MultipartFile[] mediaFiles,
      MediaMetaCommand[] mediaMetas,
      Function<List<Media>, T> transaction) {
    List<Media> medias = null;
    try {
      medias = saveMedias(mediaFiles, mediaMetas);
      return transaction.apply(medias);
    } catch (Exception e) {
      // Only rollback the medias if `transaction.apply(medias);` fails
      if (medias != null) rollback(medias);
      throw new MediaServiceException("Media Transaction failed", e);
    }
  }

  // This is a helper method to execute a transaction with a single media.
  // e.g. save a user along with its profile picture in a single transaction.
  public <T> T saveMediaTransactional(
      MultipartFile mediaFiles, MediaMetaCommand mediaMetas, Function<Media, T> transaction) {
    Media media = null;
    try {
      media = saveMedia(mediaFiles, mediaMetas);
      return transaction.apply(media);
    } catch (Exception e) {
      // Only rollback the medias if `transaction.apply(media);` fails
      if (media != null) rollback(List.of(media));
      throw new MediaServiceException("Media Transaction failed", e);
    }
  }

  // Rollback Medias ---------------------------------------------------------

  public void rollback(List<Media> medias) {
    LOGGER.warn("Rollback {} medias", medias.size());
    deleteMedias(medias);
  }

  // Mime Type ---------------------------------------------------------------

  // Returns the MIME type of GridFSFile, this is stored in the metadata.
  // We check if the metadata exists and the _contentType key exists otherwise we return the
  // default MIME type.
  private String getMimeType(GridFSFile file) {
    return Optional.ofNullable(file.getMetadata()) // could be null
        .map(metadata -> metadata.get("_contentType")) // could be null
        .map(Object::toString)
        .orElse("application/octet-stream");
  }
}
