package ua.epam.travelagencyms.model.services.implementation;

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

public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    public OrderServiceImpl (OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void addOrder(OrderDTO orderDTO) throws ServiceException {
        Order order = Order.builder()
                .tour(Tour.builder().id(orderDTO.getTourId()).price(orderDTO.getTourPrice()).build())
                .user(User.builder().id(orderDTO.getUserId()).build())
                .date(LocalDate.parse(orderDTO.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .build();
        System.out.println(order);
        try {
            orderDAO.add(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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

    @Override
    public void setDiscount(String discount, String price, String orderId) throws ServiceException {
        try {
            long id = Long.parseLong(orderId);
            int d = Integer.parseInt(discount);
            double t = Double.parseDouble(price);
            orderDAO.setOrderDiscount(id, d, t);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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

    public static long getOrderId(String idString) throws ServiceException {
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

    @Override
    public void update(OrderDTO entity) throws ServiceException {
        Order order = convertDTOToOrder(entity);
        try {
            orderDAO.update(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(String orderIdString) throws ServiceException {
        long orderId = getOrderId(orderIdString);
        try {
            orderDAO.delete(orderId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    public static Order convertDTOToOrder(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .orderStatus(OrderStatus.valueOf(orderDTO.getOrderStatus()))
                .user(User.builder().id(orderDTO.getUserId()).build())
                .tour(Tour.builder().id(orderDTO.getTourId()).price(orderDTO.getTourPrice()).title(orderDTO.getTourTitle()).build())
                .discount(orderDTO.getDiscount())
                .totalCost(orderDTO.getTotalCost())
                .build();
    }

    public static OrderDTO convertOrderToDTO(Order order) {
        User user = order.getUser();
        Tour tour = order.getTour();
        return OrderDTO.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus().name())
                .userId(user.getId())
                .userName(user.getEmail())
                .tourId(tour.getId())
                .tourTitle(tour.getTitle())
                .tourPrice(tour.getPrice())
                .discount(order.getDiscount())
                .totalCost(order.getTotalCost())
                .date(String.valueOf(order.getDate()))
                .build();
    }
}
