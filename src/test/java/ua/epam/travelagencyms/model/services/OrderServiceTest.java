package ua.epam.travelagencyms.model.services;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.exceptions.NoSuchOrderException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.dao.OrderDAO;
import ua.epam.travelagencyms.model.entities.order.Order;
import ua.epam.travelagencyms.model.services.implementation.OrderServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.orderQueryBuilder;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.tourQueryBuilder;

public class OrderServiceTest {

    private final OrderDAO orderDAO = mock(OrderDAO.class);

    private final OrderService orderService = new OrderServiceImpl(orderDAO);

    @Test
    void testCreateOrder() throws DAOException {
        doNothing().when(orderDAO).add(isA(Order.class));
        OrderDTO orderDTO = getTestOrderDTO();
        assertDoesNotThrow( () -> orderService.addOrder(orderDTO));
    }

    @Test
    void testSQLExceptionCreateOrder() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(orderDAO).add(isA(Order.class));
        OrderDTO orderDTO = getTestOrderDTO();
        ServiceException e = assertThrows(ServiceException.class, () -> orderService.addOrder(orderDTO));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testViewToursOrders() throws DAOException, ServiceException {
        List<Order> orders = new ArrayList<>();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.add(getTestOrder());
        orderDTOs.add(getTestOrderDTO());
        when(orderDAO.getToursOrders(ID_VALUE)).thenReturn(orders);
        assertIterableEquals(orderDTOs, orderService.viewToursOrders(ID_VALUE));
    }

    @Test
    void testSQLErrorViewToursOrders() throws DAOException {
        when(orderDAO.getToursOrders(isA(long.class))).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> orderService.viewToursOrders(ID_VALUE));

    }

    @Test
    void testViewUsersOrders() throws DAOException, ServiceException {
        List<Order> orders = new ArrayList<>();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.add(getTestOrder());
        orderDTOs.add(getTestOrderDTO());
        when(orderDAO.getUserOrders(ID_VALUE)).thenReturn(orders);
        assertIterableEquals(orderDTOs, orderService.viewUsersOrders(ID_VALUE));
    }

    @Test
    void testSQLErrorViewUsersOrders() throws DAOException {
        when(orderDAO.getUserOrders(isA(long.class))).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> orderService.viewUsersOrders(ID_VALUE));
    }

    @Test
    void testViewSortedOrders() throws DAOException, ServiceException {
        List<Order> orders = new ArrayList<>();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.add(getTestOrder());
        orderDTOs.add(getTestOrderDTO());
        String query = tourQueryBuilder().getQuery();
        when(orderDAO.getSorted(query)).thenReturn(orders);
        assertIterableEquals(orderDTOs, orderService.getSortedOrders(query));
    }

    @Test
    void testSQLErrorViewSortedOrders() throws DAOException {
        when(orderDAO.getSorted(QUERY)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> orderService.getSortedOrders(QUERY));
    }

    @Test
    void testNumberOfRecords() throws DAOException, ServiceException {
        String filter = orderQueryBuilder().getRecordQuery();
        when(orderDAO.getNumberOfRecords(filter)).thenReturn(1);
        assertEquals(1, orderService.getNumberOfRecords(filter));
    }

    @Test
    void testSQLErrorNumberOfRecords() throws DAOException {
        when(orderDAO.getNumberOfRecords(FILTER)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> orderService.getNumberOfRecords(FILTER));
    }

    @Test
    void testViewOrder() throws DAOException, ServiceException {
        when(orderDAO.getById(ID_VALUE)).thenReturn(Optional.of(getTestOrder()));
        assertEquals(getTestOrderDTO(), orderService.getById(ID_STRING_VALUE));
    }

    @Test
    void testViewNoOrder() throws DAOException {
        when(orderDAO.getById(ID_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchOrderException.class,() -> orderService.getById(ID_STRING_VALUE));
    }

    @Test
    void testSQLErrorViewOrder() throws DAOException {
        when(orderDAO.getById(ONE)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> orderService.getById(String.valueOf(ONE)));
    }

    @Test
    void testUpdateOrder() throws DAOException {
        doNothing().when(orderDAO).update(isA(Order.class));
        assertDoesNotThrow(() -> orderService.update(getTestOrderDTO()));
    }

    @Test
    void testDeleteOrder() throws DAOException {
        doNothing().when(orderDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> orderService.delete(ID_STRING_VALUE));
    }

    @Test
    void testSetOrderStatus() throws DAOException {
        doNothing().when(orderDAO).setOrderStatus(isA(long.class), isA(int.class));
        assertDoesNotThrow(() -> orderService.setOrderStatus(ID_STRING_VALUE, ORDER_STATUS_VALUE));
    }

    @Test
    void testSQLErrorSetOrderStatus() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(orderDAO).setOrderStatus(isA(long.class), isA(int.class));
        ServiceException e = assertThrows(ServiceException.class, () -> orderService.setOrderStatus(ID_STRING_VALUE, ORDER_STATUS_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testSetOrderDiscount() throws DAOException {
        doNothing().when(orderDAO).setOrderDiscount(isA(long.class), isA(int.class), isA(double.class));
        assertDoesNotThrow(() -> orderService.setOrderDiscount(ID_STRING_VALUE, DISCOUNT_STRING_VALUE, PRICE_STRING_VALUE));
    }

    @Test
    void testSQLErrorSetOrderDiscount() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(orderDAO).setOrderDiscount(isA(long.class), isA(int.class), isA(double.class));
        ServiceException e = assertThrows(ServiceException.class, () -> orderService.setOrderDiscount(ID_STRING_VALUE, DISCOUNT_STRING_VALUE, PRICE_STRING_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testViewOrders() throws DAOException, ServiceException {
        List<Order> orders = new ArrayList<>();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.add(getTestOrder());
        orderDTOs.add(getTestOrderDTO());
        when(orderDAO.getAll()).thenReturn(orders);
        assertIterableEquals(orderDTOs, orderService.getAll());
    }

    @Test
    void testSQLErrorViewUsers() throws DAOException {
        when(orderDAO.getAll()).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, orderService::getAll);
    }

    @Test
    void testSQLErrorUpdateOrder() throws DAOException {

        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(orderDAO).update(isA(Order.class));
        OrderDTO orderDTO = getTestOrderDTO();
        ServiceException e = assertThrows(ServiceException.class, () -> orderService.update(orderDTO));
        assertEquals(e.getCause(), exception);

    }

    @Test
    void testSQLErrorDeleteOrder() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(orderDAO).delete(isA(Long.class));
        ServiceException e = assertThrows(ServiceException.class, () -> orderService.delete(ID_STRING_VALUE));
        assertEquals(e.getCause(), exception);
    }
}
