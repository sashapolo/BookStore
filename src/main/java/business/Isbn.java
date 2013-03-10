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

	protected Isbn() {
		isbn_ = "";
	}

	abstract protected boolean isValid(String isbn);

	@Override
	public String toString() {
		return isbn_;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn_ == null) ? 0 : isbn_.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Isbn other = (Isbn) obj;
		if (isbn_ == null) {
			if (other.isbn_ != null)
				return false;
		} else if (!isbn_.equals(other.isbn_))
			return false;
		return true;
	}
}
