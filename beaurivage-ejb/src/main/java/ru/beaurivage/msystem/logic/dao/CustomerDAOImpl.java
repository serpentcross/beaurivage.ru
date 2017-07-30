package ru.beaurivage.msystem.logic.dao;

import ru.beaurivage.msystem.logic.entities.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class CustomerDAOImpl implements CustomerDAO {

    @PersistenceContext(unitName = "beaurivage")
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

    @Override
    public List<Customer> getAll() {

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = qb.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);
        CriteriaQuery<Customer> all = criteriaQuery.select(root);
        TypedQuery<Customer> allQuery = em.createQuery(all);

        return allQuery.getResultList();
    }
}
