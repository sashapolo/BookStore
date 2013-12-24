/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
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
    private String name;
    @NotNull
    private String secondName;
    @NotEmpty
    private String lastName;

    protected Credentials() { }
    public Credentials(final String name, final String lastName) {
        this.name = name;
        this.lastName = lastName;
        this.secondName = "";
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
    
    @Override
    public String toString() {
        return "{name=" + name + ", secondName=" + secondName + ", lastName=" + lastName + "}";
    }
}
