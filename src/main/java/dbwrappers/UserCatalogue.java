package dbwrappers;

import business.BookStore;
import business.User;
import db.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/26/13
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserCatalogue {
    private static final Logger LOGGER = Logger.getLogger(BookStore.class.getName());

    public User getUser(final String login, final String password) throws UserNotFoundException, IncorrectPasswordException {
        assert (login != null);
        assert (password != null);

        final ConnectionManager manager = new DerbyConnectionManager();
        Connection connection = null;
        try {
            connection = manager.getConnection();
            final UserMapper mapper = new UserMapper(connection);
            final User result = mapper.find(login);

            if (result == null)
                throw new UserNotFoundException("user not found");
            if (password.hashCode() != result.getPasswordHash())
                throw new IncorrectPasswordException("incorrect password");

            return result;
        } catch (SQLException | DataMapperException e) {
            LOGGER.severe("Error while finding user: " + e.getMessage());
            throw new IllegalStateException("something is very wrong :(", e);
        } finally {
            try {
                if (connection != null) manager.closeConnection(connection);
            } catch (SQLException e) {}
        }
    }
}
