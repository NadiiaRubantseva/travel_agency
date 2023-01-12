package ua.epam.travelagencyms.model.services;

import ua.epam.travelagencyms.exceptions.ServiceException;

import java.util.List;

public interface Service<T> {

    T getById(String idString) throws ServiceException;

    List<T> getAll() throws ServiceException;

    void update(T entity) throws ServiceException;

    void delete(String idString) throws ServiceException;

}