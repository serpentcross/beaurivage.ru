package ru.beaurivage.msystem.logic.services;

import ru.beaurivage.msystem.logic.dao.PatientDAO;
import ru.beaurivage.msystem.logic.dao.UserDAO;
import ru.beaurivage.msystem.logic.util.EjbUtil;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    private static UserDAO userDAO;

    private static SecureRandom random = new SecureRandom();

    private static Map<String, String> rememberedUsers = new HashMap<String, String>();

    public static boolean isAuthenticUser(String username, String password) {
        userDAO = EjbUtil.getLocalBean(UserDAO.class);
        return userDAO.getAuthState(username, password);
        //return username.equals("admin") && password.equals("password");
    }

    public static String rememberUser(String username) {
        String randomId = new BigInteger(130, random).toString(32);
        rememberedUsers.put(randomId, username);
        return randomId;
    }

    public static String getRememberedUser(String id) {
        return rememberedUsers.get(id);
    }

    public static void removeRememberedUser(String id) {
        rememberedUsers.remove(id);
    }

}