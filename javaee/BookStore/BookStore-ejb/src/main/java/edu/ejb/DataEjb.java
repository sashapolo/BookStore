/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexander
 * @param <T>
 */
@Stateless
@LocalBean
public abstract class DataEjb<T> {
    @PersistenceContext
    protected EntityManager em;
    
    protected abstract Class<T> getGenericClass();
    
    public T findById(@NotNull final Long id) {
        return em.find(getGenericClass(), id);
    }
    
    public @NotNull T create(@NotNull final T entity) {
        em.persist(entity);
        return entity;
    }
    
    public @NotNull T update(@NotNull final T entity) {
        return em.merge(entity);
    }
    
    public void delete(@NotNull final T entity) {
        em.remove(em.merge(entity));
    }
    
}
