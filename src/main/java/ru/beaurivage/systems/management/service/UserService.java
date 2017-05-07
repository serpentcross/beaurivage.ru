package ru.beaurivage.systems.management.service;

import ru.beaurivage.systems.management.model.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
