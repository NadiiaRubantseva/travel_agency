package ua.epam.travelagencyms.model.services;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.dao.TourDAO;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.services.implementation.TourServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.TestUtils.TITLE_VALUE;
import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_TITLE;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.tourQueryBuilder;


public class TourServiceTest {
    private final TourDAO tourDAO = mock(TourDAO.class);
    private final TourService tourService = new TourServiceImpl(tourDAO);
    private final Long ONE = 1L;

    @Test
    void testGetTourById() throws DAOException, ServiceException {
        when(tourDAO.getById(ONE)).thenReturn(Optional.of(getTestTour()));
        assertEquals(getTestTourDTO(), tourService.getById(String.valueOf(ONE)));
    }

    @Test
    void testGetNoTourById() throws DAOException {
        when(tourDAO.getById(ONE)).thenReturn(Optional.empty());
        assertThrows(NoSuchTourException.class,() -> tourService.getById(String.valueOf(ONE)));
    }

    @Test
    void testSqlExceptionGetById() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(tourDAO).getById(isA(long.class));
        ServiceException e = assertThrows(ServiceException.class,
                () -> tourService.getById(ID_STRING_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void NoSuchTourExceptionForGetById() {
        String tourId = "invalid_id";
        assertThrows(NoSuchTourException.class, () -> tourService.getById(tourId));
    }

    @Test
    void testGetAllTours() throws DAOException, ServiceException {
        List<Tour> tours = new ArrayList<>();
        List<TourDTO> tourDTOS = new ArrayList<>();
        tours.add(getTestTour());
        tourDTOS.add(getTestTourDTO());
        when(tourDAO.getAll()).thenReturn(tours);
        assertIterableEquals(tourDTOS, tourService.getAll());
    }

    @Test
    void testSqlExceptionGetAll() throws DAOException {
        when(tourDAO.getAll()).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, tourService::getAll);
    }

    @Test
    void testCorrectAddTour() throws DAOException {
        doNothing().when(tourDAO).add(isA(Tour.class));
        TourDTO tourDTO = getTestTourDTO();
        assertDoesNotThrow(() -> tourService.add(tourDTO));
    }

    @Test
    void testAddTourWithAlreadyExistingTitle() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(tourDAO).add(isA(Tour.class));
        TourDTO tourDTO = getTestTourDTO();
        DuplicateTitleException e = assertThrows(DuplicateTitleException.class ,
                () -> tourService.add(tourDTO));
        assertEquals(DUPLICATE_TITLE, e.getMessage());

    }

    @Test
    void testSQLExceptionAddTour() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(tourDAO).add(isA(Tour.class));
        TourDTO tourDTO = getTestTourDTO();
        ServiceException e = assertThrows(ServiceException.class, () -> tourService.add(tourDTO));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testEditTour() throws DAOException {
        doNothing().when(tourDAO).update(isA(Tour.class));
        assertDoesNotThrow(() -> tourService.update(getTestTourDTO()));
    }

    @Test
    void testEditTourWithAlreadyExistingTitle() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(tourDAO).update(isA(Tour.class));
        TourDTO tourDTO = getTestTourDTO();
        DuplicateTitleException e = assertThrows(DuplicateTitleException.class ,
                () -> tourService.update(tourDTO));
        assertEquals(DUPLICATE_TITLE, e.getMessage());

    }

    @Test
    void testSQLErrorUpdateTour() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(tourDAO).update(isA(Tour.class));
        TourDTO tourDTO = getTestTourDTO();
        ServiceException e = assertThrows(ServiceException.class, () -> tourService.update(tourDTO));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testDeleteTour() throws DAOException {
        doNothing().when(tourDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> tourService.delete(String.valueOf(ONE)));
    }

    @Test
    void testSQLExceptionDeleteTour() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(tourDAO).delete(isA(long.class));
        ServiceException e = assertThrows(ServiceException.class, () -> tourService.delete(ID_STRING_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testSearchTour() throws DAOException, ServiceException {
        when(tourDAO.getByTitle(TITLE_VALUE)).thenReturn(Optional.of(getTestTour()));
        assertEquals(getTestTourDTO(), tourService.getByTitle(TITLE_VALUE));
    }

    @Test
    void testSearchNoTour() throws DAOException {
        when(tourDAO.getByTitle(TITLE_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchTourException.class,() -> tourService.getByTitle(TITLE_VALUE));
    }

    @Test
    void testSQLExceptionGetByTitle() throws DAOException {
        when(tourDAO.getByTitle(isA(String.class))).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> tourService.getByTitle(TITLE_VALUE));
    }

    @Test
    void testViewSortedTours() throws DAOException, ServiceException {
        List<Tour> tours = new ArrayList<>();
        List<TourDTO> tourDTOs = new ArrayList<>();
        tours.add(getTestTour());
        tourDTOs.add(getTestTourDTO());
        String query = tourQueryBuilder().getQuery();
        when(tourDAO.getSorted(query)).thenReturn(tours);
        assertIterableEquals(tourDTOs, tourService.getSortedTours(query));
    }

    @Test
    void testSqlErrorGetSortedTours() throws DAOException {
        when(tourDAO.getSorted(QUERY)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> tourService.getSortedTours(QUERY));
    }

    @Test
    void testNumberOfRecords() throws DAOException, ServiceException {
        String filter = tourQueryBuilder().getRecordQuery();
        when(tourDAO.getNumberOfRecords(filter)).thenReturn(1);
        assertEquals(1, tourService.getNumberOfRecords(filter));
    }

    @Test
    void testSQLErrorNumberOfRecords() throws DAOException {
        when(tourDAO.getNumberOfRecords(FILTER)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> tourService.getNumberOfRecords(FILTER));
    }

    @Test
    void testServiceException() {
        assertNotEquals(new ServiceException(), new ServiceException());
    }

    @Test
    void updateImage_shouldUpdateImage() throws DAOException {
        assertDoesNotThrow(() -> tourService.updateImage(IMAGE_VALUE, ID_STRING_VALUE));
        verify(tourDAO).updateImage(IMAGE_VALUE, ID_VALUE);
    }

    @Test
    void updateImage_shouldUpdateImage2() throws DAOException, ServiceException {
        doNothing().when(tourDAO).updateImage(isA(byte[].class), isA(long.class));
        tourService.updateImage(IMAGE_VALUE, ID_STRING_VALUE);
        verify(tourDAO).updateImage(IMAGE_VALUE, ID_VALUE);
    }

    @Test
    void updateImage_shouldThrowNumberFormatException() {
        String tourId = "invalid_id";
        assertThrows(NumberFormatException.class, () -> tourService.updateImage(IMAGE_VALUE, tourId));
    }

    @Test
    void testUpdateImageSQLException() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(tourDAO).updateImage(isA(byte[].class), isA(long.class));
        ServiceException e = assertThrows(ServiceException.class, () -> tourService.updateImage(IMAGE_VALUE, ID_STRING_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testGetImage() throws DAOException, ServiceException {
        when(tourDAO.getImage(ID_VALUE)).thenReturn(IMAGE_VALUE);
        assertEquals(IMAGE_VALUE, tourService.getImage(ID_STRING_VALUE));
    }

    @Test
    void testSQLErrorGetImage() throws DAOException {
        when(tourDAO.getImage(ID_VALUE)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> tourService.getImage(ID_STRING_VALUE));
    }
}
