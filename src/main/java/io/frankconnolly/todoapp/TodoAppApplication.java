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
    public void run(String... args) throws Exception {

        User user = new User();
        user.setId(1L);
        user.setPassword("should be hashed");
        user.setUsername("John");

        Todo todo = new Todo();
        todo.setId(1L);
        todo.setContent("Complete initial commit for todo app");

        user.getTodoList().add(todo);

        todoRepository.save(todo);
        userRepository.save(user);
    }
}
