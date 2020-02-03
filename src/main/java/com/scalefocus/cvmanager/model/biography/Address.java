package com.scalefocus.cvmanager.model.biography;

import com.scalefocus.cvmanager.model.biography.education.Organisation;
import com.scalefocus.cvmanager.model.biography.identification.ContactInfo;
import com.scalefocus.cvmanager.model.biography.identification.Country;
import com.scalefocus.cvmanager.model.biography.workexperience.Employer;

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
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The street of the address must not be blank!")
    @Pattern(regexp = "[A-Z][a-z]{2,25}\\s\\d{1,5}\\s(Street|Boulevard|Avenue)", message = "The street must be in format: (name(the name of the place), number(between 1 and 5 digits), signature(Street, Boulevard, Avenue). Example: Borba 20 Street")
    private String street;

    @NotBlank(message = "The postal code must not be blank!")
    @Pattern(regexp = "\\d{1,10}", message = "Postal code have to be between 1 and 10 digits!")
    private String postalCode;

    @NotBlank(message = "The municipality must not be blank!")
    @Pattern(regexp = "\\w{3,25}", message = "Municipality have to be at least 3 letters and not more than 25 letters!")
    private String municipality;

    @NotNull(message = "Country must not be blank!")
    @OneToOne(cascade = CascadeType.ALL)
    private Country country;

    @OneToOne
    private ContactInfo contactInfo;

    @OneToOne
    private Employer employer;

    @OneToOne
    private Organisation organisation;

    public Address() {
    }

    public Address(String street, String postalCode, String municipality, Country country) {
        this.street = street;
        this.postalCode = postalCode;
        this.municipality = municipality;
        this.country = country;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
}
