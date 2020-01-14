package com.scalefocus.cvmanager.model.skill;

import javax.persistence.*;
import java.util.List;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "foreign_language")
public class ForeignLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private ProficiencyLevel proficiencyLevel;

    @ElementCollection
    private List<String> certificates;

    @ManyToOne
    @JoinColumn
    private LinguisticSkills linguisticSkills;

    public ForeignLanguage() {
    }

    public ForeignLanguage(Long id, String description, ProficiencyLevel proficiencyLevel, List<String> certificates, LinguisticSkills linguisticSkills) {
        this.id = id;
        this.description = description;
        this.proficiencyLevel = proficiencyLevel;
        this.certificates = certificates;
        this.linguisticSkills = linguisticSkills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProficiencyLevel getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public List<String> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<String> certificates) {
        this.certificates = certificates;
    }

    public LinguisticSkills getLinguisticSkills() {
        return linguisticSkills;
    }

    public void setLinguisticSkills(LinguisticSkills linguisticSkills) {
        this.linguisticSkills = linguisticSkills;
    }
}
