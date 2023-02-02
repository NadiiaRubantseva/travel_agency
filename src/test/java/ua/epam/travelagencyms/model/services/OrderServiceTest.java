package ua.epam.travelagencyms.model.services;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.exceptions.NoSuchOrderException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.dao.OrderDAO;
import ua.epam.travelagencyms.model.entities.order.Order;
import ua.epam.travelagencyms.model.services.implementation.OrderServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.ID_VALUE;
import static ua.epam.travelagencyms.model.dao.DAOTestUtils.*;
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
    void testViewToursOrders() throws DAOException, ServiceException {
        List<Order> orders = new ArrayList<>();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.add(getTestOrder());
        orderDTOs.add(getTestOrderDTO());
        when(orderDAO.getToursOrders(ID_VALUE)).thenReturn(orders);
        assertIterableEquals(orderDTOs, orderService.viewToursOrders(ID_VALUE));
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
    void testNumberOfRecords() throws DAOException, ServiceException {
        String filter = orderQueryBuilder().getRecordQuery();
        when(orderDAO.getNumberOfRecords(filter)).thenReturn(1);
        assertEquals(1, orderService.getNumberOfRecords(filter));
    }

    @Test
    void testViewOrder() throws DAOException, ServiceException {
        when(orderDAO.getById(ID_VALUE)).thenReturn(Optional.of(getTestOrder()));
        assertEquals(getTestOrderDTO(), orderService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testViewNoOrder() throws DAOException {
        when(orderDAO.getById(ID_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchOrderException.class,() -> orderService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testUpdateOrder() throws DAOException {
        doNothing().when(orderDAO).update(isA(Order.class));
        assertDoesNotThrow(() -> orderService.update(getTestOrderDTO()));
    }

    @Test
    void testDeleteOrder() throws DAOException {
        doNothing().when(orderDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> orderService.delete(String.valueOf(ID_VALUE)));
    }

}
