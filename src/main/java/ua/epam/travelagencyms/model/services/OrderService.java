package ua.epam.travelagencyms.model.services;

import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;

import java.util.List;

public interface OrderService extends Service<OrderDTO> {

    void addOrder(OrderDTO orderDTO) throws ServiceException;

    void setStatus(String status, String orderId) throws ServiceException;
    void setDiscount(String discount, String total, String orderId) throws ServiceException;

    List<OrderDTO> viewToursOrders(long tourId) throws ServiceException;

    List<OrderDTO> viewUsersOrders(long userId) throws ServiceException;

    List<OrderDTO> getSortedOrders(String query) throws ServiceException;

    int getNumberOfRecords(String filter) throws ServiceException;

}
