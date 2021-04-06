package at.htlpinkafeld.restful.dao;



import at.htlpinkafeld.restful.exception.DaoSysException;
import at.htlpinkafeld.restful.pojo.User;

import java.sql.*;

public class UserJdbcDao extends BaseJdbcDao<User> implements UserDao {

    public UserJdbcDao() {
        super("RMA_User", "UserID");
    }

    @Override
    protected User getPojoFromResultSet(ResultSet result) throws SQLException {
        User u = new User(result.getString("Username"), "");
        u.setPassword(result.getString("Password"), true);
        u.setID(result.getInt(getPkName()));
        return u;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection conn, User u) throws SQLException {
        String sql = "UPDATE " + getTablename() + " SET Username = ?, Password = ? WHERE " + getPkName() + " = ?";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, u.getUsername());
        statement.setString(2, u.getEncodedPassword());
        statement.setLong(3, u.getID());
        return statement;
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection conn, User u) throws SQLException {
        String sql = "INSERT INTO " + getTablename() + " (Username, Password) VALUES(?,?)";

        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, u.getUsername());
        statement.setString(2, u.getEncodedPassword());
        return  statement;
    }

    // TODO - to be tested
    @Override
    public User getUserByUsername(String username) throws DaoSysException {
        java.util.List<User> userList = super.list();
        for(User user : userList){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}
