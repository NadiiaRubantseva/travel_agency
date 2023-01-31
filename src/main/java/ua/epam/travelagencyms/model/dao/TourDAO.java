package ua.epam.travelagencyms.model.dao;

import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.entities.tour.Tour;

import java.util.List;
import java.util.Optional;

public interface TourDAO extends EntityDAO<Tour>{
    Optional<Tour> getByTitle(String title) throws DAOException;
    void updateTitle(Tour tour) throws DAOException;
    List<Tour> getSorted(String query) throws DAOException;
    int getNumberOfRecords(String filter) throws DAOException;
    boolean createImageContent(byte[] image, int tourId) throws DAOException;
    byte[] getImage(long id) throws DAOException;




}
