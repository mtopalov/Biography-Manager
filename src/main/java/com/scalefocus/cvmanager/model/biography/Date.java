package com.scalefocus.cvmanager.model.biography;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "date")
public class Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1960, message = "The year must not be before 1960!")
    @Max(value = 2020, message = "The year must be in past!")
    private Integer year;

    @Min(value = 1, message = "Month must be between 1 and 12!")
    @Max(value = 12, message = "Month must be between 1 and 12!")
    private Integer month;

    @OneToOne
    private Period period;

    public Date() {
    }

    public Date(Long id, Integer year, Integer month, Period period) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.period = period;
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

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
