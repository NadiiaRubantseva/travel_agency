package ua.epam.travelagencyms.model.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.dao.mysql.MysqlUserDAO;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.entities.user.User;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.Constants.*;
import static ua.epam.travelagencyms.model.dao.DAOTestUtils.*;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.*;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.EMAIL;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.NAME;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.PASSWORD;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.ROLE_ID;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.SURNAME;

class UserDAOTest {

    @Test
    void testAdd() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.add(getTestUser()));
        }
    }
    @Test
    void testSqlExceptionAdd() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.add(getTestUser()));
    }

    @Test
    void testGetById() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            User resultUser = userDAO.getById(ID_VALUE).orElse(null);
            assertNotNull(resultUser);
            assertEquals(getTestUser(), resultUser);
        }
    }

    @Test
    void testGetByIdAbsent() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            User resultUser = userDAO.getById(ID_VALUE).orElse(null);
            assertNull(resultUser);
        }
    }

    @Test
    void testSqlExceptionGetById() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.getById(ID_VALUE));
    }

    @Test
    void testGetByEmail() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            User resultUser = userDAO.getByEmail(EMAIL_VALUE).orElse(null);
            assertNotNull(resultUser);
            assertEquals(getTestUser(), resultUser);
        }
    }

    @Test
    void testGetByEmailAbsent() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            User resultUser = userDAO.getByEmail(EMAIL_VALUE).orElse(null);
            assertNull(resultUser);
        }
    }

    @Test
    void testSqlExceptionGetByEmail() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.getByEmail(EMAIL_VALUE));
    }

    @Test
    void testGetAll() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<User> users = userDAO.getAll();
            assertEquals(ONE, users.size());
            assertEquals(getTestUser(), users.get(0));
        }
    }

    @Test
    void testGetNoUsers() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            List<User> users = userDAO.getAll();
            assertEquals(ZERO, users.size());
        }
    }

    @Test
    void testSqlExceptionGetAll() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, userDAO::getAll);
    }

    @Test
    void testGetAllSorted() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<User> users = userDAO.getSorted("query");
            assertEquals(ONE, users.size());
            assertEquals(getTestUser(), users.get(0));
        }
    }

    @Test
    void testGetAllSortedNoUsers() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            List<User> users = userDAO.getSorted("query");
            assertEquals(ZERO, users.size());
        }
    }

    @Test
    void testSqlExceptionGetAllSorted() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.getSorted("query"));
    }

    @Test
    void testGetNumberOfRecords() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(NUMBER_OF_RECORDS)).thenReturn(100);
            int records = userDAO.getNumberOfRecords("filter");
            assertEquals(100, records);
        }
    }

    @Test
    void testSqlExceptionGetNumberOfRecords() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.getNumberOfRecords("filter"));
    }

    @Test
    void testUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.update(getTestUser()));
        }
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.update(getTestUser()));
    }

    @Test
    void testUpdatePassword() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.updatePassword(getTestUser()));
        }
    }

    @Test
    void testSqlExceptionUpdatePassword() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.updatePassword(getTestUser()));
    }

    @Test
    void testSetUserRole() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.setUserRole(EMAIL_VALUE, Role.USER));
        }
    }

    @Test
    void testSqlExceptionSetUserRole() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.setUserRole(EMAIL_VALUE, Role.USER));
    }

    @Test
    void testDelete() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.delete(ID_VALUE));
        }
    }

    @Test
    void testSqlExceptionDelete() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.delete(ID_VALUE));
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
        when(resultSet.getLong(ID)).thenReturn(ID_VALUE);
        when(resultSet.getString(EMAIL)).thenReturn(EMAIL_VALUE);
        when(resultSet.getString(NAME)).thenReturn(NAME_VALUE);
        when(resultSet.getString(SURNAME)).thenReturn(SURNAME_VALUE);
        when(resultSet.getString(PASSWORD)).thenReturn(PASSWORD_VALUE);
        when(resultSet.getInt(ROLE_ID)).thenReturn(ROLE_ID_VALUE);
    }

}