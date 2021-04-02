package at.htlpinkafeld.restful.dao;

import at.htlpinkafeld.restful.exception.DaoNotFoundException;
import at.htlpinkafeld.restful.exception.DaoSysException;
import at.htlpinkafeld.restful.pojo.Event;
import at.htlpinkafeld.restful.pojo.User;

public interface EventDao {
    java.util.List<Event> list(User user) throws DaoSysException;
    void create(User user, Event event) throws DaoSysException, IllegalArgumentException;
    Event read(User user, int id) throws DaoNotFoundException, DaoSysException;
    void update(User user, Event event) throws DaoNotFoundException, DaoSysException;
    void delete(User user, Event event) throws DaoNotFoundException, DaoSysException;
}
