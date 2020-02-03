package com.scalefocus.cvmanager.model.biography.skill;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "linguistic_skills")
public class LinguisticSkills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> motherLanguages;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ForeignLanguage> foreignLanguages;

    @OneToOne
    private Skills skills;

    public LinguisticSkills() {
    }

    public LinguisticSkills(Long id, List<String> motherLanguages, List<ForeignLanguage> foreignLanguages, Skills skills) {
        this.id = id;
        this.motherLanguages = motherLanguages;
        this.foreignLanguages = foreignLanguages;
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getMotherLanguages() {
        return motherLanguages;
    }

    public void setMotherLanguages(List<String> motherLanguages) {
        this.motherLanguages = motherLanguages;
    }

    public List<ForeignLanguage> getForeignLanguages() {
        return foreignLanguages;
    }

    public void setForeignLanguages(List<ForeignLanguage> foreignLanguages) {
        this.foreignLanguages = foreignLanguages;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }
}
