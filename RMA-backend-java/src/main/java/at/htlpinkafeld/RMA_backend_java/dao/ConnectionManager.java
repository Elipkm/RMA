package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.WrappedConnection;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

public abstract class ConnectionManager {
    private volatile static ConnectionManager connectionManager = null;

    public static ConnectionManager getInstance() throws SQLException {
        synchronized (ConnectionManager.class) {
            if (connectionManager == null) {
                connectionManager = new WebConnectionManager();
            }
            return connectionManager;
        }
    }

    public abstract WrappedConnection getWrappedConnection() throws SQLException;
    public abstract void closeFinally();



    private static class WebConnectionManager extends ConnectionManager {
        private DataSource datSrc;

        private WebConnectionManager() {

            try {
                Context ctx = new javax.naming.InitialContext();

                String dsName = "jdbc/RMA";
                datSrc = (DataSource) ctx.lookup("java:comp/env/" + dsName);


            } catch (NamingException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public WrappedConnection getWrappedConnection() throws SQLException {
            return new WrappedConnection(this.datSrc.getConnection(), true);
        }

        @Override
        public void closeFinally() {
            // close at the end not needed, do nothing
        }
    }
}