package ua.epam.travelagencyms.model.services;

import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;

import java.util.List;

public interface UserService extends Service<UserDTO> {

    void add(UserDTO userDTO, String password, String confirmPassword) throws ServiceException;

    UserDTO signIn(String login, String password) throws ServiceException;

    UserDTO getByEmail(String email) throws ServiceException;

    void changePassword(long userId, String password, String newPass, String confirmPass) throws ServiceException;

    String changePassword(long userId) throws ServiceException;

    void setRole(String userEmail, int roleId) throws ServiceException;

    List<UserDTO> getSortedUsers(String query) throws ServiceException;

    int getNumberOfRecords(String filter) throws ServiceException;

    boolean isEmailConfirmed(String parameter) throws ServiceException;

    String setVerificationCode(String email) throws ServiceException;

    void verifySecurityCode (String email, String code) throws ServiceException;

    void setAvatar(String userId, byte[] avatar) throws ServiceException;

}