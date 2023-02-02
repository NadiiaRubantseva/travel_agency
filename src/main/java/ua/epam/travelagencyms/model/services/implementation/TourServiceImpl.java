package ua.epam.travelagencyms.model.services.implementation;

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

public class TourServiceImpl implements TourService {

    private final TourDAO tourDAO;

    public TourServiceImpl(TourDAO tourDAO) {
        this.tourDAO = tourDAO;
    }
    @Override
    public TourDTO getById(String tourIdString) throws ServiceException {
        TourDTO tourDTO;
        long tourId = getTourId(tourIdString);
        try {
            Tour tour = tourDAO.getById(tourId).orElseThrow(NoSuchTourException::new);
            tourDTO = convertTourToDTO(tour);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tourDTO;
    }


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

    @Override
    public void update(TourDTO tourDTO) throws ServiceException {
        validateTour(tourDTO);
        Tour tour = convertDTOToTour(tourDTO);
        try {
            tourDAO.update(tour);
        } catch (DAOException e) {
            checkExceptionType(e);
        }
    }

    @Override
    public void delete(String tourIdString) throws ServiceException {
        long tourId = getTourId(tourIdString);
        try {
            tourDAO.delete(tourId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void add(TourDTO tourDTO) throws ServiceException {
        validateTour(tourDTO);
        Tour tour = convertDTOToTour(tourDTO);
        try {
            tourDAO.add(tour);
        } catch (DAOException e) {
            throw new ServiceException();
        }
    }

    @Override
    public TourDTO getByTitle(String title) throws ServiceException {
//        validateTitle(title);
        TourDTO tourDTO;
        try {
            Tour tour = tourDAO.getByTitle(title).orElseThrow(NoSuchTourException::new);
            tourDTO = convertTourToDTO(tour);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tourDTO;
    }

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

    @Override
    public void createImage(byte[] image, String tourId) throws ServiceException {
        int id = Integer.parseInt(tourId);
        try {
            tourDAO.createImageContent(image, id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public byte[] getImage(String tourId) throws ServiceException {
        int id = Integer.parseInt(tourId);
        byte[] image;
        try {
            image = tourDAO.getImage(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return image;
    }

    public static long getTourId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchUserException());
    }
    private static long checkId(String idString, ServiceException exception) throws ServiceException {
        long eventId;
        try {
            eventId = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw exception;
        }
        return eventId;
    }

    private void validateTour(TourDTO tourDTO) throws IncorrectFormatException {
        validateComplexName(tourDTO.getTitle(), ENTER_CORRECT_TITLE);
//        validatePersons(tourDTO.getPersons(), ENTER_CORRECT_NUMBERS);
//        validatePrice(tourDTO.getPrice(), ENTER_CORRECT_NUMBERS);
    }

    private void checkExceptionType(DAOException e) throws ServiceException {
        if (e.getMessage().contains("duplicate")) {
            throw new DuplicateTitleException();
        } else {
            throw new ServiceException(e);
        }
    }
}
