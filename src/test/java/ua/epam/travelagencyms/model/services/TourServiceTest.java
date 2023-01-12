package ua.epam.travelagencyms.model.services;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.dao.TourDAO;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.services.implementation.TourServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.Constants.*;
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
    void testGetAllTours() throws DAOException, ServiceException {
        List<Tour> tours = new ArrayList<>();
        List<TourDTO> tourDTOS = new ArrayList<>();
        tours.add(getTestTour());
        tourDTOS.add(getTestTourDTO());
        when(tourDAO.getAll()).thenReturn(tours);
        assertIterableEquals(tourDTOS, tourService.getAll());
    }

    @Test
    void testCorrectAddTour() throws DAOException, ServiceException {
        doNothing().when(tourDAO).add(isA(Tour.class));
        TourDTO tourDTO = getTestTourDTO();
        assertDoesNotThrow(() -> tourService.add(tourDTO));
    }

    @Test
    void testEditTour() throws DAOException {
        doNothing().when(tourDAO).update(isA(Tour.class));
        assertDoesNotThrow(() -> tourService.update(getTestTourDTO()));
    }

    @Test
    void testDeleteTour() throws DAOException {
        doNothing().when(tourDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> tourService.delete(String.valueOf(ONE)));
    }

    @Test
    void testSearchTour() throws DAOException, ServiceException {
        when(tourDAO.getByTitle(TITLE)).thenReturn(Optional.of(getTestTour()));
        assertEquals(getTestTourDTO(), tourService.getByTitle(TITLE));
    }

    @Test
    void testSearchNoTour() throws DAOException {
        when(tourDAO.getByTitle(TITLE)).thenReturn(Optional.empty());
        assertThrows(NoSuchTourException.class,() -> tourService.getByTitle(TITLE));
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
    void testNumberOfRecords() throws DAOException, ServiceException {
        String filter = tourQueryBuilder().getRecordQuery();
        when(tourDAO.getNumberOfRecords(filter)).thenReturn(1);
        assertEquals(1, tourService.getNumberOfRecords(filter));
    }

    private Tour getTestTour() {
        return Tour.builder()
                .title(TITLE)
                .persons(PERSONS)
                .price(PRICE)
                .hot((byte) 0)
                .typeId(1)
                .hotelId(2)
                .build();
    }

    private TourDTO getTestTourDTO() {
        return TourDTO.builder()
                .id(ONE)
                .title(TITLE)
                .persons(PERSONS)
                .price(PRICE)
                .hot(HOT)
                .type(TYPE_TOUR)
                .hotel(HOTEL_TOUR)
                .build();
    }
}
