/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.Book;
import edu.data.Isbn;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexander
 */
@Stateless
@LocalBean
public class BookEjb {
    @PersistenceContext(unitName = "BookStorePU")
    private EntityManager em;
    
    public List<Book> findAll() {
        final TypedQuery<Book> query = 
                em.createNamedQuery(Book.FIND_ALL, Book.class);
        return query.getResultList();
    }
    
    public Book findByIsbn(@NotNull final Isbn isbn) {
        final TypedQuery<Book> query = 
                em.createNamedQuery(Book.FIND_BY_ISBN, Book.class);
        query.setParameter("isbn", isbn);
        return query.getSingleResult();
    }
    
    public List<Book> fuzzyFind(@NotNull final String search) {
        final TypedQuery<Book> query = 
                em.createNamedQuery(Book.FUZZY_FIND, Book.class);
        query.setParameter("str", "%" + search + "%");
        return query.getResultList();
    }
    
    public @NotNull Book createBook(@NotNull final Book book) {
        em.persist(book);
        return book;
    }
    
    public @NotNull Book updateBook(@NotNull final Book book) {
        return em.merge(book);
    }
    
    public void deleteBook(@NotNull final Book book) {
        em.remove(em.merge(book));
    }
}