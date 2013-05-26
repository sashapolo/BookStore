package db;

import business.Administrator;
import business.Customer;
import business.Publisher;
import business.User;
import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/13/13
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserMapper extends Mapper<User>{

    public UserMapper(final Connection connection) {
        super(connection);
    }

    public User find(final String login) throws DataMapperException {
        assert(login != null);

        PreparedStatement statement = null;
        try {
            final String query = "SELECT * from USERS where Login=?";
            statement = connection_.prepareStatement(query);
            statement.setString(1, login);
            return createUser(statement);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    @Override
    public User find(final int id) throws DataMapperException {
        PreparedStatement statement = null;
        try {
            final String query = "SELECT * from USERS where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, id);
            return createUser(statement);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    @Override
    public int insert(final User user) throws DataMapperException {
        assert (user != null);

        PreparedStatement statement = null;
        try {
            if (user instanceof Administrator) {
                statement = getInsertQuery((Administrator) user);
            } else if (user instanceof Publisher) {
                statement = getInsertQuery((Publisher) user);
            } else if (user instanceof Customer) {
                statement = getInsertQuery((Customer) user);
            } else {
                throw new DataMapperException("Unknown user type");
            }
            statement.executeUpdate();
            return getId(statement);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting a user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    @Override
    public void update(final User user) throws DataMapperException {
        assert (user != null);

        PreparedStatement statement = null;
        try {
            if (user instanceof Administrator) {
                statement = getUpdateQuery((Administrator) user);
            } else if (user instanceof Publisher) {
                statement = getUpdateQuery((Publisher) user);
            } else if (user instanceof Customer) {
                statement = getUpdateQuery((Customer) user);
            } else {
                throw new DataMapperException("Unknown user type");
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while updating a user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    @Override
    public void delete(final int id) throws DataMapperException {
        PreparedStatement statement = null;
        try {
            final String query = "DELETE from Users where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting a user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    private User createUser(final PreparedStatement statement) throws SQLException, DataMapperException {
        assert(statement != null);

        final ResultSet result = statement.executeQuery();
        if (result.next()) {
            final int password = result.getInt("Password");
            final int id = result.getInt("Id");
            final int type = result.getInt("Type");
            final String login = result.getString("Login");
            final String name = result.getString("Name");
            final String secondName = result.getString("SecondName");
            final String email = result.getString("Email");

            switch (type) {
            case 0:
                final int discount = result.getInt("PersonalDiscount");
                return new Customer(id, login, password, name, secondName, email, discount);
            case 1:
                return new Administrator(id, login, password, name, secondName, email);
            case 2:
                return new Publisher(id, login, password, name, secondName, email);
            default:
                throw new DataMapperException("Unknown user type");
            }
        }
        return null;
    }

    private PreparedStatement getInsertQuery(final Administrator user) throws SQLException {
        final String query = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";
        final PreparedStatement statement = connection_.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, 1);
        statement.setString(2, user.getLogin());
        statement.setInt(3, user.getPasswordHash());
        statement.setString(4, user.getName());
        statement.setString(5, user.getSecondName());
        statement.setString(6, user.getEmail());
        statement.setInt(7, 0);
        return statement;
    }

    private PreparedStatement getInsertQuery(final Publisher user) throws SQLException {
        final String query = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";
        final PreparedStatement statement = connection_.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, 2);
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
        final PreparedStatement statement = connection_.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, 0);
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
        final PreparedStatement statement = connection_.prepareStatement(query);
        statement.setString(1, user.getLogin());
        statement.setInt(2, user.getPasswordHash());
        statement.setString(3, user.getName());
        statement.setString(4, user.getSecondName());
        statement.setString(5, user.getEmail());
        statement.setInt(6, 0);
        statement.setInt(7, user.getId());
        return statement;
    }

    private PreparedStatement getUpdateQuery(final Publisher user) throws SQLException {
        final String query = "UPDATE Users SET " +
                             "Login=?, Password=?, Name=?, SecondName=?, Email=?, PersonalDiscount=? " +
                             "where Id=?";
        final PreparedStatement statement = connection_.prepareStatement(query);
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
        final PreparedStatement statement = connection_.prepareStatement(query);
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
