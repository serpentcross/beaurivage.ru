package ru.beaurivage.systems.management.dao;

import ru.beaurivage.systems.management.model.Record;
import ru.beaurivage.systems.management.model.Response;

import java.util.List;

public interface RecordDAO {

	Response save(Record record);

	Response insert(Record record);

	Response update(Record record);

	List<Record> loadAllRecords();
}
