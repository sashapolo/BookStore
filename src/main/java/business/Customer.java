/**
 * 
 */
package business;

import java.util.HashMap;
import java.util.Map;


/**
 * @author alexander
 * 
 */
public class Customer extends User {
    protected Discount personalDiscount_;
    protected Map<Long, Order> orders_;

    public Customer(long id, String login, String password, String name, String secondName,
            String email, int discount) {
        super(id, login, password, name, secondName, email);
        personalDiscount_ = new Discount(discount);
        orders_ = new HashMap<>();
    }

    public Discount getPersonalDiscount() {
        return personalDiscount_;
    }
}
