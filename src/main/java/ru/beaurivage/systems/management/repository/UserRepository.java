package ru.beaurivage.systems.management.repository;

import ru.beaurivage.systems.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
