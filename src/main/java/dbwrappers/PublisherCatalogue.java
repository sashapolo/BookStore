/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbwrappers;

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
public class PublisherCatalogue {
    private static final Logger LOGGER = Logger.getLogger(BookStore.class.getName());
    
    public static List<String> getPublisherNames() {
        final ConnectionManager manager = new DerbyConnectionManager();
        try (Connection connection = manager.getConnection("db")) {
            final PublisherMapper mapper = new PublisherMapper(connection);
            final List<Publisher> publishers = mapper.findAll();

            final List<String> result = new LinkedList<>();
            for (final Publisher pub : publishers) {
                result.add(pub.getName());
            }
            return result;
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }
}
