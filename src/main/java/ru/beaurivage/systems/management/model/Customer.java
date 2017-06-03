package ru.beaurivage.systems.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @JsonIgnoreProperties("id")
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

//    @Column(name = "email")
//    @Getter @Setter
//    private String email;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!id.equals(customer.id)) return false;
        if (!firstName.equals(customer.firstName)) return false;
        if (!middleName.equals(customer.middleName)) return false;
        if (!lastName.equals(customer.lastName)) return false;
        return phone.equals(customer.phone);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + middleName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + phone.hashCode();
        return result;
    }
}
