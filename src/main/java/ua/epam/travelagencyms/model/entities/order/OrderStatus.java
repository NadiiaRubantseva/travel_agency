package ua.epam.travelagencyms.model.entities.order;

/**
 * OrderStatus entity enum. Matches table 'order_status' in database.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public enum OrderStatus {REGISTERED(1), PAID(2), CANCELED(3);
    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    /**
     * Obtains the order status by the value. REGISTERED by default.
     * @param value matching order status
     * @return the order status assigned to this value
     */
    public static OrderStatus getOrderStatus(int value) {
        for (OrderStatus orderStatus: OrderStatus.values()) {
            if (orderStatus.value == value) {
                return orderStatus;
            }
        }
        return REGISTERED;
    }
}
