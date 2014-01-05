/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexander
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Author.FIND_ALL, query = "SELECT a FROM Author a"),
})
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String FIND_ALL = "Author.findAll";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Embedded @Column(unique = true)
    @NotNull @Valid
    private Credentials credentials = new Credentials();

    public Author() {}
    
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

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setName(final String name) {
        credentials = credentials.setName(name);
    }
    
    public void setLastName(final String name) {
        credentials = credentials.setLastName(name);
    }
    
    public void setSecondName(final String name) {
        credentials = credentials.setSecondName(name);
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.credentials);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (!Objects.equals(this.credentials, other.credentials)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + credentials.getName() + credentials.getLastName() + '}';
    }
    
    
}
