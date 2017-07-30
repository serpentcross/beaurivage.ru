package ru.beaurivage.msystem.logic.dao;

import ru.beaurivage.msystem.logic.entities.Customer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CustomerDAO {

    void save(Customer customer);

    void insert(Customer customer);

    void update(Customer customer);

    List<Customer> getAll();
}
