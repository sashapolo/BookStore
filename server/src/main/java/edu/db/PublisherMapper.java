/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db;

import business.Publisher;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexander
 */
public final class PublisherMapper extends Mapper<Publisher> {
    
    public PublisherMapper(final Connection connection) {
        super(connection);
    }
    
    public List<Publisher> findAll() throws DataMapperException {
        final String query = "SELECT * from Publishers";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            final ResultSet rs = statement.executeQuery();
            final List<Publisher> result = new LinkedList<>();
            while (rs.next()) {
                final int id = rs.getInt("Id");
                final String name = rs.getString("Name");
                result.add(new Publisher(id, name));
            }
            return result;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for publisher: " + e.getMessage());
        }
    }
    
    public Publisher find(final String name) throws DataMapperException {
        assert name != null;

        final String query = "SELECT * from Publishers where Name=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                final int id = rs.getInt("Id");
                return new Publisher(id, name);
            }
            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for user: " + e.getMessage());
        }
    }
    
    @Override
    public Publisher find(final int id) throws DataMapperException {
        final String query = "SELECT * from Publishers where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            final ResultSet result = statement.executeQuery();
            if (result.next()) {
                final String name = result.getString("Name");
                return new Publisher(id, name);
            }
            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for publisher: " + e.getMessage());
        }
    }
    
    @Override
    public void delete(final int id) throws DataMapperException {
        final String query = "DELETE from Publishers where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting a publisher: " + e.getMessage());
        }
    }
    
    @Override
    public void update(final Publisher pub) throws DataMapperException {
        assert pub != null;
        final String query = "UPDATE Publishers SET Name=? WHERE Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pub.getName());
            statement.setInt(2, pub.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while updating a publisher: " + e.getMessage());
        }
    }
    
    @Override
    public int insert(final Publisher pub) throws DataMapperException {
        assert pub != null;
        final String query = "INSERT into Publishers(Name) " +
                             "VALUES (?)";
        try (final PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, pub.getName());
            statement.executeUpdate();
            return getId(statement);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting a publisher: " + e.getMessage());
        }
    }
}
