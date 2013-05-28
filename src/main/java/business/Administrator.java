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
                         final String name,
                         final String secondName,
                         final String email) {
        super(id, login, password, name, secondName, email);
    }
    
    public Administrator(final String login,
                         final int password,
                         final String name,
                         final String secondName,
                         final String email) {
        super(login, password, name, secondName, email);
    }
}
