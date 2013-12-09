/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author alexander
 */
@Entity
@Table(name = "BusinessUser")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String login;
    private int password;
    @Embedded
    @NotNull @Valid
    private Credentials credentials;

    protected User() {}
    
    public User(final String login, final int password, final Credentials credentials) {
        this.login = login;
        this.password = password;
        this.credentials = credentials;
    }
    
    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public int getPassword() {
        return password;
    }

    public String getName() {
        return credentials.getName();
    }

    public String getSecondName() {
        return credentials.getSecondName();
    }

    public String getLastName() {
        return credentials.getLastName();
    }
}
