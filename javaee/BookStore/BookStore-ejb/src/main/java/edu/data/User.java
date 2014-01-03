/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author alexander
 */
@Entity
@Table(name = "BusinessUser")
@NamedQueries({
    @NamedQuery(name = User.FUZZY_FIND, query = "SELECT u FROM User u WHERE UPPER(u.login) LIKE :login"),
    @NamedQuery(name = User.FIND_ALL, query = "SELECT u FROM User u"),
    @NamedQuery(name = User.FIND_BY_LOGIN, query = "SELECT u FROM User u WHERE u.login = :login")
})
public class User implements Serializable {
    public static final String FIND_BY_LOGIN = "User.findByLogin";
    public static final String FUZZY_FIND = "User.fuzzyFind";
    public static final String FIND_ALL = "User.findAll";
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty @Column(unique = true)
    private String login;
    private int password;
    @Embedded
    @NotNull @Valid
    private Credentials credentials;
    @Email @NotNull @Column(unique = true)
    private String email;
    @OneToMany
    @NotNull @Valid
    private List<BookOrder> orders;
    private Discount discount;
    private boolean isadmin;

    protected User() {}
    
    private User(final Builder builder) {
        this.login = builder.login;
        this.password = builder.password;
        this.credentials = builder.credentials;
        this.email = builder.email;
        this.orders = builder.orders;
        this.discount = builder.discount;
        this.isadmin = builder.isadmin;
    }
    
    public static class Builder {
        private String login;
        private int password;
        private Credentials credentials = new Credentials();
        private String email;
        private List<BookOrder> orders = new LinkedList<>();
        private Discount discount = new Discount();
        private boolean isadmin = false;
        
        public Builder login(final String login) {
            this.login = login;
            return this;
        }
        
        public Builder email(final String email) {
            this.email = email;
            return this;
        }
        
        public Builder password(final int password) {
            this.password = password;
            return this;
        }
        
        public Builder credentials(final Credentials credentials) {
            this.credentials = credentials;
            return this;
        }
        
        public Builder orders(final List<BookOrder> orders) {
            this.orders = new LinkedList<>(orders);
            return this;
        }
        
        public Builder discount(final Discount discount) {
            this.discount = discount;
            return this;
        }
        
        public Builder admin(final boolean admin) {
            this.isadmin = admin;
            return this;
        }
        
        public User build() {
            return new User(this);
        }
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
    
    public List<BookOrder> getOrders() {
        return Collections.unmodifiableList(orders);
    }
    
    public boolean isAdmin() {
        return isadmin;
    }
    
    public Discount getDiscount() {
        return discount;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(final int password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
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
    public String toString() {
        return "{id=" + id + ", login=" + login + ", password=" + password +
               ", credentials=" + credentials + ", email=" + email + "}";
    }
}
