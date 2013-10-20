/**
 * 
 */
package business;

/**
 * @author alexander
 * 
 */
public class User {
    private final int id;
    private final String login;
    private final int password;
    private final Credentials credentials;

    User(int id, final String login, int password, final Credentials credentials) {
        assert (login != null);

        this.id = id;
        this.login = login;
        this.password = password;
        this.credentials = credentials;
    }

    public final int getId() {
        return id;
    }

    public final String getLogin() {
        return login;
    }

    public final int getPasswordHash() {
        return password;
    }

    public final String getName() {
        return credentials.getName();
    }

    public final String getSecondName() {
        return credentials.getSecondName();
    }

    public final String getEmail() {
        return credentials.getEmail();
    }
}
