/**
 * 
 */
package business;

/**
 * @author alexander
 * 
 */
public class User {
    protected final int id_;
    protected String login_;
    protected int passwordHash_;
    protected String name_;
    protected String email_;

    public User(final int id,
                final String login,
                final String password,
                final String name,
                final String email) {
        assert (login != null);
        assert (password != null);
        assert (name != null);

        id_ = id;
        login_ = login;
        passwordHash_ = password.hashCode();
        name_ = name;
        email_ = email;
    }

    public int getId() {
        return id_;
    }

    public String getLogin() {
        return login_;
    }

    public String getName() {
        return name_;
    }

    public String getEmail() {
        return email_;
    }
}
