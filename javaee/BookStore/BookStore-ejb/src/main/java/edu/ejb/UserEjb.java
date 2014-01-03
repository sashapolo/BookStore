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
    
    public List<User> findByLogin(@NotNull final String login) {
        final TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN, User.class);
        query.setParameter("login", "%" + login.toUpperCase() + "%");
        return query.getResultList();
    }

    @Override
    protected Class<User> getGenericClass() {
        return User.class;
    }
}
