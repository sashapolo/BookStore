package db;

import business.Administrator;
import business.Customer;
import business.Publisher;
import business.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/13/13
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserMapper {
    private final Connection connection_;

    public UserMapper(final Connection connection) {
        assert(connection != null);
        connection_ = connection;
    }

    private User find(final PreparedStatement statement) throws SQLException, DataMapperException {
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
                    return new Customer(id,
                                        login,
                                        password,
                                        name,
                                        secondName,
                                        email,
                                        discount);
                case 1:
                    return new Administrator(id,
                                             login,
                                             password,
                                             name,
                                             secondName,
                                             email);
                case 2:
                    return new Publisher(id,
                                         login,
                                         password,
                                         name,
                                         secondName,
                                         email);
                default:
                    throw new DataMapperException("Unknown user type");
            }
        }

        return null;
    }

    public User findByLogin(final String login) throws DataMapperException {
        assert(login != null);

        PreparedStatement statement = null;
        try {
            final String query = "SELECT * from USERS where Login=?";
            statement = connection_.prepareStatement(query);
            statement.setString(1, login);

            return find(statement);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public User findById(final int id) throws DataMapperException {
        PreparedStatement statement = null;
        try {
            final String query = "SELECT * from USERS where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, id);

            return find(statement);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void insert(final Administrator user) throws DataMapperException {
        assert (user != null);

        PreparedStatement statement = null;
        try {
            final String query = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
            statement = connection_.prepareStatement(query);

            statement.setInt(1, 1);
            statement.setString(2, user.getLogin());
            statement.setInt(3, user.getPasswordHash());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSecondName());
            statement.setString(6, user.getEmail());
            statement.setInt(7, 0);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting a user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void insert(final Publisher user) throws DataMapperException {
        assert (user != null);

        PreparedStatement statement = null;
        try {
            final String query = "INSERT into Users VALUES (?, ?, ?, ?, ?, ?, ?)";
            statement = connection_.prepareStatement(query);

            statement.setInt(1, 2);
            statement.setString(2, user.getLogin());
            statement.setInt(3, user.getPasswordHash());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSecondName());
            statement.setString(6, user.getEmail());
            statement.setInt(7, 0);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting a user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void insert(final Customer user) throws DataMapperException {
        assert (user != null);

        PreparedStatement statement = null;
        try {
            final String query = "INSERT into Users VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection_.prepareStatement(query);

            statement.setInt(1, 0);
            statement.setString(2, user.getLogin());
            statement.setInt(3, user.getPasswordHash());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSecondName());
            statement.setString(6, user.getEmail());
            statement.setInt(7, user.getPersonalDiscount().integerValue());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting a user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void update(final Administrator user) throws DataMapperException {
        assert (user != null);

        PreparedStatement statement = null;
        try {
            final String query = "UPDATE Users SET " +
                                 "Password=?, Name=?, SecondName=?, Email=?, PersonalDiscount=? " +
                                 "where Login=?";
            statement = connection_.prepareStatement(query);

            statement.setInt(1, user.getPasswordHash());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSecondName());
            statement.setString(4, user.getEmail());
            statement.setInt(5, 0);
            statement.setString(6, user.getLogin());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while updating a user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void update(final Publisher user) throws DataMapperException {
        assert (user != null);

        PreparedStatement statement = null;
        try {
            final String query = "UPDATE Users SET " +
                                 "Password=?, Name=?, SecondName=?, Email=?, PersonalDiscount=? " +
                                 "where Login=?";
            statement = connection_.prepareStatement(query);

            statement.setInt(1, user.getPasswordHash());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSecondName());
            statement.setString(4, user.getEmail());
            statement.setInt(5, 0);
            statement.setString(6, user.getLogin());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while updating a user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void update(final Customer user) throws DataMapperException {
        assert (user != null);

        PreparedStatement statement = null;
        try {
            final String query = "UPDATE Users SET " +
                    "Password=?, Name=?, SecondName=?, Email=?, PersonalDiscount=? " +
                    "where Login=?";
            statement = connection_.prepareStatement(query);

            statement.setInt(1, user.getPasswordHash());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSecondName());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getPersonalDiscount().integerValue());
            statement.setString(6, user.getLogin());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while updating a user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void delete(final User user) throws DataMapperException {
        assert (user != null);

        PreparedStatement statement = null;
        try {
            final String query = "DELETE from Users where Id=?";
            statement = connection_.prepareStatement(query);

            statement.setInt(1, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting a user", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }
}
