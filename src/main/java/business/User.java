/**
 * 
 */
package business;

import java.util.List;

/**
 * @author alexander
 * 
 */
public class User {
    protected final long id_;
    protected String login_;
    protected int passwordHash_;
    protected String name_;
    protected String secondName_;
    protected String email_;

    public User(long id, String login, String password, String name, String secondName, String email) {
        assert (login != null);
        assert (password != null);
        assert (name != null);

        id_ = id;
        login_ = login;
        passwordHash_ = password.hashCode();
        name_ = name;
        secondName_ = secondName;
        email_ = email;
    }

    public long getId() {
        return id_;
    }

    public String getLogin() {
        return login_;
    }

    public String getName() {
        return name_;
    }

    public String getSecondName() {
        return secondName_;
    }

    public String getEmail() {
        return email_;
    }
    
    public List<Book> findBook(String search) {
        return BookCatalogue.INSTANCE.findBook(search);
    }
}
