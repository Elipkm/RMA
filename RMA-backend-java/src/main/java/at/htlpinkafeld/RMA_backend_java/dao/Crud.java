package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.Identifiable;

import java.util.List;

public interface Crud<T extends Identifiable> {
    void create(T t) throws DaoSysException;
    T read(int id) throws DaoSysException;
    void update(T t) throws DaoSysException;
    void delete(T t) throws DaoSysException;
    List<T> list() throws DaoSysException;
}
