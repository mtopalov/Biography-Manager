package com.scalefocus.cvmanager.model.biography.identification;

import com.scalefocus.cvmanager.model.biography.Biography;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "identification")
public class Identification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Person name must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private PersonName personName;

    @NotNull(message = "The contact information must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private ContactInfo contactInfo;

    @NotNull(message = "The demographics must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private Demographics demographics;

    @NotNull(message = "The photo must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private Photo photo;

    @OneToOne
    private Biography biography;

    public Identification() {
    }

    public Identification(Long id, PersonName personName, ContactInfo contactInfo, Demographics demographics, Photo photo, Biography biography) {
        this.id = id;
        this.personName = personName;
        this.contactInfo = contactInfo;
        this.demographics = demographics;
        this.photo = photo;
        this.biography = biography;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Demographics getDemographics() {
        return demographics;
    }

    public void setDemographics(Demographics demographics) {
        this.demographics = demographics;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }
}
