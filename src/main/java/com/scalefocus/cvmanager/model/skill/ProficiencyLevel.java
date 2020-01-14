package com.scalefocus.cvmanager.model.skill;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "proficiency_level")
public class ProficiencyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String listening;

    private String reading;

    private String spokenInteraction;

    private String spokenProduction;

    private String writing;

    @OneToOne
    private ForeignLanguage foreignLanguage;

    public ProficiencyLevel() {
    }

    public ProficiencyLevel(Long id, String listening, String reading, String spokenInteraction, String spokenProduction, String writing, ForeignLanguage foreignLanguage) {
        this.id = id;
        this.listening = listening;
        this.reading = reading;
        this.spokenInteraction = spokenInteraction;
        this.spokenProduction = spokenProduction;
        this.writing = writing;
        this.foreignLanguage = foreignLanguage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListening() {
        return listening;
    }

    public void setListening(String listening) {
        this.listening = listening;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getSpokenInteraction() {
        return spokenInteraction;
    }

    public void setSpokenInteraction(String spokenInteraction) {
        this.spokenInteraction = spokenInteraction;
    }

    public String getSpokenProduction() {
        return spokenProduction;
    }

    public void setSpokenProduction(String spokenProduction) {
        this.spokenProduction = spokenProduction;
    }

    public String getWriting() {
        return writing;
    }

    public void setWriting(String writing) {
        this.writing = writing;
    }

    public ForeignLanguage getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(ForeignLanguage foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }
}
