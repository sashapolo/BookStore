/**
 * 
 */
package business;

/**
 * @author alexander
 * 
 */
public class Administrator extends User {
    private static final long serialVersionUID = -6664202719240284460L;

    public Administrator(final int id,
                         final String login,
                         final int password,
                         final Credentials credentials) {
        super(id, login, password, credentials);
    }
}
