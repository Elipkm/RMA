package at.htlpinkafeld.RMA_backend_java.dao;

import java.util.List;

public interface Crud<T extends Identifiable> {
    void create(T t);
    T read(int id);
    void update(T t);
    void delete(T t);
    List<T> list();
}
