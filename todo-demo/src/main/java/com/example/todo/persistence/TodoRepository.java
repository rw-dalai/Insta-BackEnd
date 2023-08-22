package com.example.todo.persistence;

import com.example.todo.domain.Todo;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Spring Data
// -----------

// 1. Out of the box
// - Create, Read, Update, Delete

// 2. Magic Finders
// - findByTitleContaining, findByTitleAndCompleted, ...

// 3. Custom Queries
// - @Query("select t from Todo t where title like :title")
// - @Query(value = "{'title': {$regex : ?0, $options: 'i'}}")

// 4. Custom Repository
// - TodoRepositoryCustom

// Spring Data Docs
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface

// Spring Data Magic Finders
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-lookup-strategies

@Repository
public interface TodoRepository extends MongoRepository<Todo, ObjectId> {
  public List<Todo> findByTitleContaining(String text);

  public List<Todo> findByTitleAndCompleted(String text, boolean completed);
}
