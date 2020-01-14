package com.scalefocus.cvmanager.model.identification;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "demographics")
public class Demographics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The birth date must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private BirthDate birthDate;

    @NotBlank(message = "The gender must not be blank!")
    @Pattern(regexp = "([mM]ale|[fF]emale)",message = "Gender must be male or female!")
    private String gender;

    @OneToOne
    private Identification identification;

    public Demographics() {
    }

    public Demographics(Long id, BirthDate birthDate, String gender, Identification identification) {
        this.id = id;
        this.birthDate = birthDate;
        this.gender = gender;
        this.identification = identification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BirthDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }
}
