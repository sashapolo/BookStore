/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.Book;
import edu.data.Isbn;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexander
 */
@Stateless
@LocalBean
public class BookEjb extends DataEjb<Book> {
    @Resource
    private Validator v;
    
    public List<Book> findAll() {
        final TypedQuery<Book> query = 
                em.createNamedQuery(Book.FIND_ALL, Book.class);
        return query.getResultList();
    }
    
    public List<Book> findByIsbn(@NotNull final Isbn isbn) {
        final TypedQuery<Book> query = 
                em.createNamedQuery(Book.FIND_BY_ISBN, Book.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();
    }
    
    public List<Book> fuzzyFind(@NotNull final String search) {
        final Isbn isbn = new Isbn(search);
        if (v.validate(isbn).isEmpty()) {
            return findByIsbn(isbn);
        }
        final TypedQuery<Book> query = 
                em.createNamedQuery(Book.FUZZY_FIND, Book.class);
        query.setParameter("str", "%" + search.toUpperCase() + "%");
        return query.getResultList();
    }

    @Override
    protected Class<Book> getGenericClass() {
        return Book.class;
    }
}
