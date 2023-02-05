package ua.epam.travelagencyms.model.services.implementation;

import lombok.RequiredArgsConstructor;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.exceptions.NoSuchOrderException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.dao.OrderDAO;
import ua.epam.travelagencyms.model.entities.order.Order;
import ua.epam.travelagencyms.model.entities.order.OrderStatus;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.user.User;
import ua.epam.travelagencyms.model.services.OrderService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ua.epam.travelagencyms.utils.ConvertorUtil.convertDTOToOrder;
import static ua.epam.travelagencyms.utils.ConvertorUtil.convertOrderToDTO;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    /** Contains orderDAO field to work with OrderDAO */
    private final OrderDAO orderDAO;

    /**
     * Calls DAO to add relevant entity.
     * Converts OrderDTO to Order
     * @param orderDTO - DTO to be added as Order to database
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public void addOrder(OrderDTO orderDTO) throws ServiceException {
        Order order = Order.builder()
                .tour(Tour.builder().id(orderDTO.getTourId()).price(orderDTO.getTourPrice()).build())
                .user(User.builder().id(orderDTO.getUserId()).discount(orderDTO.getDiscount()).build())
                .totalCost(orderDTO.getTotalCost())
                .date(LocalDate.parse(orderDTO.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .build();
        try {
            orderDAO.add(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Calls DAO to set new order status
     * @param status - new status for the order
     * @param orderId - order id
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public void setStatus(String status, String orderId) throws ServiceException {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            long id = Long.parseLong(orderId);
            orderDAO.setOrderStatus(id, orderStatus);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Calls DAO to set order discount
     * @param discount - discount value to be set
     * @param tourPrice - tour price, required for establishing the total cose of the order.
     * @param orderId - order id value.
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public void setDiscount(String discount, String tourPrice, String orderId) throws ServiceException {
        try {
            long id = Long.parseLong(orderId);
            int d = Integer.parseInt(discount);
            double t = Double.parseDouble(tourPrice);
            orderDAO.setOrderDiscount(id, d, t);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Obtains list of all instances of Orders from DAO that were made for specific tour. Converts Tour to TourDTOs
     * @param tourId - tour id value
     * @return List of TourDTOS
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public List<OrderDTO> viewToursOrders(long tourId) throws ServiceException {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        try {
            List<Order> orders = orderDAO.getToursOrders(tourId);
            orders.forEach(order -> orderDTOS.add(convertOrderToDTO(order)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return orderDTOS;
    }

    /**
     * Obtains list of all instances of Orders from DAO that were made by specific user. Converts Tour to TourDTOs
     * @param userId - user id value
     * @return List of TourDTOS
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public List<OrderDTO> viewUsersOrders(long userId) throws ServiceException {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        try {
            List<Order> orders = orderDAO.getUserOrders(userId);
            orders.forEach(order -> orderDTOS.add(convertOrderToDTO(order)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return orderDTOS;
    }

    /**
     * Calls DAO to get sorted, filtered and limited list of DTOs. Converts Orders to OrderDTOs
     * @param query - to obtain necessary DTOs
     * @return List of OrderDTOs that match demands
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public List<OrderDTO> getSortedOrders(String query) throws ServiceException {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        try {
            List<Order> orders = orderDAO.getSorted(query);
            orders.forEach(order -> orderDTOS.add(convertOrderToDTO(order)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return orderDTOS;
    }

    /**
     * Calls DAO to get number of all records that match filter
     * @param filter - conditions for such Orders
     * @return number of records that match demands
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public int getNumberOfRecords(String filter) throws ServiceException {
        int records;
        try {
            records = orderDAO.getNumberOfRecords(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return records;
    }

    /**
     * Obtains instance of Order from DAO by id. Checks if id valid. Converts Order to OrderDTO
     * @param orderIdString - id as a String to validate and convert to long
     * @return OrderDTO instance
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchOrderException
     */
    @Override
    public OrderDTO getById(String orderIdString) throws ServiceException {
        OrderDTO orderDTO;
        long orderId = getOrderId(orderIdString);
        try {
            Order order = orderDAO.getById(orderId).orElseThrow(NoSuchOrderException::new);
            orderDTO = convertOrderToDTO(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return orderDTO;
    }

    private static long getOrderId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchOrderException());
    }

    private static long checkId(String idString, ServiceException exception) throws ServiceException {
        long orderId;
        try {
            orderId = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw exception;
        }
        return orderId;
    }

    /**
     * Obtains list of all instances of Order from DAO. Converts Orders to OrderDTOs
     * @return List of OrderDTOs
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public List<OrderDTO> getAll() throws ServiceException {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        try {
            List<Order> orders = orderDAO.getAll();
            orders.forEach(order -> orderDTOS.add(convertOrderToDTO(order)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return orderDTOS;
    }

    /**
     * Updates Order. Validate UserDTO. Converts OrderDTO to Order
     * @param entity - OrderDTO instance.
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public void update(OrderDTO entity) throws ServiceException {
        Order order = convertDTOToOrder(entity);
        try {
            orderDAO.update(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes Order entity from database. Validate id.
     * @param orderIdString - id as a String
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchOrderException
     */
    @Override
    public void delete(String orderIdString) throws ServiceException {
        long orderId = getOrderId(orderIdString);
        try {
            orderDAO.delete(orderId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
