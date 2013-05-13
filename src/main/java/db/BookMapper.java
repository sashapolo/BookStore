package db;

import business.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/13/13
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookMapper {
    private ConnectionManager connectionManager_;

    public BookMapper(ConnectionManager manager) {
        assert(manager != null);

        connectionManager_ = manager;
    }

    public List<Book> findByName(final String bookName) throws DataMapperException {
        assert(name != null);

        List<Book> result = new LinkedList<>();
        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "SELECT * from Books where Name=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, bookName);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String jenre = rs.getString("Jenre");
                Isbn isbn = new Isbn13(rs.getString("Isbn"));
                GregorianCalendar date = new GregorianCalendar();
                date.setTime(rs.getDate("PublicationDate"));
                double price = rs.getDouble("Price");
                Discount discount = new Discount(rs.getInt("Discount"));
                UserMapper userMapper = new UserMapper(connectionManager_);
                Publisher publisher = (Publisher
            }

            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for book", e);
        } finally {
            if (conn != null) {
                try {
                    connectionManager_.closeConnection(conn);
                } catch (SQLException e2) {}
            }
        }
    }
}
