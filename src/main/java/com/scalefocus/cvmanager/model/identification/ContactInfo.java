package com.scalefocus.cvmanager.model.identification;

import com.scalefocus.cvmanager.model.Address;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Email must not be empty!")
    @Pattern(regexp = "[a-zA-Z0-9]+[.\\-_]*[a-zA-Z0-9]+[.\\-_]*@(abv\\.bg|yahoo\\.com|gmail\\.com)", message = "Email must be in format: ...@emailprovider. Working only with yahoo.com, gmail.com and abv.bg")
    private String email;

    @NotEmpty(message = "Telephones must not be empty!")
    @ElementCollection
    private List<
            @NotBlank(message = "Telephone must not be empty!")
            @Pattern(regexp = "\\+\\d{1,3}[ \\-]?\\d{3}[ \\-]?\\d{6}", message = "Telephone must be in format: +(country code: 1-3 digits)(number: 9 digits). Example: +359895423070")
            String> telephones;

    @NotNull(message = "Address must not be empty!")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToOne
    private Identification identification;

    public ContactInfo() {
    }

    public ContactInfo(Long id, String email, List<String> telephones, Address address, Identification identification) {
        this.id = id;
        this.email = email;
        this.telephones = telephones;
        this.address = address;
        this.identification = identification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<String> telephones) {
        this.telephones = telephones;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }
}
