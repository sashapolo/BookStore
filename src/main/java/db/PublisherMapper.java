/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import business.Publisher;
import static db.Mapper.getId;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author alexander
 */
public class PublisherMapper extends Mapper<Publisher> {
    
    public PublisherMapper(final Connection connection) {
        super(connection);
    }
    
    @Override
    public Publisher find(final int id) throws DataMapperException {
        PreparedStatement statement = null;
        try {
            final String query = "SELECT * from Publishers where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, id);
            final ResultSet result = statement.executeQuery();
            if (result.next()) {
                final String name = result.getString("Name");
                return new Publisher(id, name);
            }
            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for publisher", e);
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
            final String query = "DELETE from Publishers where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting a publisher", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }
    
    @Override
    public void update(final Publisher pub) throws DataMapperException {
        assert (pub != null);

        PreparedStatement statement = null;
        try {
            final String query = "UPDATE Publishers SET Name=? WHERE Id=?";
            statement = connection_.prepareStatement(query);
            statement.setString(1, pub.getName());
            statement.setInt(2, pub.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while updating a publisher: " + e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }
    
    @Override
    public int insert(final Publisher pub) throws DataMapperException {
        assert (pub != null);

        PreparedStatement statement = null;
        try {
            final String query = "INSERT into Publishers(Name) " +
                                 "VALUES (?)";
            statement = connection_.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, pub.getName());
            statement.executeUpdate();
            return getId(statement);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting a publisher", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }
}
