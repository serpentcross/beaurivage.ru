package ru.beaurivage.msystem.logic.dao;

import ru.beaurivage.msystem.logic.entities.Patient;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

@Stateless
public class PatientDAOImpl implements PatientDAO {

    @PersistenceContext(unitName = "beaurivage")
    private EntityManager em;

    @Override
    public void save(Patient patient) {
        if (patient != null) {
            if (patient.getId() == null) {
                insert(patient);
            } else {
                update(patient);
            }
        }
    }

    @Override
    public void insert(Patient patient) {
        em.persist(patient);
    }

    @Override
    public void update(Patient patient) {
        em.merge(patient);
    }

    @Override
    public List<Patient> getAll() {

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Patient> criteriaQuery = qb.createQuery(Patient.class);
        Root<Patient> root = criteriaQuery.from(Patient.class);
        CriteriaQuery<Patient> all = criteriaQuery.select(root);
        TypedQuery<Patient> allQuery = em.createQuery(all);

        return allQuery.getResultList();
    }
}