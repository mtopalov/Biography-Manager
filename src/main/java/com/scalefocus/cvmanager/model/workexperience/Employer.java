package com.scalefocus.cvmanager.model.workexperience;

import com.scalefocus.cvmanager.model.Address;

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
@Table(name = "employer")
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name of the employer must not be empty!")
    private String name;

    @NotNull(message = "The address of the employer must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToOne
    private WorkExperience workExperience;

    public Employer() {
    }

    public Employer(Long id, String name, Address address, WorkExperience workExperience) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.workExperience = workExperience;
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

    public WorkExperience getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(WorkExperience workExperience) {
        this.workExperience = workExperience;
    }
}
