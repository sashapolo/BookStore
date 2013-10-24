package edu.service;

import edu.Main;
import business.Credentials;
import business.Customer;
import business.User;
import edu.db.ConnectionManager;
import edu.db.DataMapperException;
import edu.db.DerbyConnectionManager;
import edu.db.UserMapper;
import exception.EntryNotFoundException;
import exception.EntryRedefinitionException;
import exception.IncorrectPasswordException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/26/13
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public final class UserCatalogue {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static User getUser(final String login, final String password)
            throws EntryNotFoundException, IncorrectPasswordException {
        final ConnectionManager manager = new DerbyConnectionManager();
        try (final Connection connection = manager.getConnection("db")) {
            final UserMapper mapper = new UserMapper(connection);
            final User result = mapper.find(login);

            if (result == null)
                throw new EntryNotFoundException("user not found");
            if (password.hashCode() != result.getPasswordHash())
                throw new IncorrectPasswordException();

            return result;
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }
    
    public static void createUser(final String login, 
                                  final String password,
                                  final Credentials credentials)
            throws EntryRedefinitionException {
        final ConnectionManager manager = new DerbyConnectionManager();
        try (final Connection connection = manager.getConnection("db")) {
            final UserMapper mapper = new UserMapper(connection);
            final User test = mapper.find(login);
            if (test != null) throw new EntryRedefinitionException("user already exists");

            final User user = new Customer(-1, login, password.hashCode(), credentials);
            mapper.insert(user);
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    private UserCatalogue() {}
}
