package edu.service;

import edu.business.Customer;
import edu.business.Order;
import edu.business.OrderEntry;
import edu.Main;
import edu.db.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author alexander
 *
 */
final class OrderCatalogue {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static void createOrder(final Customer user) {
        assert user != null;
        
        final ConnectionManager manager = new DerbyConnectionManager();
        try (final Connection connection = manager.getConnection("db")) {
            final OrderMapper mapper = new OrderMapper(connection);
            final Order order = new Order.Builder(user.getCart(), user).build();
            mapper.insert(order);
            
            final BookMapper bookMapper = new BookMapper(connection);
            for (final OrderEntry entry : user.getCart()) {
                bookMapper.buy(entry.getBook().getId(), entry.getAmount());
            }
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    private OrderCatalogue() {}
}
