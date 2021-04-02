package at.htlpinkafeld.restful.dao;

import at.htlpinkafeld.restful.exception.DaoNotFoundException;
import at.htlpinkafeld.restful.exception.DaoResourceAlreadyExistsException;
import at.htlpinkafeld.restful.exception.DaoSysException;
import at.htlpinkafeld.restful.pojo.Identifiable;

public interface Crud<T extends Identifiable> {
    void create(T t) throws DaoSysException, DaoResourceAlreadyExistsException;
    T read(int id) throws DaoSysException, DaoNotFoundException;
    void update(T t) throws DaoSysException, DaoNotFoundException;
    void delete(T t) throws DaoSysException, DaoNotFoundException;
    java.util.List<T> list() throws DaoSysException;
}
