package ua.epam.travelagencyms.model.services.implementation;

import org.apache.commons.lang3.RandomStringUtils;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.dao.UserDAO;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.entities.user.User;
import ua.epam.travelagencyms.model.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_NAME;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_SURNAME;
import static ua.epam.travelagencyms.utils.ConvertorUtil.convertDTOToUser;
import static ua.epam.travelagencyms.utils.ConvertorUtil.convertUserToDTO;
import static ua.epam.travelagencyms.utils.PasswordHashUtil.encode;
import static ua.epam.travelagencyms.utils.PasswordHashUtil.verify;
import static ua.epam.travelagencyms.utils.ValidatorUtil.*;


public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


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

    @Override
    public void delete(String userIdString) throws ServiceException {
        long userId = getUserId(userIdString);
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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

    @Override
    public void setRole(String userEmail, int roleId) throws ServiceException {
        try {
            Role role = Role.getRole(roleId);
            userDAO.setUserRole(userEmail, role);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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

    @Override
    public boolean isEmailConfirmed(String email) throws ServiceException {
        try {
            if (!userDAO.isEmailConfirmed(email)) {
//                sendEmail(email);
                throw new NotConfirmedEmailException();
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    public String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    @Override
    public String setVerificationCode(String email) throws ServiceException {
        String code = getRandom();
        try {
            userDAO.setVerificationCode(email, code);
            return code;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    public String getVerificationCode(String email) throws ServiceException {
        String code = "";
        try {
            code = userDAO.getVerificationCode(email);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return code;
    }


    public void setEmailVerified(String email) throws ServiceException {
        try {
            userDAO.setEmailVerified(email);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void verifySecurityCode(String email, String code) throws ServiceException {
        try {
            String codeInDB = userDAO.getVerificationCode(email);
            if (!codeInDB.equals(code)) {
                throw new IncorrectCodeException();
            } else {
                userDAO.setEmailVerified(email);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setAvatar(String userId, byte[] avatar) throws ServiceException {
        try {
            long id = Long.parseLong(userId);
            userDAO.setAvatar(id, avatar);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    public static long getUserId(String idString) throws ServiceException {
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
        validateEmail(userDTO.getEmail());
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

    public static void checkStrings(String... strings) throws ServiceException {
        for (String string : strings) {
            if (string == null) {
                throw new ServiceException(new NullPointerException());
            }
        }
    }

    private String generatePassword() {
        return "1aA" + RandomStringUtils.randomAlphanumeric(5);
    }
}