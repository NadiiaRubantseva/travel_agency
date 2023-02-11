package ua.epam.travelagencyms.model.entities.order;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderStatusTest {

    @ParameterizedTest
    @CsvSource({"REGISTERED, 1", "PAID, 2", "CANCELED, 3"})
    void testGetValue(String orderStatusString, String value) {
        OrderStatus orderStatus = OrderStatus.valueOf(orderStatusString);
        int number = Integer.parseInt(value);
        assertEquals(number, orderStatus.getValue());
    }

    @ParameterizedTest
    @CsvSource({"REGISTERED, 1", "PAID, 2", "CANCELED, 3"})
    void testGetRole(String orderStatusString, String value) {
        OrderStatus orderStatus = OrderStatus.valueOf(orderStatusString);
        int number = Integer.parseInt(value);
        assertEquals(orderStatus, OrderStatus.getOrderStatus(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0,5,100})
    void testDefaultRole(int number) {
        assertEquals(OrderStatus.REGISTERED, OrderStatus.getOrderStatus(number));
    }
}