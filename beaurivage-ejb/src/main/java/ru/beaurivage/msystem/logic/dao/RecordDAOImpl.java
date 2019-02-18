package ru.beaurivage.msystem.logic.dao;

import ru.beaurivage.msystem.logic.entities.Record;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

@Stateless
public class RecordDAOImpl implements RecordDAO {

    @PersistenceContext(unitName = "beaurivage")
    private EntityManager em;

    @EJB
    private PatientDAO patientDAO;

    @Override
    public void save(Record record) {
        if (record != null) {
            patientDAO.save(record.getPatient());
            if (record.getId() == null) {
                insert(record);
            } else {
                update(record);
            }
        }
    }

    @Override
    public void insert(Record record) {
        em.persist(record);
    }

    @Override
    public void update(Record record) {
        em.merge(record);
    }

    @Override
    public void delete(Record record) {
        em.remove(em.contains(record) ? record : em.merge(record));
    }

    @Override
    public List<Record> getAll() {

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Record> criteriaQuery = qb.createQuery(Record.class);
        Root<Record> root = criteriaQuery.from(Record.class);
        CriteriaQuery<Record> all = criteriaQuery.select(root);
        TypedQuery<Record> allQuery = em.createQuery(all);

        return allQuery.getResultList();
    }
}