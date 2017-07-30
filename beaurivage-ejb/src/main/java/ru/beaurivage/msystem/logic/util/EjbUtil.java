package ru.beaurivage.msystem.logic.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EjbUtil {

    public static <T> T getLocalBean(Class<T> beanInterface) {
        return (T) getBeanByFullName(beanInterface.getSimpleName());
    }

    private static Object getBeanByFullName(String simpleName) {
        try {
            return new InitialContext().lookup("java:global/beaurivage-ear-1.0/beaurivage-web-1.0/" + simpleName + "Impl");
        } catch (NamingException ex) {
            System.err.println(ex);
            return null;
        }
    }
}
