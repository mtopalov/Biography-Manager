package com.scalefocus.cvmanager.model.identification;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Photo type must not be blank!")
    private String type;

    @NotBlank(message = "The photo data must not be blank!")
    private String data;

    @OneToOne
    private Identification identification;

    public Photo() {
    }

    public Photo(Long id, String type, String data, Identification identification) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.identification = identification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }
}
