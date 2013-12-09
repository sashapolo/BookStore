/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 * @author alexander
 */
@Embeddable
public class Discount implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Min(0) @Max(100)
    private int value = 0;

    public Discount() { }
    
    public Discount(final int value) {
        this.value = value;
    }
    
    public double apply(final double price) {
        return price * (100 - value) / 100;
    }

    public int getValue() {
        return value;
    }
}
