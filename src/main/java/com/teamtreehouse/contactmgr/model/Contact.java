package com.teamtreehouse.contactmgr.model;

import javax.persistence.*;

/**
 * Created by kpfromer on 3/7/17.
 */

//By Default the table name for the data is the class name
//We can change this by changing @Entity to @Entity(name="WHATEVER")
@Entity
public class Contact {
    //@Id is for PRIMARY KEY for table
    //@GeneratedValue allows for hibernate to auto generate id, strategy is used for the way it does so
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private Long phone;


    //Default constructor for JPA
    public Contact(){}

    public Contact(ContactBuilder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phone = builder.phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public static class ContactBuilder{
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private Long phone;

        public ContactBuilder(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public ContactBuilder withEmail(String email){
            this.email = email;
            return this;
        }

        public ContactBuilder withPhone(long phone){
            this.phone = phone;
            return this;
        }

        public Contact build(){
            return new Contact(this);
        }

    }
}
