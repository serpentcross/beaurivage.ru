package ru.beaurivage.msystem.logic.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "service")
public class Service implements Serializable {

    public Service() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "name")
    @Getter @Setter
    private String name;

    @Column(name = "price")
    @Getter @Setter
    private int price;

    @Column(name = "description")
    @Getter @Setter
    private String description;
}
