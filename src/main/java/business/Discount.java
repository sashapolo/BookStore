/**
 * 
 */
package business;

import java.math.BigDecimal;


/**
 * @author alexander
 * 
 */
public class Discount {
    private final int value_;

    public Discount(int value) {
        if (value > 100 || value < 0) {
            throw new IllegalArgumentException("Illegal discount settings");
        }
        value_ = value;
    }

    public double getValue() {
        return (double) value_ / 100;
    }

    public double getInvertedValue() {
        return (double) (100 - value_) / 100;
    }

    public int integerValue() {
        return value_;
    }
}
