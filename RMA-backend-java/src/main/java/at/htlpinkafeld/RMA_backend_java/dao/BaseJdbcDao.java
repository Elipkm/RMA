package at.htlpinkafeld.RMA_backend_java.dao;


import at.htlpinkafeld.RMA_backend_java.WrappedConnection;
import at.htlpinkafeld.RMA_backend_java.exception.DAOSysException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseJdbcDao<T extends Identifiable> {
    private final String TABLENAME;
    private final String PKNAME;


    protected abstract T getPojoFromResultSet(ResultSet result) throws SQLException;

    protected abstract PreparedStatement getUpdateStatement(Connection conn, T t) throws SQLException;

    protected abstract PreparedStatement getInsertStatement(Connection conn, T t) throws SQLException;

    public BaseJdbcDao(String tablename, String pkName) {
        this.TABLENAME = tablename;
        this.PKNAME = pkName;
    }

    public String getTablename() {
        return TABLENAME;
    }

    public String getPkName() {
        return PKNAME;
    }

    private PreparedStatement getPreparedStatement(Connection conn, String sql, int id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        return statement;
    }

    public final void delete(T t) throws DAOSysException {
        if(t.getID() < 0) {
            return;
        }

        String sql = "DELETE FROM " + this.TABLENAME + " WHERE " + this.PKNAME + " = ?";
        try(WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
            PreparedStatement statement = getPreparedStatement(wrCon.getConn(), sql, t.getID())) {

            if(statement.executeUpdate() == 0) {
                throw new DAOSysException("nothing was updated");
            }
        } catch (SQLException e) {
            throw new DAOSysException(e.getMessage());
        }
    }

    //template methods because of use of getPojoFromResultSet()
    public final T get(int id) throws DAOSysException {
        T t = null;
        String sql = "SELECT * FROM " + this.TABLENAME + " WHERE " + this.PKNAME + " = ?";

        try( WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
             PreparedStatement statement = getPreparedStatement(wrCon.getConn(),sql,id);
            ResultSet result = statement.executeQuery()) {

            if(result.next()) {
                t = getPojoFromResultSet(result);
            }
        } catch (SQLException e) {
            throw new DAOSysException(e.getMessage());
        }
        return t;
    }

    public final List<T> list() throws DAOSysException {
        List<T> results = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLENAME;
        try (WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
             Statement statement = wrCon.getConn().createStatement();
            ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                results.add(getPojoFromResultSet(result));
            }
        } catch (SQLException e) {
            throw new DAOSysException(e.getMessage());
        }
        return results;
    }

    public final void update(T t) throws DAOSysException {
        if (t.getID() < 0) {
            return;
        }

        try( WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
             PreparedStatement statement = getUpdateStatement(wrCon.getConn(), t)  ){

            if(statement.executeUpdate() == 0) {
                throw new DAOSysException("nothing was updated");
            }
        } catch (SQLException e) {
            throw new DAOSysException(e.getMessage());
        }
    }

    public final void create(T t) throws DAOSysException {
        if (t.getID() >= 0) {
            return;
        }

        try( WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
             PreparedStatement statement = getInsertStatement(wrCon.getConn(), t);
             ResultSet generatedKeys = (statement.executeUpdate()==1) ? statement.getGeneratedKeys() : null ) {

            if (generatedKeys != null && generatedKeys.next() ) {
                t.setID(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new DAOSysException(e.getMessage());
        }
    }

}