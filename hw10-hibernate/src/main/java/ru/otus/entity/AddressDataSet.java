package ru.otus.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "address")
public class AddressDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    public AddressDataSet(String street) {
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDataSet that = (AddressDataSet) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street);
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}
