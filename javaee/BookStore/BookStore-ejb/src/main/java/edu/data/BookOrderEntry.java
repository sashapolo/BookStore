/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexander
 */
@Entity
public class BookOrderEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Min(0)
    private int amount;
    @ManyToOne
    @NotNull @Valid
    private Book book;

    protected BookOrderEntry() { }
    
    public BookOrderEntry(final Book book, final int amount) {
        this.book = book;
        this.amount = amount;
    }
    
    public Long getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public Book getBook() {
        return book;
    }
    
    public double getPrice() {
        return book.getPrice() * amount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.book);
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
        final BookOrderEntry other = (BookOrderEntry) obj;
        if (!Objects.equals(this.book, other.book)) {
            return false;
        }
        return true;
    }
    
    
}
