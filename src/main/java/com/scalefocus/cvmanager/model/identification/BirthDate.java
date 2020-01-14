package com.scalefocus.cvmanager.model.identification;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "birth_date")
public class BirthDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Max(value = 2002, message = "Must be adult to create biography! At least 18 years old!")
    @NotNull(message = "Birth date year must not be null!")
    private Integer year;

    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    @NotNull(message = "Birth date month must not be null!")
    private Integer month;

    @Min(value = 1, message = "Day must be between 1 and 31")
    @Max(value = 31, message = "Day must be between 1 and 31")
    @NotNull(message = "Birth date day must not be null!")
    private Integer day;

    @OneToOne
    private Demographics demographics;

    public BirthDate() {
    }

    public BirthDate(Long id, Integer year, Integer month, Integer day, Demographics demographics) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.demographics = demographics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Demographics getDemographics() {
        return demographics;
    }

    public void setDemographics(Demographics demographics) {
        this.demographics = demographics;
    }
}
