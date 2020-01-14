package com.scalefocus.cvmanager.model.education;

import com.scalefocus.cvmanager.model.Biography;
import com.scalefocus.cvmanager.model.Period;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The period of the education must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private Period period;

    @NotBlank(message = "The title of the education must not be empty!")
    private String title;

    @NotNull(message = "The organisation of the education must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private Organisation organisation;

    @ManyToOne
    @JoinColumn
    private Biography biography;

    public Education() {
    }

    public Education(Long id, Period period, String title, Organisation organisation, Biography biography) {
        this.id = id;
        this.period = period;
        this.title = title;
        this.organisation = organisation;
        this.biography = biography;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }
}
