package ru.beaurivage.msystem.logic.dao;

import ru.beaurivage.msystem.logic.entities.Service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class ServiceDAOImpl implements ServiceDAO {

    @PersistenceContext(unitName = "beaurivage")
    private EntityManager em;

    @Override
    public void save(Service service) {
        if (service != null) {
            if (service.getId() == null) {
                insert(service);
            } else {
                update(service);
            }
        }
    }

    @Override
    public void insert(Service service) {
        em.persist(service);
    }

    @Override
    public void update(Service service) {
        em.merge(service);
    }

    @Override
    public List<Service> getAll() {

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Service> criteriaQuery = qb.createQuery(Service.class);
        Root<Service> root = criteriaQuery.from(Service.class);
        CriteriaQuery<Service> all = criteriaQuery.select(root);
        TypedQuery<Service> allQuery = em.createQuery(all);

        return allQuery.getResultList();
    }
}
