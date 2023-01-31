package ua.epam.travelagencyms.model.services;

import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;

import java.util.List;

public interface TourService extends Service<TourDTO>{

    void add(TourDTO tourDTO) throws ServiceException;

    TourDTO getByTitle(String title) throws ServiceException;

    List<TourDTO> getSortedTours(String query) throws ServiceException;

    int getNumberOfRecords(String filter) throws ServiceException;

    boolean createImage(byte[] image, String tourId) throws ServiceException;

    byte[] getImage(String id) throws ServiceException;

}
