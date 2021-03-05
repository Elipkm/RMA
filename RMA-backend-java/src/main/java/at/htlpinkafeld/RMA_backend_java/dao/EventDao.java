package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.pojo.Event;
import at.htlpinkafeld.RMA_backend_java.pojo.User;

public interface EventDao extends Crud<Event> {
    java.util.List<Event> list(User user);
}
