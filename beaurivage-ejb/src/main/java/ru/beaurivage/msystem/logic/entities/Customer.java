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
@Table(name = "customer")
public class Customer implements Serializable {

    public Customer() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "first_name")
    @Getter
    @Setter
    private String firstName;

    @Column(name = "middle_name")
    @Getter
    @Setter
    private String middleName;

    @Column(name = "last_name")
    @Getter
    @Setter
    private String lastName;

    @Column(name = "phone")
    @Getter
    @Setter
    private String phone;

    @Column(name = "email")
    @Getter
    @Setter
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!id.equals(customer.id)) return false;
        if (!firstName.equals(customer.firstName)) return false;
        if (middleName != null ? !middleName.equals(customer.middleName) : customer.middleName != null) return false;
        if (!lastName.equals(customer.lastName)) return false;
        if (!phone.equals(customer.phone)) return false;
        return email != null ? email.equals(customer.email) : customer.email == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + lastName.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName;
    }
}