package ua.epam.travelagencyms.model.dao.mysql;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.dao.UserDAO;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.entities.user.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.*;

class MysqlUserDAOTest {

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
        MysqlLoyaltyProgramDAO mysqlLoyaltyProgramDAO = new MysqlLoyaltyProgramDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, mysqlLoyaltyProgramDAO::get);
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
            assertEquals(getTestUser(), users.get(ZERO));
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
            List<User> users = userDAO.getSorted(QUERY);
            assertEquals(ONE, users.size());
            assertEquals(getTestUser(), users.get(ZERO));
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
            List<User> users = userDAO.getSorted(QUERY);
            assertEquals(ZERO, users.size());
        }
    }

    @Test
    void testSqlExceptionGetAllSorted() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.getSorted(QUERY));
    }

    @Test
    void testGetNumberOfRecords() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(NUMBER_OF_RECORDS)).thenReturn(ONE_HUNDRED);
            int records = userDAO.getNumberOfRecords(FILTER);
            assertEquals(ONE_HUNDRED, records);
        }
    }

    @Test
    void testSqlExceptionGetNumberOfRecords() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.getNumberOfRecords(FILTER));
    }

    @Test
    void testGetVerificationCode() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getString(VERIFICATION_CODE)).thenReturn(VERIFICATION_CODE_VALUE);
            String verificationCode = userDAO.getVerificationCode(ID_VALUE);
            assertEquals(VERIFICATION_CODE_VALUE, verificationCode);
        }
    }

    @Test
    void testSqlExceptionGetVerificationCode() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.getVerificationCode(ID_VALUE));
    }

    @Test
    void testDAOExceptionGetVerificationCode() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            assertThrows(DAOException.class, () -> userDAO.getVerificationCode(ID_VALUE));
        }
    }

    @Test
    void testGetIsBlocked() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getByte(IS_BLOCKED)).thenReturn((byte) (IS_BLOCKED_VALUE ? 1 : 0));
            boolean isBlocked = userDAO.isBlocked(ID_VALUE);
            assertEquals(IS_BLOCKED_VALUE, isBlocked);
        }
    }

    @Test
    void testDAOExceptionGetIsBlocked() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            assertThrows(DAOException.class, () -> userDAO.isBlocked(ID_VALUE));
        }
    }

    @Test
    void testSqlExceptionIsBlocked() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.isBlocked(ID_VALUE));
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
            assertDoesNotThrow(() -> userDAO.setUserRole(ID_VALUE, Role.USER));
        }
    }

    @Test
    void testSqlExceptionSetUserRole() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.setUserRole(ID_VALUE, Role.USER));
    }

    @Test
    void testSetUserAvatar() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.setAvatar(ID_VALUE, AVATAR_VALUE));
        }
    }

    @Test
    void testSqlExceptionSetUserAvatar() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.setAvatar(ID_VALUE, AVATAR_VALUE));
    }

    @Test
    void testSetUserDiscount() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.setDiscount(DISCOUNT_VALUE, ID_VALUE));
        }
    }

    @Test
    void testSqlExceptionSetUserDiscount() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.setDiscount(DISCOUNT_VALUE, ID_VALUE));
    }

    @Test
    void testSetUserBlocked() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.setStatus(ID_VALUE, (byte) (IS_BLOCKED_VALUE ? 1 : 0)));
        }
    }

    @Test
    void testSqlExceptionSetUserBlocked() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.setStatus(ID_VALUE, (byte) (IS_BLOCKED_VALUE ? 1 : 0)));
    }

    @Test
    void testSetUserEmailVerified() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.setEmailVerified(ID_VALUE));
        }
    }

    @Test
    void testSqlExceptionSetUserEmailVerified() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.setEmailVerified(ID_VALUE));
    }

    @Test
    void testSetUserVerificationCode() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.setVerificationCode(ID_VALUE, VERIFICATION_CODE_VALUE));
        }
    }

    @Test
    void testSqlExceptionSetUserVerificationCode() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.setVerificationCode(ID_VALUE, VERIFICATION_CODE_VALUE));
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
        when(resultSet.getString(PASSWORD)).thenReturn(PASSWORD_VALUE);
        when(resultSet.getString(NAME)).thenReturn(NAME_VALUE);
        when(resultSet.getString(SURNAME)).thenReturn(SURNAME_VALUE);
        when(resultSet.getBytes(AVATAR)).thenReturn(AVATAR_VALUE);
        when(resultSet.getInt(DISCOUNT)).thenReturn(DISCOUNT_VALUE);
        when(resultSet.getByte(IS_BLOCKED)).thenReturn((byte) (IS_BLOCKED_VALUE ? 1 : 0));
        when(resultSet.getByte(IS_EMAIL_VERIFIED)).thenReturn((byte) (IS_EMAIL_VERIFIED_VALUE ? 1 : 0));
        when(resultSet.getString(VERIFICATION_CODE)).thenReturn(VERIFICATION_CODE_VALUE);
        when(resultSet.getInt(ROLE_ID)).thenReturn(ROLE_ID_VALUE);
    }

}