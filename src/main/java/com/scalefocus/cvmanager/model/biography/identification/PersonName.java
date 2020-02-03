package com.scalefocus.cvmanager.model.biography.identification;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "person_name")
public class PersonName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name must not be blank!")
    private String firstName;

    @NotBlank(message = "Last name must not be blank!")
    private String lastName;

    @OneToOne
    private Identification identification;

    public PersonName() {
    }

    public PersonName(Long id, String firstName, String lastName, Identification identification) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identification = identification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    @Override
    public String toString() {
        return firstName + lastName;
    }
}
