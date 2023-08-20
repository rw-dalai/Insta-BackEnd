package com.example.todo.persistence;

import com.example.todo.domain.Todo;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Spring Data
// Out of the box
// Create, Read, Update, Delete

// Magic Finders

@Repository
public interface TodoRepository extends MongoRepository<Todo, ObjectId> {
  public List<Todo> findByTitleContaining(String text);

  public List<Todo> findByTitleAndCompleted(String text, boolean completed);
}
