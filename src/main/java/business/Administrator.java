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
                         final String password,
                         final String name,
                         final String email) {
        super(id, login, password, name, email);
    }
}
