/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.Publisher;
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
public class PublisherEjb {
    @PersistenceContext
    private EntityManager em;
    
    public List<Publisher> findAll() {
        final TypedQuery<Publisher> query = em.createNamedQuery(Publisher.FIND_ALL, Publisher.class);
        return query.getResultList();
    }
    
    public @NotNull Publisher create(@NotNull final Publisher pub) {
        em.persist(pub);
        return pub;
    }
}
