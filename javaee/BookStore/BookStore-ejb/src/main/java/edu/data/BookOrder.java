/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author alexander
 */
@Entity
public class BookOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    @NotEmpty @Valid
    private List<BookOrderEntry> entries;
    @Transient
    @Min(0)
    private double price;
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull 
    private Calendar dateCreated;
    @NotNull @Valid
    private Discount discount;
    
    protected BookOrder() { }
    
    public BookOrder(final List<BookOrderEntry> entries, final Discount discount) {
        this.entries = entries;
        this.dateCreated = new GregorianCalendar();
        this.discount = discount;
        calculatePrice();
    }
    
    public BookOrder(final List<BookOrderEntry> entries) {
        this.entries = entries;
        this.dateCreated = new GregorianCalendar();
        this.discount = new Discount();
        calculatePrice();
    }
    
    public Long getId() {
        return id;
    }

    public List<BookOrderEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public double getPrice() {
        return discount.apply(price);
    }

    public Calendar getDateCreated() {
        return (Calendar) dateCreated.clone();
    }
    
    @PostLoad
    private void calculatePrice() {
        price = 0;
        for (final BookOrderEntry entry : entries) {
            if (entry == null) {
                throw new NullPointerException("One of the entries is null");
            }
            price += entry.getPrice();
        }
    }
}
