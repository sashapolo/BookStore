/**
 * 
 */
package business;

/**
 * @author alexander
 * 
 */
public final class Discount {
    private final int value;

    public Discount(int value) {
        if (value > 100 || value < 0) {
            throw new IllegalArgumentException("Illegal discount settings");
        }
        this.value = value;
    }

    public double getValue() {
        return (double) value / 100;
    }

    public double getInvertedValue() {
        return (double) (100 - value) / 100;
    }

    public int integerValue() {
        return value;
    }
}
