package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.exception.DaoNotFoundException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.Event;
import at.htlpinkafeld.RMA_backend_java.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventJdbcDao extends BaseJdbcDao<Event> implements EventDao {

    // TODO Unit Tests and Refactor
    // TODO foreign key on delete constraint

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
        return statement;
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
                        Event event = getPojoFromResultSet(resultSet);
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

        try( WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
             PreparedStatement statement = getInsertStatement(wrCon.getConn(), event);
             ResultSet generatedKeys = (statement.executeUpdate()==1) ? statement.getGeneratedKeys() : null ) {

            if (generatedKeys != null && generatedKeys.next() ) {
                event.setID(generatedKeys.getInt(1));
            }

            PreparedStatement statement1 = wrCon.getConn()
                    .prepareStatement("INSERT INTO Ownership (EventID, UserID) VALUES(" + event.getID() + ", " + user.getID() + ")");
            statement1.executeUpdate();
            statement1.close();

        } catch (SQLException e) {
            throw (DaoSysException) new DaoSysException(e.getMessage(), e.getErrorCode()).initCause(e);
        }
    }

    @Override
    public Event read(User user, int id) throws DaoNotFoundException, DaoSysException {
        try (WrappedConnection wrappedConnection = ConnectionManager.getInstance().getWrappedConnection();
             Statement statement = wrappedConnection.getConn().createStatement();
             ResultSet eventIdResult = statement.executeQuery("SELECT EventID FROM Ownership WHERE UserID = " + user.getID())) {

            while (eventIdResult.next()) {
                String eventId = eventIdResult.getString("EventID");
                if (Integer.parseInt(eventId) == id) {
                    try (Statement statement1 = wrappedConnection.getConn().createStatement();
                         ResultSet resultSet = statement1.executeQuery("SELECT * FROM Event WHERE EventID = " + id)) {
                        if (resultSet.next()) {
                            Event event = getPojoFromResultSet(resultSet);
                            resultSet.close();
                            return event;
                        }
                    } catch (SQLException sqlException) {
                        throw (DaoSysException) new DaoSysException(sqlException.getMessage()).initCause(sqlException);
                    }
                }
            }
        } catch (SQLException e) {
            throw (DaoSysException) new DaoSysException(e.getMessage()).initCause(e);
        }
        throw new DaoNotFoundException("Could not find the appropriate event.");
    }

    @Override
    public void update(User user, Event event) throws DaoNotFoundException, DaoSysException {
        if (event.getID() < 0 || !ownsUserEvent(user, event.getID())) {
            return;
        }

        try( WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
             PreparedStatement statement = getUpdateStatement(wrCon.getConn(), event)  ){

            if(statement.executeUpdate() == 0) {
                throw new DaoSysException("nothing was updated");
            }
        } catch (SQLException e) {
            throw (DaoSysException) new DaoSysException(e.getMessage()).initCause(e);
        }
    }

    @Override
    public void delete(User user, Event event) throws DaoNotFoundException, DaoSysException {
        if(event.getID() < 0 || !ownsUserEvent(user, event.getID())) {
            return;
        }

        String sql = "DELETE FROM Event WHERE EventID = ?";
        try(WrappedConnection wrCon = ConnectionManager.getInstance().getWrappedConnection();
            PreparedStatement statement = getPreparedStatement(wrCon.getConn(), sql, event.getID())) {

            if(statement.executeUpdate() == 0) {
                throw new DaoSysException("nothing was updated");
            }
        } catch (SQLException | DaoSysException e) {
            throw (DaoSysException) new DaoSysException(e.getMessage()).initCause(e);
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
