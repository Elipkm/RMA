package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.exception.DaoNotFoundException;
import at.htlpinkafeld.RMA_backend_java.pojo.Event;
import at.htlpinkafeld.RMA_backend_java.pojo.User;

public interface EventDao {
    java.util.List<Event> list(User user);
    void create(User user, Event event);
    Event read(User user, int id) throws DaoNotFoundException;
    void update(User user, Event event) throws DaoNotFoundException;
    void delete(User user, Event event) throws DaoNotFoundException;
}
