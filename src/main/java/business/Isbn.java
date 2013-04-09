/**
 * 
 */
package business;

/**
 * @author alexander
 * 
 */
public abstract class Isbn {
    protected String isbn_ = "";

    protected abstract boolean isValid(String isbn);

    public abstract Isbn13 toIsbn13();

    @Override
    public String toString() {
        return isbn_;
    }
}
