package at.htlpinkafeld.restful.dao;

import at.htlpinkafeld.restful.exception.DaoNotFoundException;
import at.htlpinkafeld.restful.exception.DaoSysException;
import at.htlpinkafeld.restful.pojo.Event;
import at.htlpinkafeld.restful.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventJdbcDao implements EventDao {

    private BaseJdbcDao<Event> jdbcDaoHelper;
    public EventJdbcDao() {
        this.jdbcDaoHelper = new BaseJdbcDao<Event>("Event", "EventID") {
            @Override
            protected Event getPojoFromResultSet(ResultSet result) throws SQLException {
                Event e = new Event(result.getString("Name"), null, null);
                e.setStartDate(result.getDate("StartDate"));
                e.setEndDate(result.getDate("EndDate"));
                e.setID(result.getInt(getPkName()));
                return e;
            }

            @Override
            protected PreparedStatement getUpdateStatement(Connection conn, Event event) throws SQLException {
                String sql = "UPDATE " + getTablename() + " SET Name = ?, StartDate = ?, EndDate = ? WHERE " + getPkName() + " = ?";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, event.getName());
                statement.setDate(2, event.getStartDate());
                statement.setDate(3, event.getEndDate());
                statement.setLong(4, event.getID());
                return statement;
            }

            @Override
            protected PreparedStatement getInsertStatement(Connection conn, Event event) throws SQLException {
                String sql = "INSERT INTO " + getTablename() + " (Name, StartDate, EndDate) VALUES(?,?,?)";

                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, event.getName());
                statement.setDate(2, event.getStartDate());
                statement.setDate(3, event.getEndDate());
                return statement;
            }
        };
    }

    @Override
    public List<Event> list(User user) throws DaoSysException {
        try (WrappedConnection wrappedConnection = ConnectionManager.getInstance().getWrappedConnection();
             Statement statement = wrappedConnection.getConn().createStatement();
             ResultSet eventIdResult = statement.executeQuery("SELECT EventID FROM Ownership WHERE UserID = " + user.getID())) {

            List<Event> results = new ArrayList<>();

            while (eventIdResult.next()) {
                String eventId = eventIdResult.getString("EventID");

                try(Statement statement1 = wrappedConnection.getConn().createStatement();
                    ResultSet resultSet = statement1.executeQuery("SELECT * FROM Event WHERE EventID = " + eventId)) {
                    while (resultSet.next()) {
                        Event event = this.jdbcDaoHelper.getPojoFromResultSet(resultSet);
                        results.add(event);
                    }
                }catch (SQLException sqlException){
                    throw (DaoSysException) new DaoSysException(sqlException.getMessage()).initCause(sqlException);
                }
            }
            return results;

        } catch (SQLException e) {
            throw (DaoSysException) new DaoSysException(e.getMessage()).initCause(e);
        }
    }

    @Override
    public void create(User user, Event event) throws DaoSysException,IllegalArgumentException {
        if (event.getID() >= 0) {
            throw new IllegalArgumentException("Id must not be set.");
        }
        this.jdbcDaoHelper.create(event);

        try( WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
             PreparedStatement statement = wrCon.getConn().prepareStatement
                     ("INSERT INTO Ownership (EventID, UserID) VALUES(" + event.getID() + ", " + user.getID() + ")")
             ){
            statement.executeUpdate();
        } catch (SQLException e) {
            throw (DaoSysException) new DaoSysException(e.getMessage()).initCause(e);
        }
    }

    @Override
    public Event read(User user, int id) throws DaoNotFoundException, DaoSysException {
        if(ownsUserEvent(user,id)){
            return this.jdbcDaoHelper.read(id);
        }
        return null;
    }

    @Override
    public void update(User user, Event event) throws DaoNotFoundException, DaoSysException {
        if(ownsUserEvent(user, event.getID())){
            this.jdbcDaoHelper.update(event);
        }
    }

    @Override
    public void delete(User user, Event event) throws DaoNotFoundException, DaoSysException {
        if(ownsUserEvent(user, event.getID())){
            this.jdbcDaoHelper.delete(event);
        }
    }
    private PreparedStatement getPreparedStatement(Connection conn, String sql, int id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        return statement;
    }

    private boolean ownsUserEvent(User user, int eventId) throws DaoSysException, DaoNotFoundException {
        try (WrappedConnection wrappedConnection = ConnectionManager.getInstance().getWrappedConnection();
             Statement statement = wrappedConnection.getConn().createStatement();
             ResultSet eventIdResult = statement.executeQuery("SELECT EventID FROM Ownership WHERE UserID = " + user.getID())) {

            while (eventIdResult.next()) {
                String id = eventIdResult.getString("EventID");
                if(Integer.parseInt(id) == eventId){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw (DaoSysException) new DaoSysException(e.getMessage()).initCause(e);
        }
        throw new DaoNotFoundException("Could not find an event with matching id.");
    }
}
