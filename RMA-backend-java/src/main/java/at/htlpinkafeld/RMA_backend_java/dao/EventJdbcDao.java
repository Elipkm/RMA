package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.pojo.Event;
import at.htlpinkafeld.RMA_backend_java.pojo.User;

import java.sql.*;
import java.util.List;

public class EventJdbcDao extends BaseJdbcDao<Event> implements EventDao {

    public EventJdbcDao() {
        super("Event", "EventID");
    }

    @Override
    protected Event getPojoFromResultSet(ResultSet result) throws SQLException {
        Event e = new Event(result.getString("Name"), null, null);
        e.setStartDate(result.getDate("StartDate"));
        e.setEndDate(result.getDate("EndDate"));
        e.setID(result.getInt(getPkName()));
        return e;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection conn, Event e) throws SQLException {
        String sql = "UPDATE " + getTablename() + " SET Name = ?, StartDate = ?, EndDate = ? WHERE " + getPkName() + " = ?";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, e.getName());
        statement.setDate(2, e.getStartDate());
        statement.setDate(3, e.getEndDate());
        statement.setLong(4, e.getID());
        return statement;
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection conn, Event e) throws SQLException {
        String sql = "INSERT INTO " + getTablename() + " (Name, StartDate, EndDate) VALUES(?,?,?)";

        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, e.getName());
        statement.setDate(2, e.getStartDate());
        statement.setDate(3, e.getEndDate());
        return  statement;
    }

    @Override
    public List<Event> list(User user) {
        return null;
    }
}
