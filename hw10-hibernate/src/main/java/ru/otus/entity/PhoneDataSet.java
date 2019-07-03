package ru.otus.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phone")
public class PhoneDataSet  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    public PhoneDataSet(){
    }

    public PhoneDataSet(String number) {
        this.number= number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneDataSet that = (PhoneDataSet) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}

