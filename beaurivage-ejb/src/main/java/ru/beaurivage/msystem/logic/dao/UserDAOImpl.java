package ru.beaurivage.msystem.logic.dao;

import ru.beaurivage.msystem.logic.entities.User;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(unitName = "beaurivage")
    private EntityManager em;

    @Override
    public boolean getAuthState(String login, String notEncryptedPassword) {

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = qb.createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(qb.equal(root.get("username"), login));
        predicates.add(qb.equal(root.get("password"), encryptUserPassword(notEncryptedPassword)));

        criteriaQuery.where(qb.and(predicates.toArray(new Predicate[] {})));

        return !em.createQuery(criteriaQuery).getResultList().isEmpty();
    }

    private String encryptUserPassword(String notEncryptedPassword) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            byte[] result = messageDigest.digest(notEncryptedPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte arrayMember : result) {
                sb.append(Integer.toString((arrayMember & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}