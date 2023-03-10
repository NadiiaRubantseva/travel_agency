package ua.epam.travelagencyms.model.dao.mysql;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.dao.OrderDAO;
import ua.epam.travelagencyms.model.entities.order.Order;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.user.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.DATE;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.*;

class MysqlOrderDAOTest {


    @Test
    void testAdd() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> orderDAO.add(getTestOrder()));
        }
    }

    @Test
    void testSqlExceptionAdd() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> orderDAO.add(getTestOrder()));
    }

    @Test
    void getById() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Order resultOrder = orderDAO.getById(ID_VALUE).orElse(null);

            assertNotNull(resultOrder);

            User resultUser = resultOrder.getUser();
            Tour resultTour = resultOrder.getTour();

            assertEquals(getTestOrder().getId(), resultOrder.getId());
            assertEquals(getTestOrder().getOrderStatusId(), resultOrder.getOrderStatusId());
            assertEquals(getTestOrder().getDiscount(), resultOrder.getDiscount());
            assertEquals(getTestOrder().getTotalCost(), resultOrder.getTotalCost());
            assertEquals(getTestOrder().getDate(), resultOrder.getDate());

            assertEquals(getTestUser().getId(), resultUser.getId());
            assertEquals(getTestUser().getEmail(), resultUser.getEmail());
            assertEquals(getTestUser().getName(), resultUser.getName());
            assertEquals(getTestUser().getSurname(), resultUser.getSurname());
            assertEquals(getTestTour().getId(), resultTour.getId());
            assertEquals(getTestTour().getTitle(), resultTour.getTitle());
            assertEquals(getTestTour().getPrice(), resultTour.getPrice());
        }
    }

    @Test
    void testGetByIdAbsent() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            Order resultOrder = orderDAO.getById(ID_VALUE).orElse(null);
            assertNull(resultOrder);
        }
    }

    @Test
    void testSqlExceptionGetById() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> orderDAO.getById(ID_VALUE));
    }

    @Test
    void getAll() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Order> orders = orderDAO.getAll();
            assertEquals(ONE, orders.size());
            Order resultOrder = orders.get(0);
            assertNotNull(resultOrder);

            User resultUser = resultOrder.getUser();
            Tour resultTour = resultOrder.getTour();

            assertEquals(getTestOrder().getId(), resultOrder.getId());
            assertEquals(getTestOrder().getOrderStatusId(), resultOrder.getOrderStatusId());
            assertEquals(getTestOrder().getDiscount(), resultOrder.getDiscount());
            assertEquals(getTestOrder().getTotalCost(), resultOrder.getTotalCost());
            assertEquals(getTestOrder().getDate(), resultOrder.getDate());

            assertEquals(getTestUser().getId(), resultUser.getId());
            assertEquals(getTestUser().getEmail(), resultUser.getEmail());
            assertEquals(getTestUser().getName(), resultUser.getName());
            assertEquals(getTestUser().getSurname(), resultUser.getSurname());
            assertEquals(getTestTour().getId(), resultTour.getId());
            assertEquals(getTestTour().getTitle(), resultTour.getTitle());
            assertEquals(getTestTour().getPrice(), resultTour.getPrice());

        }
    }

    @Test
    void testGetNoOrders() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            List<Order> orders = orderDAO.getAll();
            assertEquals(ZERO, orders.size());
        }
    }

    @Test
    void testSqlExceptionGetAll() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, orderDAO::getAll);
    }

    @Test
    void testGetAllSorted() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Order> orders = orderDAO.getSorted(QUERY);
            assertEquals(ONE, orders.size());

            Order resultOrder = orders.get(0);
            assertNotNull(resultOrder);

            User resultUser = resultOrder.getUser();
            Tour resultTour = resultOrder.getTour();

            assertEquals(getTestOrder().getId(), resultOrder.getId());
            assertEquals(getTestOrder().getOrderStatusId(), resultOrder.getOrderStatusId());
            assertEquals(getTestOrder().getDiscount(), resultOrder.getDiscount());
            assertEquals(getTestOrder().getTotalCost(), resultOrder.getTotalCost());
            assertEquals(getTestOrder().getDate(), resultOrder.getDate());

            assertEquals(getTestUser().getId(), resultUser.getId());
            assertEquals(getTestUser().getEmail(), resultUser.getEmail());
            assertEquals(getTestUser().getName(), resultUser.getName());
            assertEquals(getTestUser().getSurname(), resultUser.getSurname());
            assertEquals(getTestTour().getId(), resultTour.getId());
            assertEquals(getTestTour().getTitle(), resultTour.getTitle());
            assertEquals(getTestTour().getPrice(), resultTour.getPrice());
        }
    }

    @Test
    void testGetSortedNoOrders() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            List<Order> orders = orderDAO.getSorted(QUERY);
            assertEquals(ZERO, orders.size());
        }
    }

    @Test
    void testSqlExceptionGetAllSorted() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> orderDAO.getSorted(QUERY));
    }

    @Test
    void testGetNumberOfRecords() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(NUMBER_OF_RECORDS)).thenReturn(ONE_HUNDRED);
            int records = orderDAO.getNumberOfRecords(FILTER);
            assertEquals(ONE_HUNDRED, records);
        }
    }

    @Test
    void testSqlExceptionGetNumberOfRecords() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> orderDAO.getNumberOfRecords(FILTER));
    }

    @Test
    void setOrderStatus() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> orderDAO.setOrderStatus(ID_VALUE, ORDER_STATUS_ID_VALUE));
        }
    }

    @Test
    void testSqlExceptionSetOrderStatus() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> orderDAO.setOrderStatus(ID_VALUE, ORDER_STATUS_ID_VALUE));
    }

    @Test
    void setOrderDiscount() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> orderDAO.setOrderDiscount(ID_VALUE, DISCOUNT_VALUE, PRICE_VALUE));
        }
    }

    @Test
    void testSqlExceptionSetOrderDiscount() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> orderDAO.setOrderDiscount(ID_VALUE, DISCOUNT_VALUE, PRICE_VALUE));
    }

    @Test
    void update() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> orderDAO.update(getTestOrder()));
        }
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> orderDAO.update(getTestOrder()));
    }

    @Test
    void testDelete() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> orderDAO.delete(ID_VALUE));
        }
    }

    @Test
    void testSqlExceptionDelete() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> orderDAO.delete(ID_VALUE));
    }

    @Test
    void testGetUserOrders() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);

            List<Order> orders = orderDAO.getUserOrders(ID_VALUE);
            assertEquals(ONE, orders.size());
            Order resultOrder = orders.get(0);

            assertNotNull(resultOrder);

            User resultUser = resultOrder.getUser();
            Tour resultTour = resultOrder.getTour();

            assertEquals(getTestOrder().getId(), resultOrder.getId());
            assertEquals(getTestOrder().getOrderStatusId(), resultOrder.getOrderStatusId());
            assertEquals(getTestOrder().getDiscount(), resultOrder.getDiscount());
            assertEquals(getTestOrder().getTotalCost(), resultOrder.getTotalCost());
            assertEquals(getTestOrder().getDate(), resultOrder.getDate());

            assertEquals(getTestUser().getId(), resultUser.getId());
            assertEquals(getTestUser().getEmail(), resultUser.getEmail());
            assertEquals(getTestUser().getName(), resultUser.getName());
            assertEquals(getTestUser().getSurname(), resultUser.getSurname());
            assertEquals(getTestTour().getId(), resultTour.getId());
            assertEquals(getTestTour().getTitle(), resultTour.getTitle());
            assertEquals(getTestTour().getPrice(), resultTour.getPrice());
        }
    }

    @Test
    void testGetUsersNoOrders() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            List<Order> orders = orderDAO.getUserOrders(ID_VALUE);
            assertEquals(ZERO, orders.size());
        }
    }

    @Test
    void testSqlExceptionGetUserNoOrders() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> orderDAO.getUserOrders(ID_VALUE));
    }

    @Test
    void testGetToursOrders() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);

            List<Order> orders = orderDAO.getToursOrders(ID_VALUE);
            assertEquals(ONE, orders.size());
            Order resultOrder = orders.get(0);

            assertNotNull(resultOrder);

            User resultUser = resultOrder.getUser();
            Tour resultTour = resultOrder.getTour();

            assertEquals(getTestOrder().getId(), resultOrder.getId());
            assertEquals(getTestOrder().getOrderStatusId(), resultOrder.getOrderStatusId());
            assertEquals(getTestOrder().getDiscount(), resultOrder.getDiscount());
            assertEquals(getTestOrder().getTotalCost(), resultOrder.getTotalCost());
            assertEquals(getTestOrder().getDate(), resultOrder.getDate());

            assertEquals(getTestUser().getId(), resultUser.getId());
            assertEquals(getTestUser().getEmail(), resultUser.getEmail());
            assertEquals(getTestUser().getName(), resultUser.getName());
            assertEquals(getTestUser().getSurname(), resultUser.getSurname());
            assertEquals(getTestTour().getId(), resultTour.getId());
            assertEquals(getTestTour().getTitle(), resultTour.getTitle());
            assertEquals(getTestTour().getPrice(), resultTour.getPrice());
        }
    }

    @Test
    void testGetToursNoOrders() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            List<Order> orders = orderDAO.getToursOrders(ID_VALUE);
            assertEquals(ZERO, orders.size());
        }
    }

    @Test
    void testSqlExceptionGetToursNoOrders() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OrderDAO orderDAO = new MysqlOrderDAO(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> orderDAO.getToursOrders(ID_VALUE));
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
        when(resultSet.getInt(ORDER_STATUS_ID)).thenReturn(ORDER_STATUS_ID_VALUE);
        when(resultSet.getLong(USER_ID)).thenReturn(ID_VALUE);
        when(resultSet.getString(EMAIL)).thenReturn(EMAIL_VALUE);
        when(resultSet.getString(NAME)).thenReturn(NAME_VALUE);
        when(resultSet.getString(SURNAME)).thenReturn(SURNAME_VALUE);
        when(resultSet.getLong(TOUR_ID)).thenReturn(ID_VALUE);
        when(resultSet.getString(TITLE)).thenReturn(TITLE_VALUE);
        when(resultSet.getDouble(PRICE)).thenReturn(PRICE_VALUE);
        when(resultSet.getInt(DISCOUNT)).thenReturn(DISCOUNT_VALUE);
        when(resultSet.getDouble(TOTAL_COST)).thenReturn(TOTAL_COST_VALUE);
        when(resultSet.getDate(DATE)).thenReturn(Date.valueOf(DATE_VALUE));
    }
}