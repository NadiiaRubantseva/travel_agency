package ua.epam.travelagencyms.model.dao;

import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends EntityDAO<User>{
    Optional<User> getByEmail(String email) throws DAOException;
    void updatePassword(User user) throws DAOException;
    void setUserRole(String userEmail, Role role) throws DAOException;
    List<User> getSorted(String query) throws DAOException;
    int getNumberOfRecords(String filter) throws DAOException;

    boolean isEmailConfirmed(String email) throws DAOException;

    void setVerificationCode(String email, String code) throws DAOException;

    String getVerificationCode(String email) throws DAOException;

    void setEmailVerified(String email) throws DAOException;

    void setAvatar(long userId, byte[] avatar) throws DAOException;

    byte isBlocked(long id) throws DAOException;

    void setStatus(String email, byte statusId) throws DAOException;
}
