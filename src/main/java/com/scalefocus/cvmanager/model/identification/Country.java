package com.scalefocus.cvmanager.model.identification;

import com.scalefocus.cvmanager.model.Address;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The code of the country must not be blank!")
    @Pattern(regexp = "\\w{2,3}",message = "The country code must be 2 or 3 letters")
    private String code;

    @NotBlank(message = "Country name must not be blank!")
    @Pattern(regexp = "\\w{3,25}", message = "The country name must be at least 3 letters and not more than 25 letters!")
    private String label;

    @OneToOne
    private Address address;

    public Country() {
    }

    public Country(Long id, String code, String label, Address address) {
        this.id = id;
        this.code = code;
        this.label = label;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
