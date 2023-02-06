package ua.epam.travelagencyms.model.services.implementation;

import lombok.RequiredArgsConstructor;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.dao.UserDAO;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.entities.user.User;
import ua.epam.travelagencyms.model.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_NAME;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_SURNAME;
import static ua.epam.travelagencyms.utils.ConvertorUtil.convertDTOToUser;
import static ua.epam.travelagencyms.utils.ConvertorUtil.convertUserToDTO;
import static ua.epam.travelagencyms.utils.PasswordHashUtil.encode;
import static ua.epam.travelagencyms.utils.PasswordHashUtil.verify;
import static ua.epam.travelagencyms.utils.ValidatorUtil.*;

/**
 * Implementation of UserService interface.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /** Contains userDAO field to work with UserDAO */
    private final UserDAO userDAO;
    private static final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final Random random = new Random();

    /**
     * Obtains instance of User from DAO by id. Checks if id valid. Converts User to UserDTO
     * @param userIdString - id as a String to validate and convert to long
     * @return UserDTO instance
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchUserException
     */
    @Override
    public UserDTO getById(String userIdString) throws ServiceException {
        UserDTO userDTO;
        long userId = getUserId(userIdString);
        try {
            User user = userDAO.getById(userId).orElseThrow(NoSuchUserException::new);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    /**
     * Obtains list of all instances of User from DAO. Converts Users to UserDTOs
     * @return List of UserDTOs
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public List<UserDTO> getAll() throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            List<User> users = userDAO.getAll();
            users.forEach(user -> userDTOS.add(convertUserToDTO(user)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTOS;
    }

    /**
     * Updates User's email, name, surname. Validate UserDTO. Converts UserDTO to User
     * @param userDTO - UserDTO that contains user's id, email, name and surname.
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException or
     * DuplicateEmailException
     */
    @Override
    public void update(UserDTO userDTO) throws ServiceException {
        validateUser(userDTO);
        User user = convertDTOToUser(userDTO);
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            checkExceptionType(e);
        }
    }

    /**
     * Deletes User entity from database. Validate id.
     * @param userIdString - id as a String
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchUserException
     */
    @Override
    public void delete(String userIdString) throws ServiceException {
        long userId = getUserId(userIdString);
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets UserDTO from action and calls DAO to add relevant entity. Validate user's fields, passwords.
     * Encode password for database. Converts UserDTO to User
     * @param userDTO - DTO to be added as User to database
     * @param password - password to be added to User
     * @param confirmPassword - will check if passwords match
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException with specific message,
     * PasswordMatchingException, DuplicateEmailException.
     */
    @Override
    public void add(UserDTO userDTO, String password, String confirmPassword) throws ServiceException {
        validateUser(userDTO);
        validatePassword(password);
        checkPasswordMatching(password, confirmPassword);
        User user = convertDTOToUser(userDTO);
        user.setPassword(encode(password));
        try {
            userDAO.add(user);
        } catch (DAOException e) {
            checkExceptionType(e);
        }
    }

    /**
     * Checks if parameters are correct. Obtains necessary User entity and checks if password matches.
     * Converts UserDTO to User
     * @param email - to find user in database
     * @param password - to check if matches with user password
     * @return UserDTO - that matches this User entity
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchUserException, IncorrectPasswordException.
     */
    @Override
    public UserDTO signIn(String email, String password) throws ServiceException {
        checkStrings(email, password);
        UserDTO userDTO;
        try {
            User user = userDAO.getByEmail(email).orElseThrow(NoSuchUserException::new);
            verify(user.getPassword(), password);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    /**
     * Obtains instance of User from DAO by email. Checks if email valid. Converts User to UserDTO
     * @param email - email to find User's instance
     * @return DTO instance
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchUserException
     */
    @Override
    public UserDTO getByEmail(String email) throws ServiceException {
        validateEmail(email);
        UserDTO userDTO;
        try {
            User user = userDAO.getByEmail(email).orElseThrow(NoSuchUserException::new);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    /**
     * Updates User's password. Validate passwords. Encode new password
     * @param userId - id to find user by
     * @param password - old password
     * @param newPass - new password
     * @param confirmPass - should match new password
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException,
     * IncorrectPasswordException, NoSuchUserException, PasswordMatchingException
     */
    @Override
    public void changePassword(long userId, String password, String newPass, String confirmPass) throws ServiceException {
        checkStrings(password, newPass, confirmPass);
        try {
            User user = userDAO.getById(userId).orElseThrow(NoSuchUserException::new);
            verify(user.getPassword(), password);
            checkPasswordMatching(newPass, confirmPass);
            validatePassword(newPass);
            User userToUpdate = User.builder().id(userId).password(encode(newPass)).build();
            userDAO.updatePassword(userToUpdate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Calls DAO to update User with new password id. Generate new password. Encode new password.
     * @param userId id to find user by
     * @return new password for user
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException, NoSuchUserException.
     */
    @Override
    public String changePassword(long userId) throws ServiceException {
        String newPass = generatePassword();
        try {
            User user = User.builder().id(userId).password(encode(newPass)).build();
            userDAO.updatePassword(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return newPass;
    }

    /**
     * Calls DAO to set new user role
     * @param userId - to find user by id
     * @param roleId new role for user
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public void setRole(String userId, int roleId) throws ServiceException {
        try {
            long id = Long.parseLong(userId);
            Role role = Role.getRole(roleId);
            userDAO.setUserRole(id, role);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Calls DAO to get sorted, filtered and limited list of DTOs. Converts Users to UserDTOs
     * @param query - to obtain necessary DTOs
     * @return List of UserDTOs that match demands
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public List<UserDTO> getSortedUsers(String query) throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            List<User> users = userDAO.getSorted(query);
            users.forEach(user -> userDTOS.add(convertUserToDTO(user)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTOS;
    }

    /**
     * Calls DAO to get number of all records that match filter
     * @param filter - conditions for such Users
     * @return number of records that match demands
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public int getNumberOfRecords(String filter) throws ServiceException {
        int records;
        try {
            records = userDAO.getNumberOfRecords(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return records;
    }

    /**
     * Calls DAO to check if user email is verified
     * @param id - to find user by id
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public boolean isEmailVerified(long id) throws ServiceException {
        try {
            return userDAO.isEmailVerified(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isEmailNotVerified(long id) throws ServiceException {
        return !isEmailVerified(id);
    }
    private String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    /**
     * Calls DAO to set verification code
     * @param id - to find user by id
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public String setVerificationCode(long id) throws ServiceException {
        String code = getRandom();
        try {
            userDAO.setVerificationCode(id, code);
            return code;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Calls DAO to check if provided verification code matched the one from database
     * @param id - to find user by id
     * @param code - verification code received
     * @throws ServiceException - may wrap DAOException
     * @throws IncorrectCodeException - if code entered by user does not match database.
     */
    @Override
    public void verifySecurityCode(long id, String code) throws ServiceException {
        try {
            String codeInDB = userDAO.getVerificationCode(id);
            if (codeInDB.equals(code)) {
                userDAO.setEmailVerified(id);
            } else {
                throw new IncorrectCodeException();
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Calls DAO to set user avatar
     * @param userId - to find user by id
     * @param avatar - image in byte representation
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public void setAvatar(String userId, byte[] avatar) throws ServiceException {
        try {
            long id = Long.parseLong(userId);
            userDAO.setAvatar(id, avatar);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Calls DAO to check if user is blocked
     * @param id - to find user by id
     * @return - if yes - true, otherwise false.
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public boolean isBlocked(long id) throws ServiceException {
        try {
            return userDAO.isBlocked(id);
        } catch (DAOException e) {
            throw new ServiceException();
        }
    }

    /**
     * Calls DAO to set user status
     * @param id - to find user by id
     * @param status - status value
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public void setStatus(long id, String status) throws ServiceException {
        byte statusId = 0;
        if (status.equals("BLOCKED")) {
            statusId = 1;
        }
        try {
            userDAO.setStatus(id, statusId);
        } catch (DAOException e) {
            throw new ServiceException();
        }
    }

    @Override
    public void setDiscount(int discount, long userId) throws ServiceException {
        try {
            userDAO.setDiscount(discount, userId);
        } catch (DAOException e) {
            throw new ServiceException();
        }
    }

    @Override
    public int getDiscount(long userId) throws ServiceException {
        try {
            return userDAO.getDiscount(userId);
        } catch (DAOException e) {
            throw new ServiceException();
        }
    }


    private static long getUserId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchUserException());
    }

    private static long checkId(String idString, ServiceException exception) throws ServiceException {
        long eventId;
        try {
            eventId = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw exception;
        }
        return eventId;
    }

    private void validateUser(UserDTO userDTO) throws IncorrectFormatException {
        validateName(userDTO.getName(), ENTER_CORRECT_NAME);
        validateName(userDTO.getSurname(), ENTER_CORRECT_SURNAME);
    }

    private void checkExceptionType(DAOException e) throws ServiceException {
        if (e.getMessage().contains("Duplicate")) {
            throw new DuplicateEmailException();
        } else {
            throw new ServiceException(e);
        }
    }

    private static void checkStrings(String... strings) throws ServiceException {
        for (String string : strings) {
            if (string == null) {
                throw new ServiceException(new NullPointerException());
            }
        }
    }

    /**
     * Obtains new password for User
     * @return generated password
     */
    private String generatePassword() {
        return IntStream.generate(() -> random.nextInt(SYMBOLS.length()))
                .map(SYMBOLS::charAt)
                .limit(8)
                .collect(() -> new StringBuilder("1aA"), StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}