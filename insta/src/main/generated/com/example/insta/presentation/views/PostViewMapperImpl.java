package com.example.insta.presentation.views;

import com.example.insta.domain.media.Media;
import com.example.insta.domain.post.HashTag;
import com.example.insta.domain.post.Post;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-29T12:17:57+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class PostViewMapperImpl implements PostViewMapper {

    @Override
    public Views.PostView toPostView(Post post) {
        if ( post == null ) {
            return null;
        }

        String creatorId = null;
        Media thumb = null;
        String id = null;
        String text = null;
        Set<HashTag> hashTags = null;

        creatorId = post.getUserId();
        thumb = PostViewMapper.selectThumb( post.getMedias() );
        id = post.getId();
        text = post.getText();
        Set<HashTag> set = post.getHashTags();
        if ( set != null ) {
            hashTags = new LinkedHashSet<HashTag>( set );
        }

        int likes = post.getLikes().size();

        Views.PostView postView = new Views.PostView( id, creatorId, text, thumb, hashTags, likes );

        return postView;
    }
}
