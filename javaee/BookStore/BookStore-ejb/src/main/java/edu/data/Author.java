/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexander
 */
@Entity
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Embedded @Column(unique = true)
    @NotNull @Valid
    private Credentials credentials;

    protected Author() { }
    
    public Author(final Credentials credentials) {
        this.credentials = credentials;
    }
    
    public Long getId() {
        return id;
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
