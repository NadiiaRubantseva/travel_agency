package ua.epam.travelagencyms.model.dao;

import ua.epam.travelagencyms.exceptions.DAOException;

import java.util.List;
import java.util.Optional;

public interface EntityDAO<T> {

    void add(T t) throws DAOException;

    Optional<T> getById(long id) throws DAOException;

    List<T> getAll() throws DAOException;

    void update(T t) throws DAOException;

    void delete(long id) throws DAOException;
}
