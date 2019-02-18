package ru.beaurivage.msystem.ui.util;

import com.vaadin.server.Page;

import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

public class NotificationFactory {

    public static void constructNotification(String notificationMessage, Notification.Type notificationType, Page currentPage) {
        Notification notification = new Notification(notificationMessage, notificationType);
        notification.setDelayMsec(2000);
        notification.setPosition(Position.TOP_RIGHT);
        notification.show(currentPage);
    }
}