package com.scalefocus.cvmanager.model;

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
@Table(name = "headline")
public class Headline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The type of the headline must not be empty!")
    private String type;

    @NotBlank(message = "The description of the headline must not be empty!")
    private String description;

    @OneToOne
    private Biography biography;

    public Headline() {
    }

    public Headline(Long id, String type, String description, Biography biography) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.biography = biography;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }
}
