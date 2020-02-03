package com.scalefocus.cvmanager.model.user;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

/**
 * @author Mariyan Topalov
 */
@Entity
@Table(name = "user_entities")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;

    public UserEntity() {
        authorities = Collections.singletonList("USER");
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
        this.authorities = Collections.singletonList("USER");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
