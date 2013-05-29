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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexander
 */
public class PublisherMapper extends Mapper<Publisher> {
    
    public PublisherMapper(final Connection connection) {
        super(connection);
    }
    
    public List<Publisher> findAll() throws DataMapperException {
        try (final Statement statement = connection_.createStatement()) {
            final String query = "SELECT * from Publishers";
            final ResultSet rs = statement.executeQuery(query);
            final List<Publisher> result = new LinkedList<>();
            while (rs.next()) {
                final int id = rs.getInt("Id");
                final String name = rs.getString("Name");
                result.add(new Publisher(id, name));
            }
            return result;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for publisher", e);
        }
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
