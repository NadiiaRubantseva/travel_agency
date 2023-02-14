package ua.epam.travelagencyms.model.services.implementation;

import lombok.RequiredArgsConstructor;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.dao.TourDAO;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.services.TourService;

import java.util.ArrayList;
import java.util.List;

import static ua.epam.travelagencyms.exceptions.constants.Message.*;
import static ua.epam.travelagencyms.utils.ConvertorUtil.*;
import static ua.epam.travelagencyms.utils.ValidatorUtil.*;

@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    /** Contains tourDAO field to work with TourDAO */
    private final TourDAO tourDAO;

    /**
     * Obtains instance of Tour from DAO by id. Checks if id valid. Converts Tour to TourDTO
     *
     * @param tourIdString - id as a String to validate and convert to long
     * @return TourDTO instance
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchUserException
     */
    @Override
    public TourDTO getById(String tourIdString) throws ServiceException {
        TourDTO tourDTO;
        try {
            long tourId = getTourId(tourIdString);
            Tour tour = tourDAO.getById(tourId).orElseThrow(NoSuchTourException::new);
            tourDTO = convertTourToDTO(tour);
        } catch (DAOException  | NumberFormatException e) {
            throw new ServiceException(e);
        }
        return tourDTO;
    }

    /**
     * Obtains list of all instances of Tour from DAO. Converts Tours to TourDTOs
     *
     * @return List of TourDTOs
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public List<TourDTO> getAll() throws ServiceException {
        List<TourDTO> tourDTOS = new ArrayList<>();
        try {
            List<Tour> tours = tourDAO.getAll();
            tours.forEach(tour -> tourDTOS.add(convertTourToDTO(tour)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tourDTOS;
    }

    /**
     * Updates Tour information in database. Validates TourDTO. Converts TourDTO to Tour
     *
     * @param tourDTO - TourDTO that instance
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException or
     *                          DuplicateTitleException
     */
    @Override
    public void update(TourDTO tourDTO) throws ServiceException {
        validateTour(tourDTO);
        Tour tour = convertTourDTOToTour(tourDTO);
        try {
            tourDAO.update(tour);
        } catch (DAOException e) {
            checkExceptionType(e);
        }
    }

    /**
     * Deletes Tour entity from database. Validates id.
     *
     * @param tourIdString - id as a String
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchUserException
     */
    @Override
    public void delete(String tourIdString) throws ServiceException {
        long tourId = getTourId(tourIdString);
        try {
            tourDAO.delete(tourId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets TourDTO from action and calls DAO to add relevant entity. Validate tour's fields.
     * Converts TourDTO to Tour
     *
     * @param tourDTO - DTO to be added as Tour to database
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException with specific message, DuplicateTitleException.
     */
    @Override
    public void add(TourDTO tourDTO) throws ServiceException {
        validateTour(tourDTO);
        Tour tour = convertTourDTOToTour(tourDTO);
        try {
            tourDAO.add(tour);
        } catch (DAOException e) {
            checkExceptionType(e);
        }
    }

    /**
     * Obtains instance of Tour from DAO by title. Checks if id valid. Converts Tour to TourDTO
     * @param title - Tour title
     * @return TourDTO instance
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchTourException
     */
    @Override
    public TourDTO getByTitle(String title) throws ServiceException {
        TourDTO tourDTO;
        try {
            Tour tour = tourDAO.getByTitle(title).orElseThrow(NoSuchTourException::new);
            tourDTO = convertTourToDTO(tour);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tourDTO;
    }

    /**
     * Calls DAO to get sorted, filtered and limited list of DTOs. Converts Tours to TourDTOs
     * @param query - to obtain necessary DTOs
     * @return List of TourDTOs that match demands
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public List<TourDTO> getSortedTours(String query) throws ServiceException {
        List<TourDTO> tourDTOS = new ArrayList<>();
        try {
            List<Tour> tours = tourDAO.getSorted(query);
            tours.forEach(tour -> tourDTOS.add(convertTourToDTO(tour)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tourDTOS;
    }

    /**
     * Calls DAO to get number of all records match filter
     * @param filter - conditions for such Tours
     * @return number of records that match demands
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public int getNumberOfRecords(String filter) throws ServiceException {
        int records;
        try {
            records = tourDAO.getNumberOfRecords(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return records;
    }

    /**
     * Calls DAO to update Tour image. Check if id is valid.
     * @param tourId - id as a String
     * @param image - image as an array of bytes
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    @Override
    public void updateImage(byte[] image, String tourId) throws ServiceException {
        int id = Integer.parseInt(tourId);
        try {
            tourDAO.updateImage(image, id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Calls DAO to retrieve Tour image. Check if id is valid.
     * @param tourId - id as a String
     * @return image - image as an array of bytes
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    @Override
    public byte[] getImage(String tourId) throws ServiceException {
        long id = Long.parseLong(tourId);
        byte[] image;
        try {
            image = tourDAO.getImage(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return image;
    }

    private void validateTour(TourDTO tourDTO) throws IncorrectFormatException {
        validateComplexName(tourDTO.getTitle(), ENTER_CORRECT_TITLE);
    }

    private void checkExceptionType(DAOException e) throws ServiceException {
        if (e.getMessage().contains("Duplicate")) {
            throw new DuplicateTitleException();
        } else {
            throw new ServiceException(e);
        }
    }
}
