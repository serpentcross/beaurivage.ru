package ru.beaurivage.systems.management.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Column(name = "first_name")
    @Getter @Setter
    private String firstName;

    @Column(name = "middle_name")
    @Getter @Setter
    private String middleName;

    @Column(name = "last_name")
    @Getter @Setter
    private String lastName;

    @Column(name = "phone")
    @Getter @Setter
    private String phone;

    @Column(name = "email")
    @Getter @Setter
    private String email;

}
