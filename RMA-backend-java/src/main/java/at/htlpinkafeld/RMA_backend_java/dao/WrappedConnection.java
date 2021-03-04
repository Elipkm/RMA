package at.htlpinkafeld.RMA_backend_java.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class WrappedConnection implements AutoCloseable {
    private final Connection conn;
    private final boolean doClose;

    public WrappedConnection(Connection conn, boolean doClose) {
        this.conn = conn;
        this.doClose = doClose;
    }

    public Connection getConn() { return conn; }

    @Override
    public void close() throws SQLException {
        if (this.doClose)
            this.conn.close();
    }
}