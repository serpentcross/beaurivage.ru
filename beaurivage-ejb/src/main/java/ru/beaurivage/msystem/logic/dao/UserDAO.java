package ru.beaurivage.msystem.logic.dao;

import ru.beaurivage.msystem.logic.entities.User;

import javax.ejb.Local;

@Local
public interface UserDAO {

    boolean getAuthState(String login, String password);
}
