/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.Book;
import edu.data.Stock;
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
public class StockEjb extends DataEjb<Stock> {

    @Override
    protected Class<Stock> getGenericClass() {
        return Stock.class;
    }
    
    public Stock findByBook(@NotNull final Book book) {
        final TypedQuery<Stock> query = 
                em.createNamedQuery(Stock.FIND_BY_BOOK_ID, Stock.class);
        query.setParameter("id", book.getId());
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
