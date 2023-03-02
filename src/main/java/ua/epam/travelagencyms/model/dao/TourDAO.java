package ua.epam.travelagencyms.model.dao;

import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.entities.tour.Tour;

import java.util.List;
import java.util.Optional;

/**
 * Tour DAO interface.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public interface TourDAO extends EntityDAO<Tour>{

    /**
     * Obtains instance of tour from database
     * @param title - value of title
     * @return Optional.ofNullable - tour is null if there is no tour
     * @throws DAOException is wrapper for SQLException
     */
    Optional<Tour> getByTitle(String title) throws DAOException;

    /**
     * Obtains sorted and limited list of tours from database
     * @param query should contain filters, order, limits for pagination
     * @return events list that matches demands
     * @throws DAOException is wrapper for SQLException
     */
    List<Tour> getSorted(String query) throws DAOException;

    /**
     * Obtains number of all records matching filter
     * @param filter should contain 'where' to specify query
     * @return number of records
     * @throws DAOException is wrapper for SQLException
     */
    int getNumberOfRecords(String filter) throws DAOException;

    /**
     * Saves image in tour table
     * @param image - value of uploaded image
     * @param tourId - value of tourID
     * @throws DAOException is wrapper for SQLException
     */
    void updateImage(byte[] image, long tourId) throws DAOException;

    /**
     * Retrieves image from tour table
     * @param id- value of tourID
     * @throws DAOException is wrapper for SQLException
     */
    String getImage(long id) throws DAOException;

}
