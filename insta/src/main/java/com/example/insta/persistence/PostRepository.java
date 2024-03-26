package com.example.insta.persistence;

import com.example.insta.domain.post.Post;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-lookup-strategies

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Repository to tell Spring that this is a repository

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

  List<Post> findByUserId(String userId);
}
