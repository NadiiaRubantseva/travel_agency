package ua.epam.travelagencyms.model.dao.mysql;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.dao.TourDAO;
import ua.epam.travelagencyms.model.entities.tour.Tour;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.*;

public class MysqlTourDAOTest {

    @Test
    void testAdd() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> tourDAO.add(getTestTour()));
        }
    }

    @Test
    void testSqlExceptionAdd() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> tourDAO.add(getTestTour()));
    }

    @Test
    void testGetById() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Tour resultTour = tourDAO.getById(ONE).orElse(null);
            assertNotNull(resultTour);
            assertEquals(getTestTour(), resultTour);
        }
    }

    @Test
    void testGetByIdAbsent() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            Tour resultTour = tourDAO.getById(ID_VALUE).orElse(null);
            assertNull(resultTour);
        }
    }

    @Test
    void testSqlExceptionGetById() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> tourDAO.getById(ID_VALUE));
    }

    @Test
    void testGetByTitle() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Tour resultTour = tourDAO.getByTitle(TITLE_VALUE).orElse(null);
            assertNotNull(resultTour);
            assertEquals(getTestTour(), resultTour);
        }
    }

    @Test
    void testGetByTitleAbsent() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            Tour resultTour = tourDAO.getByTitle(TITLE_VALUE).orElse(null);
            assertNull(resultTour);
        }
    }

    @Test
    void testSqlExceptionGetByTitle() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> tourDAO.getByTitle(EMAIL_VALUE));
    }

    @Test
    void testGetAll() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Tour> tours = tourDAO.getAll();
            assertEquals(ONE, tours.size());
            assertEquals(getTestTour(), tours.get(ZERO));
        }
    }

    @Test
    void testGetNoTours() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            List<Tour> tours = tourDAO.getAll();
            assertEquals(ZERO, tours.size());
        }
    }

    @Test
    void testSqlExceptionGetAll() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, tourDAO::getAll);
    }

    @Test
    void testGetAllSorted() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Tour> tours = tourDAO.getSorted(QUERY);
            assertEquals(ONE, tours.size());
            assertEquals(getTestTour(), tours.get(ZERO));
        }
    }

    @Test
    void testGetAllSortedNoTours() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            List<Tour> tours = tourDAO.getSorted(QUERY);
            assertEquals(ZERO, tours.size());
        }
    }

    @Test
    void testSqlExceptionGetAllSorted() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> tourDAO.getSorted(QUERY));
    }

    @Test
    void testGetNumberOfRecords() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(NUMBER_OF_RECORDS)).thenReturn(ONE_HUNDRED);
            int records = tourDAO.getNumberOfRecords(FILTER);
            assertEquals(ONE_HUNDRED, records);
        }
    }

    @Test
    void testSqlExceptionGetNumberOfRecords() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> tourDAO.getNumberOfRecords(FILTER));
    }

    @Test
    void testCreateImageContent() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> tourDAO.updateImage(IMAGE_VALUE, ID_VALUE));
        }
    }

    @Test
    void testGetImageContent() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getString(IMAGE)).thenReturn("image");
            String image = tourDAO.getImage(ID_VALUE);
            assertEquals("image", image);
        }
    }

    @Test
    void testGetNoImageContent() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            String image = tourDAO.getImage(ID_VALUE);
            assertNull(image);
        }
    }

    @Test
    void testSqlExceptionGetImageContent() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> tourDAO.getImage(ID_VALUE));
    }

    @Test
    void testSqlExceptionCreateImageContent() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> tourDAO.updateImage(IMAGE_VALUE, ID_VALUE));
    }

    @Test
    void testUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> tourDAO.update(getTestTour()));
        }
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> tourDAO.update(getTestTour()));
    }

    @Test
    void testDelete() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> tourDAO.delete(ID_VALUE));
        }
    }

    @Test
    void testSqlExceptionDelete() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TourDAO tourDAO = new MysqlTourDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> tourDAO.delete(ID_VALUE));
    }

    private PreparedStatement prepareMocks(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(isA(int.class), isA(int.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        when(preparedStatement.execute()).thenReturn(true);
        return preparedStatement;
    }

    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(ID)).thenReturn(ONE);
        when(resultSet.getString(TITLE)).thenReturn(TITLE_VALUE);
        when(resultSet.getInt(PERSONS)).thenReturn(PERSONS_VALUE);
        when(resultSet.getDouble(PRICE)).thenReturn(PRICE_VALUE);
        when(resultSet.getByte(HOT)).thenReturn(HOT_VALUE);
        when(resultSet.getBytes(IMAGE)).thenReturn(IMAGE_VALUE);
        when(resultSet.getString(DESCRIPTION)).thenReturn(DESCRIPTION_VALUE);
        when(resultSet.getInt(TYPE_ID)).thenReturn(TYPE_ID_VALUE);
        when(resultSet.getInt(HOTEL_ID)).thenReturn(HOTEL_ID_VALUE);
    }

}
