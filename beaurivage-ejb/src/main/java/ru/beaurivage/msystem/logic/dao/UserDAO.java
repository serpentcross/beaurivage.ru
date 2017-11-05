package ru.beaurivage.msystem.logic.dao;

import javax.ejb.Local;

@Local
public interface UserDAO {

    boolean getAuthState(String login, String password);
}
