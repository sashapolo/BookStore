/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author alexander
 */
@Embeddable
public class Credentials implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotEmpty
    private String name = "";
    @NotNull
    private String secondName = "";
    @NotEmpty
    private String lastName = "";

    public Credentials() {}
    
    public Credentials(final String name, final String lastName) {
        this.name = name;
        this.lastName = lastName;
    }
    
    public Credentials(final String name, final String secondName, final String lastName) {
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public Credentials setName(final String name) {
        return new Credentials(name, secondName, lastName);
    }
    
    public Credentials setSecondName(final String secondName) {
        return new Credentials(name, secondName, lastName);
    }
    
    public Credentials setLastName(final String lastName) {
        return new Credentials(name, secondName, lastName);
    }
    
    @Override
    public String toString() {
        return "{name=" + name + ", secondName=" + secondName + ", lastName=" + lastName + "}";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.secondName);
        hash = 71 * hash + Objects.hashCode(this.lastName);
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
        final Credentials other = (Credentials) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.secondName, other.secondName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        return true;
    }
    
    
}
