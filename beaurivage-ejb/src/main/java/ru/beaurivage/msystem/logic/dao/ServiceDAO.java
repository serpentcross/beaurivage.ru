package ru.beaurivage.msystem.logic.dao;

import ru.beaurivage.msystem.logic.entities.Service;

import javax.ejb.Local;

import java.util.List;

@Local
public interface ServiceDAO {

    void save(Service service);

    void insert(Service service);

    void update(Service service);

    List<Service> getAll();

}