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

    public abstract Isbn toIsbn13();

    @Override
    public String toString() {
        return isbn_;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Isbn)) return false;

        final Isbn isbn = (Isbn) o;
        return isbn_.equals(isbn.isbn_);
    }

    @Override
    public int hashCode() {
        return isbn_.hashCode();
    }
}
