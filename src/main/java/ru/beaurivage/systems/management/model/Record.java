package ru.beaurivage.systems.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import java.io.Serializable;

@Entity
@Table(name = "record")
public class Record implements Serializable {

    public Record() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	@JsonIgnoreProperties("id")
    @Getter @Setter
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="customer")
    @Getter @Setter
    private Customer customer;

    @Column(name = "day")
    @Getter @Setter
    private String day;

    @Column(name = "time_from")
    @Getter @Setter
    private String time_from;

    @Column(name = "time_to")
    @Getter @Setter
    private String time_to;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Record record = (Record) o;

		if (!id.equals(record.id)) return false;
		if (!customer.equals(record.customer)) return false;
		if (day != null ? !day.equals(record.day) : record.day != null) return false;
		if (time_from != null ? !time_from.equals(record.time_from) : record.time_from != null) return false;
		return time_to != null ? time_to.equals(record.time_to) : record.time_to == null;
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + customer.hashCode();
		result = 31 * result + (day != null ? day.hashCode() : 0);
		result = 31 * result + (time_from != null ? time_from.hashCode() : 0);
		result = 31 * result + (time_to != null ? time_to.hashCode() : 0);
		return result;
	}
}
