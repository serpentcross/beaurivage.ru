package ru.beaurivage.msystem.logic.entities;

import lombok.Data;

import ru.beaurivage.msystem.logic.enums.CabinetType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;

import java.time.LocalDate;

@Data
@Entity
public class Record implements Serializable {

    public Record() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_from")
    private String time_from;

    @Column(name = "time_to")
    private String time_to;

    @Column(name = "recdate")
    private LocalDate recDate;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name= "patient")
    private Patient patient;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name= "service")
    private Service service;

    @Column(name = "cabinet")
    private CabinetType cabinetType;

}