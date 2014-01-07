/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.BookOrder;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author alexander
 */
@Stateless
@LocalBean
public class OrderEjb extends DataEjb<BookOrder> {

    @Override
    protected Class<BookOrder> getGenericClass() {
        return BookOrder.class;
    }
}
