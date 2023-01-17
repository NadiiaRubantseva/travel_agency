//package ua.epam.travelagencyms.model.dao;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ua.epam.travelagencyms.exceptions.DAOException;
//import ua.epam.travelagencyms.model.entities.order.Order;
//import ua.epam.travelagencyms.model.entities.order.OrderStatus;
//import ua.epam.travelagencyms.model.entities.tour.Tour;
//import ua.epam.travelagencyms.model.entities.user.User;
//
//import java.io.FileNotFoundException;
//import java.sql.SQLException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static ua.epam.travelagencyms.Constants.*;
//import static ua.epam.travelagencyms.model.dao.DAOTestUtils.*;
//import static ua.epam.travelagencyms.utils.QueryBuilderUtil.*;
//
//public class OrderDAOTest {
//
//
//    @BeforeEach
//    void clearDB() throws FileNotFoundException, SQLException {
//        createEmptyDB();
//    }
//
//    @Test
//    void testAdd() throws DAOException {
//
//        tourDAO.add(getTestTour());
//        userDAO.add(getTestUser());
//        assertDoesNotThrow(() -> orderDAO.add(getTestOrder()));
//
//    }
//
//    @Test
//    void testAddNoTour() {
//        assertThrows(DAOException.class, () -> orderDAO.add(getTestOrder()));
//    }
//
//    @Test
//    void testAddNoSuchUser() throws DAOException {
//        userDAO.add(getTestUser());
//        assertThrows(DAOException.class, () -> orderDAO.add(getTestOrder()));
//    }
//
//    @Test
//    void testAddNoSuchTour() throws DAOException {
//        tourDAO.add(getTestTour());
//        assertThrows(DAOException.class, () -> orderDAO.add(getTestOrder()));
//    }
//
//    @Test
//    void testGetById() throws DAOException {
//        prepareDb();
//        Order resultOrder = orderDAO.getById(ID_VALUE).orElse(null);
//        assertNotNull(resultOrder);
//        assertEquals(getTestOrder().getId(), resultOrder.getId());
//    }
//
//    private static void prepareDb() throws DAOException {
//        tourDAO.add(getTestTour());
//        userDAO.add(getTestUser());
//        orderDAO.add(getTestOrder());
//    }
//
//    @Test
//    void testGetAbsent() throws DAOException {
//        assertNull(orderDAO.getById(ID_VALUE).orElse(null));
//    }
//
//    @Test
//    void testGetOrder() throws DAOException {
//        prepareDb();
//
//        Order resultOrder = orderDAO.getById(ID_VALUE).orElse(null);
//        assertNotNull(resultOrder);
//
//        User resultUser = resultOrder.getUser();
//        Tour resultTour = resultOrder.getTour();
//
//        assertEquals(resultUser.getId(), getTestUser().getId());
//        assertEquals(resultUser.getEmail(), getTestUser().getEmail());
//        assertEquals(resultUser.getName(), getTestUser().getName());
//        assertEquals(resultUser.getSurname(), getTestUser().getSurname());
//        assertEquals(resultTour.getId(), getTestTour().getId());
//        assertEquals(resultTour.getTitle(), getTestTour().getTitle());
//        assertEquals(resultTour.getPrice(), getTestTour().getPrice());
//
//    }
//
//    @Test
//    void testGetAll() throws DAOException {
//        prepareDb();
//
//        List<Order> orders = orderDAO.getAll();
//        assertEquals(ONE, orders.size());
//    }
//
//    @Test
//    void testGetNoOrders() throws DAOException {
//        List<Order> orders = orderDAO.getAll();
//        assertEquals(ZERO, orders.size());
//    }
//
//    @Test
//    void testUpdate() throws DAOException {
//        prepareDb();
//        assertDoesNotThrow(() -> orderDAO.update(getTestOrder()));
//    }
//
//    @Test
//    void testUpdateWithDiscount() throws DAOException {
//        prepareDb();
//
//        Order testOrder = getTestOrder();
//        testOrder.setDiscount(10);
//        orderDAO.update(testOrder);
//
//        testOrder.setTotalCost(90);
//
//        Order resultOrder = orderDAO.getById(ID_VALUE).orElse(null);
//        assertNotNull(resultOrder);
//        assertEquals(resultOrder.getDiscount(), testOrder.getDiscount());
//        assertEquals(resultOrder.getTotalCost(), testOrder.getTotalCost());
//    }
//
//    @Test
//    void testUpdateWithStatus() throws DAOException {
//        prepareDb();
//
//        Order testOrder = getTestOrder();
//        testOrder.setOrderStatus(OrderStatus.PAID);
//        orderDAO.update(testOrder);
//
//        Order resultOrder = orderDAO.getById(ID_VALUE).orElse(null);
//        assertNotNull(resultOrder);
//        assertEquals(resultOrder.getOrderStatus(), testOrder.getOrderStatus());
//    }
//
//    @Test
//    void testUpdateNoOrder() {
//        assertDoesNotThrow(() -> orderDAO.update(getTestOrder()));
//    }
//
//    @Test
//    void testDelete() throws DAOException {
//        prepareDb();
//        assertDoesNotThrow(() -> orderDAO.delete(ID_VALUE));
//        List<Order> orders = orderDAO.getAll();
//        assertEquals(ZERO, orders.size());
//    }
//
//    @Test
//    void testDeleteNoOrder() {
//        assertDoesNotThrow(() -> orderDAO.delete(ID_VALUE));
//    }
//
//    @Test
//    void testGetToursOrders() throws DAOException {
//        prepareDb();
//
//        List<Order> orders = orderDAO.getToursOrders(ID_VALUE);
//        assertEquals(ONE, orders.size());
//    }
//
//    @Test
//    void testNoOrder() throws DAOException {
//        tourDAO.add( getTestTour());
//        List<Order> orders = orderDAO.getToursOrders(ID_VALUE);
//        assertEquals(ZERO, orders.size());
//    }
//
//    @Test
//    void testGetUsersOrders() throws DAOException {
//        prepareDb();
//
//        List<Order> orders = orderDAO.getUserOrders(ID_VALUE);
//        assertEquals(ONE, orders.size());
//    }
//
//    @Test
//    void testNoOrderForUser() throws DAOException {
//        tourDAO.add( getTestTour());
//        List<Order> orders = orderDAO.getUserOrders(ID_VALUE);
//        assertEquals(ZERO, orders.size());
//    }
//
//    @Test
//    void testGetAllSorted() throws DAOException {
//        prepareDb();
//        List<Order> sorted = orderDAO.getSorted(orderQueryBuilder().getQuery());
//        assertFalse(sorted.isEmpty());
//        assertEquals(ONE, sorted.size());
//    }
//
//    @Test
//    void testGetAllSortedByTotalPrice() throws DAOException {
//        for (User user: getRandomUsers()) {
//            userDAO.add(user);
//        }
//        for (Tour tour: getRandomTours()) {
//            tourDAO.add(tour);
//        }
//
//        orderDAO.add(getTestOrder());
//        Order order = getTestOrder();
//        order.getTour().setPrice(250);
//        orderDAO.add(order);
//        order.getTour().setPrice(150);
//        orderDAO.add(order);
//
//        List<Order> orders = orderDAO.getAll();
//        orders = orders.stream()
//                .sorted(Comparator.comparing(Order::getTotalCost))
//                .collect(Collectors.toList());
//        List<Order> sorted = orderDAO.getSorted(orderQueryBuilder().setSortField("total_cost").getQuery());
//        assertIterableEquals(orders, sorted);
//
//    }
//
//    @Test
//    void testGetAllSortedByTotalPriceDesc() throws DAOException {
//        for (User user: getRandomUsers()) {
//            userDAO.add(user);
//        }
//        for (Tour tour: getRandomTours()) {
//            tourDAO.add(tour);
//        }
//
//        orderDAO.add(getTestOrder());
//        Order order = getTestOrder();
//        order.getTour().setPrice(250);
//        orderDAO.add(order);
//        order.getTour().setPrice(150);
//        orderDAO.add(order);
//
//        List<Order> orders = orderDAO.getAll();
//        orders = orders.stream()
//                .sorted(Comparator.comparing(Order::getTotalCost).reversed())
//                .collect(Collectors.toList());
//        List<Order> sorted = orderDAO.getSorted(orderQueryBuilder().setSortField("total_cost").setOrder(DESC).getQuery());
//        assertIterableEquals(orders, sorted);
//    }
//
//    @Test
//    void testGetAllSortedByTotalPricePagination() throws DAOException {
//        for (User user: getRandomUsers()) {
//            userDAO.add(user);
//        }
//        for (Tour tour: getRandomTours()) {
//            tourDAO.add(tour);
//        }
//
//        orderDAO.add(getTestOrder());
//        Order order = getTestOrder();
//        order.getTour().setPrice(250);
//        orderDAO.add(order);
//        order.getTour().setPrice(150);
//        orderDAO.add(order);
//
//        List<Order> orders = orderDAO.getAll();
//        orders = orders.stream()
//                .sorted(Comparator.comparing(Order::getTotalCost).reversed())
//                .limit(THREE)
//                .collect(Collectors.toList());
//        List<Order> sorted = orderDAO.getSorted(orderQueryBuilder().setSortField("total_cost").setLimits("0", "3").setOrder(DESC).getQuery());
//        assertIterableEquals(orders, sorted);
//    }
//
//    @Test
//    void testGetAllSortedByTotalPricePaginationOffsetThree() throws DAOException {
//        for (User user: getRandomUsers()) {
//            userDAO.add(user);
//        }
//        for (Tour tour: getRandomTours()) {
//            tourDAO.add(tour);
//        }
//
//        orderDAO.add(getTestOrder());
//        Order order = getTestOrder();
//        order.getTour().setPrice(250);
//        orderDAO.add(order);
//        order.getTour().setPrice(150);
//        orderDAO.add(order);
//
//        List<Order> orders = orderDAO.getAll();
//        orders = orders.stream()
//                .sorted(Comparator.comparing(Order::getTotalCost))
//                .skip(THREE)
//                .limit(THREE)
//                .collect(Collectors.toList());
//        List<Order> sorted = orderDAO.getSorted(orderQueryBuilder().setSortField("total_cost").setLimits("3", "3").setOrder(DESC).getQuery());
//        assertIterableEquals(orders, sorted);
//    }
//
//    private List<User> getRandomUsers() {
//        List<User> users = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            users.add(getRandomUser(i));
//        }
//        return users;
//    }
//
//    private User getRandomUser(int i) {
//        User user = getTestUser();
//        user.setId(i + 1);
//        user.setEmail(user.getEmail() + i);
//        user.setName(user.getName() + new Random().nextInt(100));
//        return user;
//    }
//
//    private List<Tour> getRandomTours() {
//        List<Tour> tours = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            tours.add(getRandomTour(i));
//        }
//        return tours;
//    }
//
//    private Tour getRandomTour(int i) {
//        Tour tour = getTestTour();
//        tour.setId(i + 1);
//        tour.setTitle(tour.getTitle() + i);
//        tour.setPersons(tour.getPersons() + new Random().nextInt(100));
//        tour.setPrice(tour.getPrice() + new Random().nextInt(100));
//        tour.setHot((byte) (tour.getHot() + new Random().nextInt(1)));
//        return tour;
//    }
//}
