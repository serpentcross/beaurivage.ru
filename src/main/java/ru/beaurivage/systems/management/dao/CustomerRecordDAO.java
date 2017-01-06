package ru.beaurivage.systems.management.dao;

import ru.beaurivage.systems.management.model.CustomerRecord;

import javax.xml.ws.Response;

public interface CustomerRecordDAO {
    void postRecord(CustomerRecord customerRecord);
}
