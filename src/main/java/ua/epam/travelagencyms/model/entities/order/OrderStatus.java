package ua.epam.travelagencyms.model.entities.order;

public enum OrderStatus {REGISTERED(1), PAID(2), CANCELED(3);
    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static OrderStatus getOrderStatus(int value) {
        for (OrderStatus orderStatus: OrderStatus.values()) {
            if (orderStatus.value == value) {
                return orderStatus;
            }
        }
        return REGISTERED;
    }
}
