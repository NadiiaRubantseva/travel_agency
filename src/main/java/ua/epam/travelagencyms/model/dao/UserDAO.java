package ua.epam.travelagencyms.model.dao;

import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.entities.user.User;

import java.util.List;
import java.util.Optional;

/**
 * User DAO interface.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public interface UserDAO extends EntityDAO<User>{

    /**
     * Obtains instance of User from database by email
     * @param email - value of email
     * @return Optional.ofNullable - user is null if there is no user
     * @throws DAOException is wrapper for SQLException
     */
    Optional<User> getByEmail(String email) throws DAOException;

    /**
     * Updates user's password
     * @param user should contain user id and new password
     * @throws DAOException is wrapper for SQLException
     */
    void updatePassword(User user) throws DAOException;

    /**
     * Sets new user's role
     * @param userId - value of id field in database
     * @param role - new role for user
     * @throws DAOException is wrapper for SQLException
     */
    void setUserRole(long userId, Role role) throws DAOException;

    /**
     * Obtains sorted and limited list of users from database
     * @param query should contain filters, order, limits for pagination
     * @return users list that matches demands
     * @throws DAOException is wrapper for SQLException
     */
    List<User> getSorted(String query) throws DAOException;

    /**
     * Obtains number of all records matching filter
     * @param filter should contain 'where' to specify query
     * @return number of records
     * @throws DAOException is wrapper for SQLException
     */
    int getNumberOfRecords(String filter) throws DAOException;

//    /**
//     * Checks if email is verified by the user.
//     * @param id - value of user id
//     * @throws DAOException is wrapper for SQLException
//     */
//    boolean isEmailVerified(long id) throws DAOException;

    /**
     * Sets verification code in user table that is required for email verification.
     * @param id - value of user id
     * @param code - value of verification code
     * @throws DAOException is wrapper for SQLException
     */
    void setVerificationCode(long id, String code) throws DAOException;

    /**
     * Retrieves verification code value from user table.
     * @param id - value of user id
     * @throws DAOException is wrapper for SQLException
     */
    String getVerificationCode(long id) throws DAOException;

    /**
     * Sets 1 in email_verified column of user table meaning that
     * email is verified by the user.
     * @param id - value of user id
     * @throws DAOException is wrapper for SQLException
     */
    void setEmailVerified(long id) throws DAOException;

    /**
     * Sets image value in image column of user table.
     * @param userId - value of user id
     * @param avatar - value of image
     * @throws DAOException is wrapper for SQLException
     */
    void setAvatar(long userId, byte[] avatar) throws DAOException;

    /**
     * Checks if user is blocked.
     * @param id - value of user id
     * @throws DAOException is wrapper for SQLException
     */
    boolean isBlocked(long id) throws DAOException;

    /**
     * Sets new user status
     * @param userId - value of id field in database
     * @param statusId - new status of the user
     * @throws DAOException is wrapper for SQLException
     */
    void setStatus(long userId, byte statusId) throws DAOException;
    void setDiscount(int discount, long userId) throws DAOException;
}
