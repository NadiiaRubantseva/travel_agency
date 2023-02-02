package ua.epam.travelagencyms.model.dao;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.dao.mysql.MysqlTourDAO;
import ua.epam.travelagencyms.model.entities.tour.Tour;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.model.dao.DAOTestUtils.getTestTour;

public class TourDAOTest {

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
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("title")).thenReturn("title");
        when(resultSet.getInt("persons")).thenReturn(2);
        when(resultSet.getDouble("price")).thenReturn(100.0);
        when(resultSet.getByte("hot")).thenReturn((byte) 0);
        when(resultSet.getInt("type_id")).thenReturn(3);
        when(resultSet.getInt("hotel_id")).thenReturn(2);
        when(resultSet.getBytes("image")).thenReturn(new byte[]{1,2,3});
    }

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
            Tour resultTour = tourDAO.getById(1L).orElse(null);
            assertNotNull(resultTour);
            assertEquals(getTestTour(), resultTour);
        }
    }

}
