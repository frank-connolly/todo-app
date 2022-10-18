package io.frankconnolly.todoapp.api.controllers;

import io.frankconnolly.todoapp.api.requests.AddTodoRequest;
import io.frankconnolly.todoapp.api.requests.AddUserRequest;
import io.frankconnolly.todoapp.model.Todo;
import io.frankconnolly.todoapp.model.User;
import io.frankconnolly.todoapp.repository.TodoRepository;
import io.frankconnolly.todoapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public UserController(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userRepository.findById(userId).orElseThrow(
                ()-> new NoSuchElementException("No user found with ID: " + userId)),
                HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> addUser(@RequestBody AddUserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> addTodo(@PathVariable Long userId, @RequestBody AddTodoRequest todoRequest) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new NoSuchElementException("No user found with ID: " + userId));
        Todo todo = new Todo();
        todo.setContent(todoRequest.getContent());
        user.getTodoList().add(todo);
        todoRepository.save(todo);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @PatchMapping("/todos/{todoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> toggleTodoCompleted(@PathVariable Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                ()-> new NoSuchElementException("No todo found with ID: " + todoId));
        todo.setCompletedStatus(!todo.getCompletedStatus());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{userId}/todos/{todoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable Long userId, @PathVariable Long todoId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new NoSuchElementException("No user found with ID: " + userId));
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                ()-> new NoSuchElementException("No todo found with ID: " + todoId));
        user.getTodoList().remove(todo);
        todoRepository.delete(todo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new NoSuchElementException("No user found with ID: " + userId));
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}