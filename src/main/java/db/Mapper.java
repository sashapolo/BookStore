package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/19/13
 * Time: 1:38 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class Mapper<T> {
    protected Connection connection_;

    public static int getId(final Statement statement) throws SQLException, DataMapperException {
        ResultSet keys = null;
        try {
            keys = statement.getGeneratedKeys();
            assert (keys != null): "Forgot to add Statement.RETURN_GENERATED_KEYS";
            if (keys.next()) {
                return keys.getInt(1);
            }
            throw new DataMapperException("Error occurred while retrieving primary key");
        } finally {
            if (keys != null) keys.close();
        }
    }

    public Mapper(final Connection connection) {
        assert (connection != null);
        connection_ = connection;
    }

    public abstract T find(final int id) throws DataMapperException;
    public abstract int insert(final T param) throws DataMapperException;
    public abstract void update(final T param) throws DataMapperException;
    public abstract void delete(final T param) throws DataMapperException;
}
