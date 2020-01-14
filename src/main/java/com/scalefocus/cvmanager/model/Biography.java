package com.scalefocus.cvmanager.model;

import com.scalefocus.cvmanager.model.education.Education;
import com.scalefocus.cvmanager.model.identification.Identification;
import com.scalefocus.cvmanager.model.skill.Skills;
import com.scalefocus.cvmanager.model.workexperience.WorkExperience;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "biography")
public class Biography {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @NotNull(message = "Identification must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private Identification identification;

    @NotNull(message = "Headline must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private Headline headline;

    @NotEmpty(message = "Work experience must not be empty!")
    @OneToMany(cascade = CascadeType.ALL)
    private List<WorkExperience> workExperiences;

    @NotEmpty(message = "Education must not be empty!")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Education> educations;

    @NotNull(message = "Skills must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private Skills skills;

    public Biography() {
    }

    public Biography(Long id, Identification identification, Headline headline, List<WorkExperience> workExperiences, List<Education> educations, Skills skills) {
        this.id = id;
        this.identification = identification;
        this.headline = headline;
        this.workExperiences = workExperiences;
        this.educations = educations;
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }
}
