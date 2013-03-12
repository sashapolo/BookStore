/**
 * 
 */
package business;

/**
 * @author alexander
 * 
 */
public class Administrator extends User {
    public Administrator(long id, String login, String password, String name, String secondName,
            String email) {
        super(id, login, password, name, secondName, email);
    }
}
