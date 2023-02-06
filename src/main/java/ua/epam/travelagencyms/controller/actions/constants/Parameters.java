package ua.epam.travelagencyms.controller.actions.constants;

/**
 * This is Parameters class. It contains required parameters and attributes names
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class Parameters {

    /**
     * To store logged user in session
     */
    public static final String LOGGED_USER = "loggedUser";

    /**
     * To store blocked user in HttpRequest
     */
    public static final String BLOCKED_USER = "blockedUser";


    /**
     * Basic field
     */
    public static final String ID = "id";


    /**
     * Parameters and attributes to work with UserDTO
     */
    public static final String USER_ID = "user-id";
    public static final String USER = "user";
    public static final String USERS = "users";
    public static final String ROLE = "role";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirm-password";
    public static final String OLD_PASSWORD = "old-password";
    public static final String AVATAR = "avatar";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String SECURITY_CODE = "security-code";
    public static final String STATUS = "status";
    public static final String USER_STATUS = "user-status";


    /**
     * Parameters and attributes to work with TourDTO
     */
    public static final String TOUR = "tour";
    public static final String TOURS = "tours";
    public static final String TOUR_ID = "tour-id";
    public static final String TITLE = "title";
    public static final String HOT = "hot";
    public static final String TYPE = "type";
    public static final String HOTEL = "hotel";
    public static final String IMAGE = "image";


    /**
     * Parameters and attributes to work with OrderDTO
     */
    public static final String ORDER_STATUS_ID = "status";
    public static final String ORDER_ID = "order-id";
    public static final String ORDERS = "orders";
    public static final String DISCOUNT = "discount";


    /**
     * Parameters and attributes to work with sorting, ordering and pagination
     */
    public static final String SORT = "sort";
    public static final String ORDER = "order";
    public static final String OFFSET = "offset";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String PAGES = "pages";
    public static final String START = "start";
    public static final String END = "end";
    public static final String RECORDS = "records";
    public static final String MIN_PRICE = "min_price";
    public static final String MAX_PRICE = "max_price";
    public static final String PERSONS = "persons";
    public static final String PRICE = "price";
    public static final String TOUR_PRICE = "tour-price";


    /**
     * Parameters and attributes to send error or message to view
     */
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";


    /**
     * Parameter to get action name
     */
    public static final String ACTION = "action";


    /**
     * Parameter and attribute to get or set locale
     */
    public static final String LOCALE = "locale";

    public static final String CURRENT_PATH = "currentPath";
}
