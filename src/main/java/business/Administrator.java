/**
 * 
 */
package business;

/**
 * @author alexander
 * 
 */
public class Administrator extends User {
    public Administrator(final int id,
                         final String login,
                         final int password,
                         final Credentials credentials) {
        super(id, login, password, credentials);
    }
}
