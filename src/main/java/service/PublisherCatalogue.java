/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.BookStore;
import business.Publisher;
import db.ConnectionManager;
import db.DataMapperException;
import db.DerbyConnectionManager;
import db.PublisherMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander
 */
public final class PublisherCatalogue {
    private static final Logger LOGGER = Logger.getLogger(BookStore.class.getName());
    
    public static List<String> getPublisherNames() {
        final ConnectionManager manager = new DerbyConnectionManager();
        try (final Connection connection = manager.getConnection("db")) {
            final PublisherMapper mapper = new PublisherMapper(connection);
            final List<Publisher> publishers = mapper.findAll();

            final List<String> result = new LinkedList<>();
            for (final Publisher pub : publishers) {
                result.add(pub.getName());
            }
            return result;
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e);
        }
    }
    
    public static Publisher getPublisher(final String name) throws EntryNotFoundException {
        final ConnectionManager manager = new DerbyConnectionManager();
        try (final Connection connection = manager.getConnection("db")) {
            final PublisherMapper mapper = new PublisherMapper(connection);
            final Publisher result = mapper.find(name);

            if (result == null) throw new EntryNotFoundException("Publisher not found");
            return result;
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e);
        }
    }
    
    public static void updatePublisher(final Publisher pub) {
        assert pub != null;
        
        final ConnectionManager manager = new DerbyConnectionManager();
        try (final Connection connection = manager.getConnection("db")) {
            final PublisherMapper mapper = new PublisherMapper(connection);
            mapper.update(pub);
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e);
        }
    }
    
    public static void deletePublisher(final Publisher pub) {
        assert pub != null;
        
        final ConnectionManager manager = new DerbyConnectionManager();
        try (final Connection connection = manager.getConnection("db")) {
            final PublisherMapper mapper = new PublisherMapper(connection);
            mapper.delete(pub.getId());
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e);
        }
    }
    
    public static void createPublisher(final String name) {
        assert name != null;
        
        final ConnectionManager manager = new DerbyConnectionManager();
        try (Connection connection = manager.getConnection("db")) {
            final PublisherMapper mapper = new PublisherMapper(connection);
            mapper.insert(new Publisher(name));
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    private PublisherCatalogue() {}
}
