package io.frankconnolly.todoapp.api.controllers;

import io.frankconnolly.todoapp.api.requests.AddTodoRequest;
import io.frankconnolly.todoapp.api.requests.AddUserRequest;
import io.frankconnolly.todoapp.model.Todo;
import io.frankconnolly.todoapp.model.User;
import io.frankconnolly.todoapp.repository.TodoRepository;
import io.frankconnolly.todoapp.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

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
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    }

    @PostMapping
    public User addUser(@RequestBody AddUserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        return userRepository.save(user);
    }

    @PostMapping("/{userId}/todos")
    public void addTodo(@PathVariable Long userId, @RequestBody AddTodoRequest todoRequest) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Todo todo = new Todo();
        todo.setContent(todoRequest.getContent());
        user.getTodoList().add(todo);
        todoRepository.save(todo);
        userRepository.save(user);
    }

    @PostMapping("/todos/{todoId}")
    public void toggleTodoCompleted(@PathVariable Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(NoSuchElementException::new);
        todo.setCompletedStatus(!todo.getCompletedStatus());
        todoRepository.save(todo);
    }

    @DeleteMapping("{userId}/todos/{todoId}")
    public void deleteTodo(@PathVariable Long userId, @PathVariable Long todoId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Todo todo = todoRepository.findById(todoId).orElseThrow(NoSuchElementException::new);
        user.getTodoList().remove(todo);
        todoRepository.delete(todo);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        userRepository.delete(user);
    }
}