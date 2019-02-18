package ru.beaurivage.msystem.logic.dao;

import ru.beaurivage.msystem.logic.entities.Patient;

import javax.ejb.Local;

import java.util.List;

@Local
public interface PatientDAO {

    void save(Patient patient);

    void insert(Patient patient);

    void update(Patient patient);

    List<Patient> getAll();
}