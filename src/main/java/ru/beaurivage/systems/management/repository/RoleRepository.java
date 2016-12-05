package ru.beaurivage.systems.management.repository;

import ru.beaurivage.systems.management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
