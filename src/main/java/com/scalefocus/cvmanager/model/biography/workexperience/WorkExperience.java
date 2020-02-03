package com.scalefocus.cvmanager.model.biography.workexperience;

import com.scalefocus.cvmanager.model.biography.Period;
import com.scalefocus.cvmanager.model.biography.Biography;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "work_experience")
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;

    private String activities;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    private Period period;

    @OneToOne(cascade = CascadeType.ALL)
    private Employer employer;

    @ManyToOne
    @JoinColumn
    private Biography biography;

    public WorkExperience() {
    }

    public WorkExperience(Long id, Period period, String position, String activities, Employer employer, Biography biography) {
        this.id = id;
        this.period = period;
        this.position = position;
        this.activities = activities;
        this.employer = employer;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }
}
