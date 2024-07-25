package dev.satwant.springbootMongodb.service;

import java.util.List;

import dev.satwant.springbootMongodb.exception.TodoCollectionException;
import dev.satwant.springbootMongodb.model.Todo;
import jakarta.validation.ConstraintViolationException;

public interface TodoService{
public void createTodo(Todo todo) throws ConstraintViolationException, TodoCollectionException;
public List<Todo> getAllTodos();
public Todo getSingleTodo(String id) throws TodoCollectionException;
public void updateTodo(String id, Todo todo) throws TodoCollectionException;
public void deleteTodoById(String id) throws TodoCollectionException;
}
