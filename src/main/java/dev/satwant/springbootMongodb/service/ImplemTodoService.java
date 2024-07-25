package dev.satwant.springbootMongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.satwant.springbootMongodb.exception.TodoCollectionException;
import dev.satwant.springbootMongodb.model.Todo;
import dev.satwant.springbootMongodb.repository.TodoRepository;
import jakarta.validation.ConstraintViolationException;

@Service
public class ImplemTodoService implements TodoService{
@Autowired
private TodoRepository todoRepository;
@Override
public void createTodo(Todo todo) throws ConstraintViolationException, TodoCollectionException{
	Optional<Todo> todoOptional=todoRepository.findByTodo(todo.getTodo());
	if(todoOptional.isPresent())
		throw new TodoCollectionException(TodoCollectionException.alreadyExists());
	else{
		todo.setCreatedAt(new Date(System.currentTimeMillis()));
		todoRepository.save(todo);
	}
}
public List<Todo> getAllTodos(){
	List<Todo> todos = todoRepository.findAll();
	if(todos.isEmpty())
		return new ArrayList<Todo>();
		else
			return todos;
}
public Todo getSingleTodo(String id) throws TodoCollectionException{
	Optional<Todo> todoOptional = todoRepository.findById(id);
	if(todoOptional.isPresent())
		return todoOptional.get();
	else
		throw new TodoCollectionException(TodoCollectionException.notFound(id));
}
@Override
public void updateTodo(String id, Todo todo) throws TodoCollectionException {
	Optional<Todo> todoWithId = todoRepository.findById(id);
	Optional<Todo> todoWithSameName = todoRepository.findByTodo(todo.getTodo());
	if(todoWithId.isPresent()) {
		if(todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id))
			throw new TodoCollectionException(TodoCollectionException.alreadyExists());
		Todo updateTodo = todoWithId.get();
		updateTodo.setTodo(todo.getTodo());
		updateTodo.setDescription(todo.getDescription());
		updateTodo.setCompleted(todo.getCompleted());
		updateTodo.setUpdatedAt(new Date(System.currentTimeMillis()));
		todoRepository.save(updateTodo);
	}else
		throw new TodoCollectionException(TodoCollectionException.notFound(id));
}
@Override
public void deleteTodoById(String id) throws TodoCollectionException{
	Optional<Todo> todoOptional = todoRepository.findById(id);
	if(todoOptional.isPresent())
		todoRepository.deleteById(id);
	else
		throw new TodoCollectionException(TodoCollectionException.notFound(id));
}
}
