package ua.epam.travelagencyms.model.dao.mysql.constants;

/**
 * Class that contains all My SQL queries for UserDAO
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class UserSQLQueries {

    public static final String ADD_USER = "INSERT INTO user (email, password, name, surname) VALUES (?,?,?,?)";
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id=?";
    public static final String GET_USERS = "SELECT * FROM user";
    public static final String DELETE_USER = "DELETE FROM user WHERE id=?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";
    public static final String UPDATE_PASSWORD = "UPDATE user SET password=? WHERE id=?";
    public static final String SET_ROLE = "UPDATE user SET role_id=? WHERE id=?";
    public static final String SET_AVATAR = "UPDATE user SET avatar=? WHERE id=?";
    public static final String SET_STATUS = "UPDATE user SET is_blocked=? WHERE id=?";
    public static final String SET_DISCOUNT = "UPDATE user SET discount=? WHERE id=?";
    public static final String SET_VERIFICATION_CODE = "UPDATE user SET verification_code=? WHERE id=?";
    public static final String SET_EMAIL_VERIFIED = "UPDATE user SET is_email_verified=1 WHERE id=?";
    public static final String GET_VERIFICATION_CODE = "SELECT verification_code FROM user WHERE id=?";
    public static final String UPDATE_USER = "UPDATE user SET name=?, surname=? WHERE id=?";
    public static final String GET_SORTED = "SELECT * FROM user %s";
    public static final String GET_IS_EMAIl_VERIFIED = "SELECT is_email_verified FROM user WHERE id=?";
    public static final String GET_IS_USER_BLOCKED = "SELECT is_blocked FROM user WHERE id=?";
    public static final String GET_DISCOUNT = "SELECT discount FROM user WHERE id=?";
    public static final String GET_NUMBER_OF_RECORDS = "SELECT COUNT(id) AS numberOfRecords FROM user %s";

}
