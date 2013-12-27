/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.Credentials;
import edu.data.User;
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
public class UserEjb {
    @PersistenceContext
    private EntityManager em;
    
    public List<User> findByLogin(@NotNull final String login) {
        final TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN, User.class);
        query.setParameter("login", login);
        return query.getResultList();
    }
    
    public @NotNull User create(@NotNull final User user) {
        em.persist(user);
        return user;
    }
}
