/**
 * 
 */
package business;

/**
 * @author alexander
 * 
 */
public abstract class User {
    protected final long id_ = IdDispatcher.INSTANCE.getNewUserId();
    protected String login_;
    protected int passwordHash_;
    protected String name_;
    protected String secondName_;
    protected String email_;

    public User(String login, String password, String name, String secondName, String email) {
        assert (login != null);
        assert (password != null);
        assert (name != null);

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
}