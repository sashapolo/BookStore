/**
 * 
 */
package business;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author alexander
 * 
 */
public final class Customer extends User {
    private static final long serialVersionUID = 7869877102786958293L;
    private transient Discount personalDiscount;
    private transient Cart currentCart = new Cart();

    public Customer(final int id, final String login, final int password,
                    final Credentials credentials) {
        super(id, login, password, credentials);
        personalDiscount = new Discount(0);
    }
    
    public Customer(final int id, final String login, final int password,
                    final Credentials credentials, final int discount) {
        super(id, login, password, credentials);
        personalDiscount = new Discount(discount);
    }

    public Discount getPersonalDiscount() {
        return personalDiscount;
    }
    
    public void addOrderEntry(final OrderEntry entry) {
        assert entry != null;
    	currentCart.put(entry);
    }
    
    public void deleteOrderEntry(final OrderEntry entry) {
        assert entry != null;
        currentCart.remove(entry.getBook());
    }
    
    public double getCartPrice() {
        return currentCart.getPrice(personalDiscount);
    }
    
    public Cart getCart() {
        return currentCart;
    }

    private void readObject(final ObjectInputStream inputStream)
            throws ClassNotFoundException, IOException {
        inputStream.defaultReadObject();

        personalDiscount = new Discount(inputStream.readInt());
        final int size = inputStream.readInt();
        currentCart = new Cart(size);
        for (int i = 0; i < size; ++i) {
            final int id = inputStream.readInt();
            final int amount = inputStream.readInt();
            final Book book = (Book) inputStream.readObject();
            currentCart.put(new OrderEntry(id, book, amount));
        }
    }

    private void writeObject(final ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();

        outputStream.writeInt(personalDiscount.integerValue());
        outputStream.writeInt(currentCart.size());
        for (final OrderEntry entry : currentCart) {
            outputStream.writeInt(entry.getId());
            outputStream.writeInt(entry.getAmount());
            outputStream.writeObject(entry.getBook());
        }
    }
}
