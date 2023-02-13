package com.Example.EndAssignment.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Member implements Serializable {
    private int id;

    public Member(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName= firstName;
        this.lastName=lastName;
        this.birthday=dateOfBirth;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    private String firstName;
    private String lastName;
    private LocalDate birthday;

    public Member(int id, String firstName, String lastName, LocalDate birthday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }
}
