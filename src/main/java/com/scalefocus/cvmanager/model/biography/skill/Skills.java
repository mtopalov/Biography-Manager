package com.scalefocus.cvmanager.model.biography.skill;

import com.scalefocus.cvmanager.model.biography.Biography;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "skills")
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private LinguisticSkills linguisticSkills;

    private String communication;

    private String organisational;

    private String computer;

    @ElementCollection
    private List<String> driving;

    private String other;

    @OneToOne
    private Biography biography;

    public Skills() {
    }

    public Skills(Long id, LinguisticSkills linguisticSkills, String communication, String organisational, String computer, List<String> driving, String other, Biography biography) {
        this.id = id;
        this.linguisticSkills = linguisticSkills;
        this.communication = communication;
        this.organisational = organisational;
        this.computer = computer;
        this.driving = driving;
        this.other = other;
        this.biography = biography;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LinguisticSkills getLinguisticSkills() {
        return linguisticSkills;
    }

    public void setLinguisticSkills(LinguisticSkills linguisticSkills) {
        this.linguisticSkills = linguisticSkills;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getOrganisational() {
        return organisational;
    }

    public void setOrganisational(String organisational) {
        this.organisational = organisational;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public List<String> getDriving() {
        return driving;
    }

    public void setDriving(List<String> driving) {
        this.driving = driving;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }
}
