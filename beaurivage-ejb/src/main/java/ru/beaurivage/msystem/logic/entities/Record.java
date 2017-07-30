package ru.beaurivage.msystem.logic.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "record")
public class Record implements Serializable {

    public Record() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "time_from")
    @Getter @Setter
    private String time_from;

    @Column(name = "time_to")
    @Getter @Setter
    private String time_to;

    @Column(name = "recdate")
    @Getter @Setter
    private LocalDate recDate;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="customer")
    @Getter @Setter
    private Customer customer;

}
