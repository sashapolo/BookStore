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
    protected int password_;
    protected String name_;
    protected String secondName_;
    protected String email_;

    public User(final int id,
                final String login,
                final int password,
                final String name,
                final String secondName,
                final String email) {
        assert (login != null);
        assert (name != null);
        assert (secondName != null);

        id_ = id;
        login_ = login;
        password_ = password;
        name_ = name;
        secondName_ = secondName;
        email_ = email;
    }

    public int getId() {
        return id_;
    }

    public String getLogin() {
        return login_;
    }

    public int getPasswordHash() {
        return password_;
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
}
