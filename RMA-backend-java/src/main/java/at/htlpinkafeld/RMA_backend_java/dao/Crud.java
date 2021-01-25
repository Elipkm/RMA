package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.exception.DAOSysException;

import java.util.List;

public interface Crud<T extends Identifiable> {
    void create(T t) throws DAOSysException;
    T read(int id) throws DAOSysException;
    void update(T t) throws DAOSysException;
    void delete(T t) throws DAOSysException;
    List<T> list() throws DAOSysException;
}
