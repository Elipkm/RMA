package at.htlpinkafeld.RMA_backend_java.dao;

import java.util.List;

public interface CRUD<T> {
    void create(T t);
    T read();
    void update(T t);
    void delete(T t);
    List<T> list();
}
