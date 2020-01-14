package com.scalefocus.cvmanager.model;

import com.scalefocus.cvmanager.model.education.Education;
import com.scalefocus.cvmanager.model.workexperience.WorkExperience;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "period")
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The starting date of the period must not be blank!")
    @OneToOne(cascade = CascadeType.ALL)
    private Date start;

    @OneToOne(cascade = CascadeType.ALL)
    private Date end;

    private boolean current;

    @OneToOne
    private WorkExperience workExperience;

    @OneToOne
    private Education education;

    public Period() {
    }

    public Period(Long id, Date start, Date end, boolean current, WorkExperience workExperience, Education education) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.current = current;
        this.workExperience = workExperience;
        this.education = education;
        if (end == null && !current) {
            throw new ConstraintViolationException("Either ending date must not be blank, or the period must be marked as current", null);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public WorkExperience getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(WorkExperience workExperience) {
        this.workExperience = workExperience;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }
}
