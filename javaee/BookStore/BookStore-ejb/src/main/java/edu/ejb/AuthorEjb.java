/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.Author;
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
public class AuthorEjb {
    @PersistenceContext
    private EntityManager em;
    
    public List<Author> findAll() {
        final TypedQuery<Author> query = em.createNamedQuery(Author.FIND_ALL, Author.class);
        return query.getResultList();
    }
    
    public Author findById(@NotNull final Long id) {
        return em.find(Author.class, id);
    }
    
    public List<Author> findByCredentials(@NotNull final String name, 
            @NotNull final String secondName) {
        //TODO
        return null;
    }
    
    public @NotNull Author create(@NotNull final Author author) {
        em.persist(author);
        return author;
    }
    
    public @NotNull Author update(@NotNull final Author author) {
        return em.merge(author);
    }
    
    public void delete(@NotNull final Author author) {
        em.remove(em.merge(author));
    }
}
