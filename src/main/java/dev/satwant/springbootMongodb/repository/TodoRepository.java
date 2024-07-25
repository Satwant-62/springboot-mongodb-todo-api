package dev.satwant.springbootMongodb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import dev.satwant.springbootMongodb.model.Todo;
@Repository
public interface TodoRepository extends MongoRepository<Todo, String>{
@Query("{'todo':?0}")
Optional<Todo> findByTodo(String todo);
}
