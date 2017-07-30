package ru.beaurivage.msystem.logic.dao;

import ru.beaurivage.msystem.logic.entities.Record;

import javax.ejb.Local;
import java.util.List;

@Local
public interface RecordDAO {

    void save(Record record);

    void insert(Record record);

    void update(Record record);

    List<Record> getAll();

}
