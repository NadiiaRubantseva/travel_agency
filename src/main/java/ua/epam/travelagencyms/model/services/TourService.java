package ua.epam.travelagencyms.model.services;

import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;

import java.util.List;

public interface TourService extends Service<TourDTO>{

    /**
     * Calls DAO to add relevant entity
     * @param tourDTO - The {@link TourDTO} object that contains the tour information to be added as entity to database
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void add(TourDTO tourDTO) throws ServiceException;

    /**
     * Calls DAO to retrieve a tour with given title
     * @param title - tour title
     * @return TourDTO - TourDTO instance
     * @throws ServiceException - may wrap DAOException or be thrown NoSuchTourException
     */
    TourDTO getByTitle(String title) throws ServiceException;

    /**
     * Calls DAO to get sorted, filtered and limited list of DTOs. Converts Tour to TourDTOs
     * @param query - to obtain necessary DTOs
     * @return List of TourDTOs that match demands
     * @throws ServiceException - may wrap DAOException
     */
    List<TourDTO> getSortedTours(String query) throws ServiceException;

    /**
     * Calls DAO to get number of all records that match filter
     * @param filter - conditions for such Tours
     * @return number of records that match demands
     * @throws ServiceException - may wrap DAOException
     */
    int getNumberOfRecords(String filter) throws ServiceException;

    /**
     * Calls DAO to save new tour image
     * @param image - new tour image
     * @param tourId - tour id.
     * @throws ServiceException - may wrap DAOException
     */
    void updateImage(byte[] image, String tourId) throws ServiceException;

    /**
     * Calls DAO to retrieve tour image
     * @param id - tour id.
     * @return image - image in byte array representation.
     * @throws ServiceException - may wrap DAOException
     */
    byte[] getImage(String id) throws ServiceException;

}
