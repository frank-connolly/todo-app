package io.frankconnolly.todoapp.repository;

import io.frankconnolly.todoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}