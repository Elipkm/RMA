package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.exception.DaoNotFoundException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoResourceAlreadyExistsException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.Identifiable;

public interface Crud<T extends Identifiable> {
    void create(T t) throws DaoSysException, DaoResourceAlreadyExistsException;
    T read(int id) throws DaoSysException, DaoNotFoundException;
    void update(T t) throws DaoSysException, DaoNotFoundException;
    void delete(T t) throws DaoSysException, DaoNotFoundException;
    java.util.List<T> list() throws DaoSysException;
}
