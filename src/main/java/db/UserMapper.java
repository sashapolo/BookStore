package db;

import business.Administrator;
import business.Credentials;
import business.Customer;
import business.User;
import util.ReverseEnumMap;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/13/13
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public final class UserMapper extends Mapper<User>{

    public UserMapper(final Connection connection) {
        super(connection);
    }

    public User find(final String login) throws DataMapperException {
        assert login != null;

        final String query = "SELECT * from USERS where Login=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            return createUser(statement);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for user: " + e.getMessage());
        }
    }

    @Override
    public User find(final int id) throws DataMapperException {
        final String query = "SELECT * from USERS where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return createUser(statement);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for user: " + e.getMessage());
        }
    }

    @Override
    public int insert(final User user) throws DataMapperException {
        assert user != null;

        try {
            if (user instanceof Administrator) {
                try (final PreparedStatement statement = getInsertQuery((Administrator) user)) {
                    statement.executeUpdate();
                    return getId(statement);
                }
            } else if (user instanceof Customer) {
                try (final PreparedStatement statement = getInsertQuery((Customer) user)) {
                    statement.executeUpdate();
                    return getId(statement);
                }
            } else {
                throw new DataMapperException("Unknown user type");
            }
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting a user: " + e.getMessage());
        }
    }

    @Override
    public void update(final User user) throws DataMapperException {
        assert user != null;

        try {
            if (user instanceof Administrator) {
                try (final PreparedStatement statement = getUpdateQuery((Administrator) user)) {
                    statement.executeUpdate();
                }
            } else if (user instanceof Customer) {
                try (final PreparedStatement statement = getUpdateQuery((Customer) user)) {
                    statement.executeUpdate();
                }
            } else {
                throw new DataMapperException("Unknown user type");
            }
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while updating a user: " + e.getMessage());
        }
    }

    @Override
    public void delete(final int id) throws DataMapperException {
        final String query = "DELETE from Users where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting a user: " + e.getMessage());
        }
    }

    private static User createUser(final PreparedStatement statement) throws SQLException {
        assert statement != null;

        final ResultSet result = statement.executeQuery();
        if (result.next()) {
            final int password = result.getInt("Password");
            final int id = result.getInt("Id");
            final ReverseEnumMap<UserType> reverse = new ReverseEnumMap<>(UserType.class);
            final UserType type = reverse.get(result.getInt("Type"));
            final String login = result.getString("Login");
            final String name = result.getString("Name");
            final String secondName = result.getString("SecondName");
            final String email = result.getString("Email");

            final Credentials credentials = new Credentials(name, secondName, email);
            switch (type) {
            case CUSTOMER:
                final int discount = result.getInt("PersonalDiscount");
                return new Customer(id, login, password, credentials, discount);
            case ADMIN:
                return new Administrator(id, login, password, credentials);
            case PUBLISHER:
                throw new IllegalArgumentException("There must be no Publishers in this table!");
            }
        }
        return null;
    }

    private PreparedStatement getInsertQuery(final Administrator user) throws SQLException {
        final String query = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";
        final PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, UserType.ADMIN.convert());
        statement.setString(2, user.getLogin());
        statement.setInt(3, user.getPasswordHash());
        statement.setString(4, user.getName());
        statement.setString(5, user.getSecondName());
        statement.setString(6, user.getEmail());
        statement.setInt(7, 0);
        return statement;
    }

    private PreparedStatement getInsertQuery(final Customer user) throws SQLException {
        final String query = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";
        final PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, UserType.CUSTOMER.convert());
        statement.setString(2, user.getLogin());
        statement.setInt(3, user.getPasswordHash());
        statement.setString(4, user.getName());
        statement.setString(5, user.getSecondName());
        statement.setString(6, user.getEmail());
        statement.setInt(7, user.getPersonalDiscount().integerValue());
        return statement;
    }

    private PreparedStatement getUpdateQuery(final Administrator user) throws SQLException {
        final String query = "UPDATE Users SET " +
                             "Login=?, Password=?, Name=?, SecondName=?, Email=?, PersonalDiscount=? " +
                             "where Id=?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getLogin());
        statement.setInt(2, user.getPasswordHash());
        statement.setString(3, user.getName());
        statement.setString(4, user.getSecondName());
        statement.setString(5, user.getEmail());
        statement.setInt(6, 0);
        statement.setInt(7, user.getId());
        return statement;
    }

    private PreparedStatement getUpdateQuery(final Customer user) throws SQLException {
        final String query = "UPDATE Users SET " +
                             "Login=?, Password=?, Name=?, SecondName=?, Email=?, PersonalDiscount=? " +
                             "where Id=?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getLogin());
        statement.setInt(2, user.getPasswordHash());
        statement.setString(3, user.getName());
        statement.setString(4, user.getSecondName());
        statement.setString(5, user.getEmail());
        statement.setInt(6, user.getPersonalDiscount().integerValue());
        statement.setInt(7, user.getId());
        return statement;
    }
}
