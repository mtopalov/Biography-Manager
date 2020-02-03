package com.scalefocus.cvmanager.model.biography.education;

import com.scalefocus.cvmanager.model.biography.Address;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "organisation")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name of the organisation must not be blank!")
    private String name;

    @NotNull(message = "The address of the organisation must not be blank!")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToOne
    private Education education;

    public Organisation() {
    }

    public Organisation(Long id, String name, Address address, Education education) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.education = education;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }
}
