/**
 * 
 */
package business;

import java.io.Serializable;

/**
 * @author alexander
 * 
 */
public class User implements Serializable {
    private static final long serialVersionUID = 3577630894764478936L;
    private final int id;
    private final String login;
    private final int password;
    private final Credentials credentials;

    User(final int id, final String login,
         final int password, final Credentials credentials) {
        assert login != null;

        this.id = id;
        this.login = login;
        this.password = password;
        this.credentials = credentials;
    }

    public final int getId() {
        assert id != -1;
        return id;
    }

    public final String getLogin() {
        assert login != null;
        return login;
    }

    public final int getPasswordHash() {
        return password;
    }

    public final String getName() {
        assert credentials != null;
        return credentials.getName();
    }

    public final String getSecondName() {
        assert credentials != null;
        return credentials.getSecondName();
    }

    public final String getEmail() {
        assert credentials != null;
        return credentials.getEmail();
    }
}
