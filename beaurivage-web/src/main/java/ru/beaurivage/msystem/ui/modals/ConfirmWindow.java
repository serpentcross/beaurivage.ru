package ru.beaurivage.msystem.ui.modals;

import com.vaadin.server.Page;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;

import ru.beaurivage.msystem.logic.dao.RecordDAO;
import ru.beaurivage.msystem.logic.entities.Record;
import ru.beaurivage.msystem.logic.util.EjbUtil;

import ru.beaurivage.msystem.ui.constants.Notifications;
import ru.beaurivage.msystem.ui.util.NotificationFactory;

public class ConfirmWindow extends Window {

    private final RecordDAO recordDAO;

    public ConfirmWindow(Record record) {

        Button acceptButton = new Button("Да");
        acceptButton.setWidth("130px");

        Button cancelButton = new Button("Нет", event -> close());
        cancelButton.setWidth("130px");

        FormLayout formLayout = new FormLayout();
        GridLayout buttonLayout = new GridLayout(2 ,1);

        setCaption("Удаление записи № " + record.getId());
        setClosable(false);
        setDraggable(false);
        setModal(true);
        setResizable(false);

        setWidth("300px");

        acceptButton.addClickListener(e -> deleteRecord(record));

        buttonLayout.addComponent(acceptButton, 0,0);
        buttonLayout.setComponentAlignment(acceptButton, Alignment.MIDDLE_LEFT);

        buttonLayout.addComponent(cancelButton,1,0);
        buttonLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_RIGHT);

        buttonLayout.setSpacing(true);

        formLayout.setSpacing(true);
        formLayout.addComponent(buttonLayout);
        formLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);

        recordDAO = EjbUtil.getLocalBean(RecordDAO.class);
        setContent(formLayout);
    }

    private void deleteRecord(Record record) {
        recordDAO.delete(record);
        NotificationFactory.constructNotification(Notifications.RECORD_DELETED_SUCCESS, Notification.Type.HUMANIZED_MESSAGE, Page.getCurrent());
        this.close();
    }
}