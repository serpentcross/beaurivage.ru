package ru.beaurivage.systems.management.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.beaurivage.systems.management.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class CustomerDAOImpl implements CustomerDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Customer customer) {
		if (customer != null) {
			if (customer.getId() == null) {
				insert(customer);
			} else {
				update(customer);
			}
		}
	}

	@Override
	public void insert(Customer customer) {
		em.persist(customer);
	}

	@Override
	public void update(Customer customer) {
		em.merge(customer);
	}
}
