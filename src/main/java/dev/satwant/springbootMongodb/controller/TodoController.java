package dev.satwant.springbootMongodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.satwant.springbootMongodb.exception.TodoCollectionException;
import dev.satwant.springbootMongodb.model.Todo;
import dev.satwant.springbootMongodb.service.TodoService;
import jakarta.validation.ConstraintViolationException;

@RestController
public class TodoController{

@Autowired
private TodoService todoService;
@PostMapping("/todo") // get and post can happen at same url
public ResponseEntity<?> createNewTodo(@RequestBody Todo todo){
	try{
		//		todo.setCreatedAt(new Date(System.currentTimeMillis())); because it is added in the service layer.
		//		todoRepository.save(todo);
		todoService.createTodo(todo);
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}catch(ConstraintViolationException e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		//		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}catch(TodoCollectionException e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}
}

@GetMapping("/todos")
public ResponseEntity<?> readAllTodos(){ // rest end point to get all todos
	List<Todo> todos=todoService.getAllTodos();
	return new ResponseEntity<List<Todo>>(todos, todos.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
}

@GetMapping("/todo/{id}")
public ResponseEntity<?> readTodoById(@PathVariable String id){
//	Optional<Todo> todo=todoRepository.findById(id);
	try {
		return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);	
	}catch(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}		
}

@PutMapping("/todo/{id}")
public ResponseEntity<?> updateTodoById(@PathVariable String id, @RequestBody Todo todo){
	try {
		todoService.updateTodo(id, todo);
		return new ResponseEntity<>("todo with id: "+id+" get updated.", HttpStatus.OK);
	}catch(ConstraintViolationException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}catch(TodoCollectionException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	/* Optional<Todo> todoOfId=todoRepository.findById(id);
	 if(todoOfId.isPresent()){
		Todo todoToSave=todoOfId.get();
		if(todo.getCompleted()!=null)
			todoToSave.setCompleted(todo.getCompleted());
		if(todo.getTodo()!=null)
			todoToSave.setTodo(todo.getTodo());
		if(todo.getDescription()!=null)
			todoToSave.setDescription(todo.getDescription());
		try{
			todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
			todoRepository.save(todoToSave);
		}catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(todoToSave, HttpStatus.OK);
	}else
		return new ResponseEntity<>("No todo found with id"+id, HttpStatus.NOT_FOUND); */
}

@DeleteMapping("/todo/{id}")
public ResponseEntity<?> deleteTodoById(@PathVariable String id){
	try{
		todoService.deleteTodoById(id);
		return new ResponseEntity<>("Successfully deleted, todo with id"+id+".", HttpStatus.OK);
	}catch(TodoCollectionException e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
}
}
