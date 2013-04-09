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

    public Discount(final int value) {
        if (value > 100 || value < 0) {
            throw new IllegalArgumentException("Illegal discount settings");
        }
        value_ = value;
    }

    public BigDecimal getPercents() {
        return BigDecimal.valueOf(value_).divide(BigDecimal.valueOf(100), 2,
                BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getInverted() {
        return BigDecimal.valueOf(100 - value_).divide(BigDecimal.valueOf(100), 2,
                BigDecimal.ROUND_HALF_UP);
    }
}
