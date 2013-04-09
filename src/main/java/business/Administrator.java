/**
 * 
 */
package business;

/**
 * @author alexander
 * 
 */
public class Administrator extends User {
    public Administrator(final String login,
                         final String password,
                         final String name,
                         final String secondName,
                         final String email) {
        super(login, password, name, secondName, email);
    }
}
