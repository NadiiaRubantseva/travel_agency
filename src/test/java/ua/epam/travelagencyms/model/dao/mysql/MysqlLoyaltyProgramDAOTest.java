package ua.epam.travelagencyms.model.dao.mysql;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.dao.UserDAO;
import ua.epam.travelagencyms.model.entities.loyaltyProgram.LoyaltyProgram;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.*;

class MysqlLoyaltyProgramDAOTest {

    @Test
    void testUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        MysqlLoyaltyProgramDAO mysqlLoyaltyProgramDAO = new MysqlLoyaltyProgramDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> mysqlLoyaltyProgramDAO.update(getTestLoyaltyProgram()));
        }
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        MysqlLoyaltyProgramDAO mysqlLoyaltyProgramDAO = new MysqlLoyaltyProgramDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> mysqlLoyaltyProgramDAO.update(getTestLoyaltyProgram()));
    }

    @Test
    void testGet() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        MysqlLoyaltyProgramDAO mysqlLoyaltyProgramDAO = new MysqlLoyaltyProgramDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            LoyaltyProgram resultLoyaltyProgram = mysqlLoyaltyProgramDAO.get();
            assertNotNull(resultLoyaltyProgram);
            assertEquals(getTestLoyaltyProgram(), resultLoyaltyProgram);
        }
    }

    @Test
    void testSqlExceptionGet() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.getById(ID_VALUE));
    }

    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(ID)).thenReturn(ID_VALUE);
        when(resultSet.getInt(STEP)).thenReturn(STEP_VALUE);
        when(resultSet.getInt(DISCOUNT)).thenReturn(LOYALTY_PROGRAM_DISCOUNT_VALUE);
        when(resultSet.getInt(MAX_DISCOUNT)).thenReturn(MAX_DISCOUNT_VALUE);
    }

    private PreparedStatement prepareMocks(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(isA(int.class), isA(int.class));
        when(preparedStatement.execute()).thenReturn(true);
        return preparedStatement;
    }
}