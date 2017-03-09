package com.teamtreehouse.contactmgr.model;

import javax.persistence.*;

/**
 * Created by kpfromer on 3/8/17.
 */
@Entity(name = "PHONE_LIST")
@Table
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private long number;
    @Column
    private String name;

    public Phone(String name, long number) {
        this.name = name;
        this.number = number;
    }

    public Phone() {
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number=" + number +
                ", name='" + name + '\'' +
                '}';
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
