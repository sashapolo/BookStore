/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.User;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexander
 */
@Stateless
@LocalBean
public class UserEjb extends DataEjb<User>{
    public List<User> findAll() {
        final TypedQuery<User> query = em.createNamedQuery(User.FIND_ALL, User.class);
        return query.getResultList();
    }
    
    public User findByLogin(final String login) {
        if (login == null) return null;
        
        final TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN, User.class);
        query.setParameter("login", login);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<User> fuzzyFind(@NotNull final String search) {
        final TypedQuery<User> query = em.createNamedQuery(User.FUZZY_FIND, User.class);
        query.setParameter("login", "%" + search.toUpperCase() + "%");
        return query.getResultList();
    }

    @Override
    protected Class<User> getGenericClass() {
        return User.class;
    }
}
