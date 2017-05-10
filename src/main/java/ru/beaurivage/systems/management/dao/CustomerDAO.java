package ru.beaurivage.systems.management.dao;

import ru.beaurivage.systems.management.model.Customer;

public interface CustomerDAO {

	void save(Customer customer);

	void insert(Customer customer);

	void update(Customer customer);
}
