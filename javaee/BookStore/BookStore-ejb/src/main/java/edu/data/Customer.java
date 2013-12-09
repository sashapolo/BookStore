/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexander
 */
@Entity
public class Customer extends User {
    private static final long serialVersionUID = 1L;
    private int discount = 0;
    @OneToMany
    @NotNull @Valid
    private List<BookOrder> orders = new LinkedList<>();

    public List<BookOrder> getOrders() {
        return Collections.unmodifiableList(orders);
    }
    
    public void addOrder(final BookOrder order) {
        orders.add(order);
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(final int discount) {
        this.discount = discount;
    }
}
