package ua.epam.travelagencyms.model.dao.mysql;

import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.dao.OrderDAO;
import ua.epam.travelagencyms.model.entities.order.Order;
import ua.epam.travelagencyms.model.entities.order.OrderStatus;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.user.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.epam.travelagencyms.model.dao.mysql.constants.OrderSQLQueries.*;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.*;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.NUMBER_OF_RECORDS;

public class MySqlOrderDAO implements OrderDAO {

    private final DataSource dataSource;

    public MySqlOrderDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Order order) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER)) {
            int k = 0;
            preparedStatement.setLong(++k, order.getUser().getId());
            preparedStatement.setLong(++k, order.getTour().getId());
            preparedStatement.setDouble(++k, order.getTour().getPrice());
            preparedStatement.setDate(++k, Date.valueOf(order.getDate()));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<Order> getById(long id) throws DAOException {
        Order order = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    order = getOrder(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> getAll() throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(getOrder(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return orders;
    }

    @Override
    public void setOrderStatus(long orderId, OrderStatus status) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_STATUS)) {
            int k = 0;
            preparedStatement.setInt(++k, status.getValue());
            preparedStatement.setLong(++k, orderId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void setOrderDiscount(long orderId, int discount, double price) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_DISCOUNT)) {
            int k = 0;
            preparedStatement.setInt(++k, discount);
            preparedStatement.setDouble(++k, calculateTotalPrice(price, discount));
            preparedStatement.setLong(++k, orderId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Order order) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER)) {
            int k = 0;
            preparedStatement.setInt(++k, order.getOrderStatus().getValue());
            preparedStatement.setInt(++k, order.getDiscount());
            preparedStatement.setDouble(++k, calculateTotalPrice(order.getTour().getPrice(), order.getDiscount()));
            preparedStatement.setLong(++k, order.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private double calculateTotalPrice(double price, int discount) {
        double discountValue = (price * discount) / 100;
        return price - discountValue;
    }
    //cancel
    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CANCEL_ORDER)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Order> getUserOrders(long userId) throws DAOException {
        return getOrders(userId, GET_USERS_ORDERS);
    }

    @Override
    public List<Order> getToursOrders(long tourId) throws DAOException {
        return getOrders(tourId, GET_TOURS_ORDERS);
    }

    @Override
    public List<Order> getSorted(String query) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(GET_SORTED, query))) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(getOrder(resultSet));
                }
            }
        }catch (SQLException e) {
            throw new DAOException(e);
        }
        return orders;
    }

    @Override
    public int getNumberOfRecords(String filter) throws DAOException {
        int numberOfRecords = 0;
        String query = String.format(GET_NUMBER_OF_RECORDS, filter);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    numberOfRecords = resultSet.getInt(NUMBER_OF_RECORDS);
                }
            }
        }catch (SQLException e) {
            throw new DAOException(e);
        }
        return numberOfRecords;
    }


    private List<Order> getOrders(long tourId, String getToursOrders) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getToursOrders)) {
            int k = 0;
            preparedStatement.setLong(++k, tourId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(getOrder(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return orders;
    }

    private Order getOrder(ResultSet resultSet) throws SQLException {
        Tour tour = getTour(resultSet);
        User user = getUser(resultSet);
        return Order.builder()
                .id(resultSet.getInt(ID))
                .orderStatus(OrderStatus.getOrderStatus(resultSet.getInt(ORDER_STATUS_ID)))
                .tour(tour)
                .user(user)
                .discount(resultSet.getInt(DISCOUNT))
                .totalCost(resultSet.getDouble(TOTAL_COST))
                .date(resultSet.getDate("date").toLocalDate())
                .build();
    }

    private Tour getTour(ResultSet resultSet) throws SQLException {
        Tour tour = null;
        long tourId = resultSet.getLong(TOUR_ID);
        if (tourId !=0) {
            tour = Tour.builder()
                    .id(tourId)
                    .title(resultSet.getString(TITLE))
                    .price(resultSet.getDouble(PRICE))
                    .build();
        }
        return tour;
    }

    private static User getUser(ResultSet resultSet) throws SQLException {
        User user = null;
        long userId = resultSet.getLong(USER_ID);
        if (userId !=0) {
            user = User.builder()
                    .id(userId)
                    .email(resultSet.getString(EMAIL))
                    .name(resultSet.getString(NAME))
                    .surname(resultSet.getString(SURNAME))
                    .build();
        }
        return user;
    }
}
