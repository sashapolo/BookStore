/**
 * 
 */
package business;

import java.io.*;

/**
 * @author alexander
 * 
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 3577630894764478936L;
    private final int id;
    private final String login;
    private final int password;
    private transient Credentials credentials;

    public User(final int id, final String login,
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

    private void readObject(final ObjectInputStream inputStream)
            throws ClassNotFoundException, IOException {
        inputStream.defaultReadObject();

        final String name = (String) inputStream.readObject();
        final String secondName = (String) inputStream.readObject();
        final String email = (String) inputStream.readObject();
        credentials = new Credentials(name, secondName, email);
    }

    private void writeObject(final ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();

        outputStream.writeObject(credentials.getName());
        outputStream.writeObject(credentials.getSecondName());
        outputStream.writeObject(credentials.getEmail());
    }

    private void readObjectNoData() throws InvalidObjectException {
        throw new InvalidObjectException("Stream data required");
    }
}
