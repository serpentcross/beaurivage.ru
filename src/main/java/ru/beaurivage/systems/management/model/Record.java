package ru.beaurivage.systems.management.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @OneToMany
    @Getter @Setter
    Set<Customer> customer;

    @Column(name = "day")
    @Getter @Setter
    private String day;

    @Column(name = "time_from")
    @Getter @Setter
    private String time_from;

    @Column(name = "time_to")
    @Getter @Setter
    private String time_to;

}
