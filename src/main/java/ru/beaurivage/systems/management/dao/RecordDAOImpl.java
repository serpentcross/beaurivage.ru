package ru.beaurivage.systems.management.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.beaurivage.systems.management.constants.ResponseText;
import ru.beaurivage.systems.management.model.Record;
import ru.beaurivage.systems.management.model.Response;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class RecordDAOImpl implements RecordDAO {

	@PersistenceContext private EntityManager em;

	@Autowired CustomerDAO customerDAO;

	private Response response = new Response();

	@Override
	public Response save(Record record) {
		if (record != null) {
			customerDAO.save(record.getCustomer());
			if (record.getId() == null) {
				insert(record);
			} else {
				update(record);
			}
		}

		response.setMessage(ResponseText.RECORD_ADDED_SUCCESSFULLY);
		response.setErrcode(0);

		return response;
	}

	@Override
	public Response insert(Record record) {
		em.persist(record);

		response.setMessage(ResponseText.RECORD_ADDED_SUCCESSFULLY);
		response.setErrcode(0);

		return response;
	}

	@Override
	public Response update(Record record) {
		em.merge(record);

		response.setMessage(ResponseText.RECORD_UPDATED_SUCCESSFULLY);
		response.setErrcode(0);

		return response;
	}
}
