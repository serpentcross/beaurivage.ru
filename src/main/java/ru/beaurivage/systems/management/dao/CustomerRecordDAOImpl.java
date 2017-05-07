package ru.beaurivage.systems.management.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.beaurivage.systems.management.model.CustomerRecord;

import javax.xml.ws.Response;

@Service("customerRecordDAO")
@Transactional
public class CustomerRecordDAOImpl implements CustomerRecordDAO {

    @Override
    public void postRecord(CustomerRecord customerRecord) {
        System.out.println("sdfsfsd");
    }
}
