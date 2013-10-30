/**
 * 
 */
package edu.business;

/**
 * @author alexander
 * 
 */
public final class Discount {
    private final int value;

    public Discount(final int value) {
        if (value > 100 || value < 0) {
            throw new IllegalArgumentException("Illegal discount settings");
        }
        this.value = value;
    }

    public double doubleValue() {
        return value / 100.0;
    }

    public double invertedValue() {
        return (100 - value) / 100.0;
    }

    public int integerValue() {
        return value;
    }
}
