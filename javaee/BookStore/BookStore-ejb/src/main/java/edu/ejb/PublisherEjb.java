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
import javax.persistence.TypedQuery;

/**
 *
 * @author alexander
 */
@Stateless
@LocalBean
public class PublisherEjb extends DataEjb<Publisher> {
   
    public List<Publisher> findAll() {
        final TypedQuery<Publisher> query = 
                em.createNamedQuery(Publisher.FIND_ALL, Publisher.class);
        return query.getResultList();
    }
    
    public List<Publisher> findByName(final String name) {
        final TypedQuery<Publisher> query = 
                em.createNamedQuery(Publisher.FIND_BY_NAME, Publisher.class);
        query.setParameter("name", "%" + name.toUpperCase() + "%");
        return query.getResultList();
    }

    @Override
    protected Class<Publisher> getGenericClass() {
        return Publisher.class;
    }
}
