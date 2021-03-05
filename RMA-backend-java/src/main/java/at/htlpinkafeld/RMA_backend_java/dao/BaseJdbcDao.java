package at.htlpinkafeld.RMA_backend_java.dao;


import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.Identifiable;

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

    public final void delete(T t) throws DaoSysException {
        if(t.getID() < 0) {
            return;
        }

        String sql = "DELETE FROM " + this.TABLENAME + " WHERE " + this.PKNAME + " = ?";
        try(WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
            PreparedStatement statement = getPreparedStatement(wrCon.getConn(), sql, t.getID())) {

            if(statement.executeUpdate() == 0) {
                throw new DaoSysException("nothing was updated");
            }
        } catch (SQLException e) {
            throw (DaoSysException) new DaoSysException(e.getMessage()).initCause(e);
        }
    }

    //template methods because of use of getPojoFromResultSet()
    public final T read(int id) throws DaoSysException {
        T t = null;
        String sql = "SELECT * FROM " + this.TABLENAME + " WHERE " + this.PKNAME + " = ?";

        try( WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
             PreparedStatement statement = getPreparedStatement(wrCon.getConn(),sql,id);
            ResultSet result = statement.executeQuery()) {

            if(result.next()) {
                t = getPojoFromResultSet(result);
            }
        } catch (SQLException e) {
            throw (DaoSysException) new DaoSysException(e.getMessage()).initCause(e);
        }
        return t;
    }

    public final List<T> list() throws DaoSysException {
        List<T> results = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLENAME;

        try (WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
             Statement statement = wrCon.getConn().createStatement();
            ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                results.add(getPojoFromResultSet(result));
            }
        } catch (SQLException e) {
            throw (DaoSysException) new DaoSysException(e.getMessage()).initCause(e);
        }
        return results;
    }

    public final void update(T t) throws DaoSysException {
        if (t.getID() < 0) {
            return;
        }

        try( WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
             PreparedStatement statement = getUpdateStatement(wrCon.getConn(), t)  ){

            if(statement.executeUpdate() == 0) {
                throw new DaoSysException("nothing was updated");
            }
        } catch (SQLException e) {
            throw (DaoSysException) new DaoSysException(e.getMessage()).initCause(e);
        }
    }

    public final void create(T t) throws DaoSysException {
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
            throw (DaoSysException) new DaoSysException(e.getMessage(), e.getErrorCode()).initCause(e);
        }
    }
}