package com.example.insta.persistence;

import com.example.insta.domain.user.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-lookup-strategies

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Repository to tell Spring that this is a repository

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByEmail(String email);

  //  User findByEmail(String email);

  boolean existsByEmail(String email);
  //    User findByEmail(String email);
}
