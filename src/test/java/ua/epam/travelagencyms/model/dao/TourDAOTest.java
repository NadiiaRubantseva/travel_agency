package ua.epam.travelagencyms.model.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.entities.tour.Tour;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ua.epam.travelagencyms.Constants.*;
import static ua.epam.travelagencyms.model.dao.DAOTestUtils.*;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.tourQueryBuilder;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.userQueryBuilder;

public class TourDAOTest {

    @BeforeEach
    void clearDB() throws FileNotFoundException, SQLException {
        createEmptyDB();
    }

    @Test
    void testAdd() {
        assertDoesNotThrow(() -> tourDAO.add(getTestTour()));
    }

    @Test
    void testTwoTours() throws DAOException {
        tourDAO.add(getTestTour());
        Tour testTour2 = getTestTour();
        testTour2.setTitle(ANOTHER_TITLE);
        assertDoesNotThrow(() -> tourDAO.add(testTour2));
    }

    @Test
    void testAddTwice() throws DAOException {
        tourDAO.add(getTestTour());
        DAOException exception = assertThrows((DAOException.class), () -> tourDAO.add(getTestTour()));
        assertTrue(exception.getMessage().contains(DUPLICATE));
    }

    @Test
    void testGetById() throws DAOException {
        tourDAO.add(getTestTour());

        Tour resultTour = tourDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultTour);
        Assertions.assertEquals(resultTour, getTestTour());
    }

    @Test
    void testGetByIdNoUser() throws DAOException {
        assertNull(tourDAO.getById(ID_VALUE).orElse(null));
    }

    @Test
    void testGetByTitle() throws DAOException {
        tourDAO.add(getTestTour());

        Tour resultTour = tourDAO.getByTitle(TITLE).orElse(null);
        assertNotNull(resultTour);
        Assertions.assertEquals(resultTour, getTestTour());
    }

    @Test
    void testGetByTitleNoTour() throws DAOException {
        assertNull(tourDAO.getByTitle(TITLE).orElse(null));
    }

    @Test
    void testGetAll() throws DAOException {
        tourDAO.add(getTestTour());

        List<Tour> tours = tourDAO.getAll();
        assertTrue(tours.contains(getTestTour()));
        assertEquals(ONE, tours.size());
    }

    @Test
    void testGetAllMoreTours() throws DAOException {
        tourDAO.add(getTestTour());
        Tour testTour2 = getTestTour();
        testTour2.setTitle(ANOTHER_TITLE);
        tourDAO.add(testTour2);
        Tour testTour3 = getTestTour();
        testTour3.setTitle(ANOTHER_TITLE + ANOTHER_TITLE);
        tourDAO.add(testTour3);

        List<Tour> tours = tourDAO.getAll();
        assertTrue(tours.contains(getTestTour()));
        assertEquals(THREE, tours.size());
    }

    @Test
    void testUpdate() throws DAOException {
        tourDAO.add(getTestTour());
        assertDoesNotThrow(() -> tourDAO.update(getTestTour()));
    }

    @Test
    void testUpdateCheckUpdated() throws DAOException {
        tourDAO.add(getTestTour());

        Tour testTour = getTestTour();
        testTour.setTitle(ANOTHER_TITLE);
        testTour.setPersons(ANOTHER_PERSONS);
        tourDAO.update(testTour);

        Tour resultTour = tourDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultTour);
        assertEquals(resultTour.getTitle(), resultTour.getTitle());
        assertEquals(resultTour.getPersons(), resultTour.getPersons());
    }

    @Test
    void testUpdateDuplicateTitle() throws DAOException {
        tourDAO.add(getTestTour());

        Tour testTour2 = getTestTour();
        testTour2.setId(2);
        testTour2.setTitle(ANOTHER_TITLE);
        tourDAO.add(testTour2);

        testTour2.setTitle(TITLE);
        DAOException exception = assertThrows((DAOException.class), () -> tourDAO.update(testTour2));
        assertTrue(exception.getMessage().contains(DUPLICATE));
    }

    @Test
    void testDelete() throws DAOException {
        tourDAO.add(getTestTour());
        assertDoesNotThrow(() -> tourDAO.delete(ID_VALUE));
        List<Tour> tours = tourDAO.getAll();
        assertEquals(ZERO, tours.size());
    }

    @Test
    void testDeleteNoUser() {
        assertDoesNotThrow(() -> tourDAO.delete(ID_VALUE));
    }

    @Test
    void testUpdateTitle() throws DAOException {
        Tour testTour = getTestTour();
        tourDAO.add(testTour);

        testTour.setTitle(ANOTHER_TITLE);
        tourDAO.updateTitle(testTour);

        testTour = tourDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(testTour);
        assertEquals(ANOTHER_TITLE, testTour.getTitle());
    }


    @Test
    void testGetAllSorted() throws DAOException {
        tourDAO.add(getTestTour());
        List<Tour> sorted = tourDAO.getSorted(userQueryBuilder().getQuery());
        assertFalse(sorted.isEmpty());
        assertEquals(ONE, sorted.size());
    }

    @Test
    void testGetAllSortedByTitle() throws DAOException {
        List<Tour> tours = getRandomTours();
        for (int i = 0; i < 5; i++) {
            tourDAO.add(tours.get(i));
        }
        List<Tour> sorted = tourDAO.getSorted(tourQueryBuilder().setSortField(TITLE_FIELD).getQuery());
        assertIterableEquals(tours, sorted);
    }

    @Test
    void testGetAllSortedByTitleDesc() throws DAOException {
        List<Tour> tours = getRandomTours();
        for (int i = 0; i < 5; i++) {
            tourDAO.add(tours.get(i));
        }
        tours.sort(Comparator.comparing(Tour::getTitle).reversed());
        String query = tourQueryBuilder().setSortField(TITLE_FIELD).setOrder(DESC).getQuery();
        List<Tour> sorted = tourDAO.getSorted(query);
        assertIterableEquals(tours, sorted);
    }

    @Test
    void testGetAllSortedByTitlePagination() throws DAOException {
        List<Tour> tours = getRandomTours();
        for (int i = 0; i < 5; i++) {
            tourDAO.add(tours.get(i));
        }
        tours = tours.stream()
                .sorted(Comparator.comparing(Tour::getPersons).reversed())
                .limit(THREE)
                .collect(Collectors.toList());
        String query = tourQueryBuilder()
                .setSortField(PERSONS_FIELD)
                .setLimits("0", "3")
                .setOrder(DESC)
                .getQuery();
        List<Tour> sorted = tourDAO.getSorted(query);
        assertIterableEquals(tours, sorted);
    }

    @Test
    void testGetAllSortedByTitlePaginationOffsetThree() throws DAOException {
        List<Tour> tours = getRandomTours();
        for (int i = 0; i < 5; i++) {
            tourDAO.add(tours.get(i));
        }
        tours = tours.stream()
                .sorted(Comparator.comparing(Tour::getPersons))
                .skip(THREE)
                .limit(THREE)
                .collect(Collectors.toList());
        String query = tourQueryBuilder()
                .setSortField(PERSONS_FIELD)
                .setLimits("3", "3")
                .getQuery();
        List<Tour> sorted = tourDAO.getSorted(query);
        assertIterableEquals(tours, sorted);
    }

    private List<Tour> getRandomTours() {
        List<Tour> tours = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tours.add(getRandomTour(i));
        }
        return tours;
    }

    private Tour getRandomTour(int i) {
        Tour tour = getTestTour();
        tour.setId(i + 1);
        tour.setTitle(tour.getTitle() + i);
        tour.setPersons(tour.getPersons() + new Random().nextInt(100));
        tour.setPrice(tour.getPrice() + new Random().nextInt(100));
        tour.setHot((byte) (tour.getHot() + new Random().nextInt(1)));
        return tour;
    }
}
