package ua.epam.travelagencyms.model.dao.mysql.constants;

/**
 * Class that contains all My SQL database fields
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class SQLFields {

    /** Common id field for user, tour, order and loyalty_program tables */
    public static final String ID = "id";
    public static final String DISCOUNT = "discount";


    /** Fields for user table */
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String AVATAR = "avatar";
    public static final String IS_BLOCKED = "is_blocked";
    public static final String IS_EMAIL_VERIFIED = "is_email_verified";
    public static final String VERIFICATION_CODE = "verification_code";
    public static final String ROLE_ID = "role_id";

    /** Fields for tour table */
    public static final String TITLE = "title";
    public static final String PERSONS = "persons";
    public static final String PRICE = "price";
    public static final String HOT = "hot";
    public static final String TYPE_ID = "type_id";
    public static final String HOTEL_ID = "hotel_id";
    public static final String IMAGE = "image";
    public static final String DESCRIPTION = "description";

    /** Fields for order table */
    public static final String TOTAL_COST = "total_cost";
    public static final String ORDER_STATUS_ID = "order_status_id";
    public static final String TOUR_ID = "tour_id";
    public static final String USER_ID = "user_id";
    public static final String DATE = "date";

    /** Fields for loyalty_program table */
    public static final String STEP = "step";
    public static final String MAX_DISCOUNT = "max_discount";


    /** Fields generated by queries */
    public static final String NUMBER_OF_RECORDS = "numberOfRecords";


}
