package com.example.insta.service.media;

import com.example.insta.domain.media.Media;
import com.example.insta.presentation.commands.Commands.MediaMetaCommand;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MediaMetaCommandMapper {
  MediaMetaCommandMapper INSTANCE = Mappers.getMapper(MediaMetaCommandMapper.class);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
  Media toMedia(MediaMetaCommand mediaMeta, ObjectId id);
}
