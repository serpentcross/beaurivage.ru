package ru.beaurivage.msystem.logic.services;

import ru.beaurivage.msystem.logic.dao.UserDAO;
import ru.beaurivage.msystem.logic.util.EjbUtil;

import java.math.BigInteger;

import java.security.SecureRandom;

import java.util.HashMap;
import java.util.Map;

class UserService {

    private static final SecureRandom random = new SecureRandom();

    private static final Map<String, String> rememberedUsers = new HashMap<>();

    static boolean isAuthenticUser(String username, String password) {
        UserDAO userDAO = EjbUtil.getLocalBean(UserDAO.class);
        return userDAO.getAuthState(username, password);
    }

    static String rememberUser(String username) {
        String randomId = new BigInteger(130, random).toString(32);
        rememberedUsers.put(randomId, username);
        return randomId;
    }

    static String getRememberedUser(String id) {
        return rememberedUsers.get(id);
    }

    static void removeRememberedUser(String id) {
        rememberedUsers.remove(id);
    }

}