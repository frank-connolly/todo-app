package io.frankconnolly.todoapp;

import io.frankconnolly.todoapp.model.Todo;
import io.frankconnolly.todoapp.model.User;
import io.frankconnolly.todoapp.repository.TodoRepository;
import io.frankconnolly.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoAppApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TodoRepository todoRepository;

    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }

    @Override
    public void run(String... args) {

        User user = new User();
        user.setPassword("should be hashed");
        user.setUsername("John");

        Todo todo = new Todo();
        todo.setContent("Initial commit");

        user.getTodoList().add(todo);

        userRepository.save(user);
        todoRepository.save(todo);
    }
}
