/**
 * 
 */
package business;

/**
 * @author alexander
 *
 */
public abstract class Isbn {
	protected String isbn_;
	
	public Isbn(final String isbn) {
		assert(isbn != null);
		isbn_ = isbn;
	}
	
	abstract protected boolean isValid(final String isbn);
	
	public String getIsbn() {
		return isbn_;
	}
	
	@Override
	public String toString() {
		return isbn_;
	}
}
