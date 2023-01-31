package ua.epam.travelagencyms.model.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.dao.UserDAO;
import ua.epam.travelagencyms.model.entities.user.User;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.services.implementation.UserServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.Constants.*;
import static ua.epam.travelagencyms.Constants.EMAIL;
import static ua.epam.travelagencyms.Constants.ID_VALUE;
import static ua.epam.travelagencyms.Constants.INCORRECT_EMAIL;
import static ua.epam.travelagencyms.Constants.INCORRECT_NAME;
import static ua.epam.travelagencyms.Constants.INCORRECT_PASSWORD;
import static ua.epam.travelagencyms.Constants.INCORRECT_SURNAME;
import static ua.epam.travelagencyms.Constants.NAME;
import static ua.epam.travelagencyms.Constants.PASSWORD;
import static ua.epam.travelagencyms.Constants.ROLE_ID;
import static ua.epam.travelagencyms.Constants.SURNAME;
import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_EMAIL;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_EMAIL;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_NAME;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_PASSWORD;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_SURNAME;
import static ua.epam.travelagencyms.exceptions.constants.Message.PASSWORD_MATCHING;
import static ua.epam.travelagencyms.exceptions.constants.Message.WRONG_PASSWORD;
import static ua.epam.travelagencyms.utils.PasswordHashUtil.encode;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.userQueryBuilder;

class UserServiceTest {
    private final UserDAO userDAO = mock(UserDAO.class);
    private final UserService userService = new UserServiceImpl(userDAO);
    private final Long ONE = 1L;

    @Test
    void testCorrectRegistration() throws DAOException {
       doNothing().when(userDAO).add(isA(User.class));
       UserDTO userDTO = getTestUserDTO();
       assertDoesNotThrow(() -> userService.add(userDTO, PASSWORD, PASSWORD));
    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "email.com", "email@com", "email@epam.m", "email@epam.mmmmmmmm", "email@epam.444"})
    void testWrongEmailRegistration(String email) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(email);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullEmailRegistration(String email) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(email);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Pass1", "PASSWORD", "password", "12345678", "PASSWORD1", "password1", "password1Password1234"})
    void testWrongPassRegistration(String password) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, password, password));
        assertEquals(ENTER_CORRECT_PASSWORD, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullPassRegistration(String password) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, password, password));
        assertEquals(ENTER_CORRECT_PASSWORD, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "!!!", "$*$", "Julian 2", "X Æ A-12"})
    void testWrongNameRegistration(String name) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullNameRegistration(String name) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "!!!", "$*$", "Julian 2", "X Æ A-12"})
    void testWrongSurnameRegistration(String surname) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(surname);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullSurnameRegistration(String surname) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(surname);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @Test
    void testDuplicateEmailRegistration() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        DuplicateEmailException e = assertThrows(DuplicateEmailException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(DUPLICATE_EMAIL, e.getMessage());
    }

    @Test
    void testPasswordsDoNotMatch() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        PasswordMatchingException e = assertThrows(PasswordMatchingException.class ,
                () -> userService.add(userDTO, PASSWORD, INCORRECT_PASSWORD));
        assertEquals(PASSWORD_MATCHING, e.getMessage());
    }

    @Test
    void testDbIsDown() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        ServiceException e = assertThrows(ServiceException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(e.getCause(), exception);
        assertFalse(e.getMessage().contains("Duplicate entry"));
    }


    @Test
    void testSignIn() throws DAOException, ServiceException {
        User user = getTestUser();
        user.setPassword(encode(PASSWORD));
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.of(user));
        assertEquals(getTestUserDTO(), userService.signIn(EMAIL, PASSWORD));
    }

    @Test
    void testWrongEmailSignIn() throws DAOException {
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class,() -> userService.signIn(EMAIL, PASSWORD));
    }

    @Test
    void testWrongPasswordSignIn() throws DAOException {
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD));
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.of(testUser));
        assertThrows(IncorrectPasswordException.class,() -> userService.signIn(EMAIL, INCORRECT_PASSWORD));
    }

    @Test
    void testViewProfile() throws DAOException, ServiceException {
        when(userDAO.getById(ONE)).thenReturn(Optional.of(getTestUser()));
        assertEquals(getTestUserDTO(), userService.getById(String.valueOf(ONE)));
    }

    @Test
    void testViewProfileNoUser() throws DAOException {
        when(userDAO.getById(ONE)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class,() -> userService.getById(String.valueOf(ONE)));
    }

    @Test
    void testSearchUser() throws DAOException, ServiceException {
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.of(getTestUser()));
        assertEquals(getTestUserDTO(), userService.getByEmail(EMAIL));
    }

    @Test
    void testSearchNoUser() throws DAOException {
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class,() -> userService.getByEmail(EMAIL));
    }

    @Test
    void testViewUsers() throws DAOException, ServiceException {
        List<User> users = new ArrayList<>();
        List<UserDTO> userDTOs = new ArrayList<>();
        users.add(getTestUser());
        userDTOs.add(getTestUserDTO());
        when(userDAO.getAll()).thenReturn(users);
        assertIterableEquals(userDTOs, userService.getAll());
    }

    @Test
    void testEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        assertDoesNotThrow(() -> userService.update(getTestUserDTO()));
    }

    @Test
    void testWrongEmailEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(INCORRECT_EMAIL);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @Test
    void testWrongPassEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        assertDoesNotThrow(() -> userService.update(getTestUserDTO()));
    }

    @Test
    void testWrongNameEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(INCORRECT_NAME);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @Test
    void testWrongSurnameEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(INCORRECT_SURNAME);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @Test
    void testDuplicateEmailEditProfile() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        DuplicateEmailException e = assertThrows(DuplicateEmailException.class ,
                () -> userService.update(userDTO));
        assertEquals(DUPLICATE_EMAIL, e.getMessage());
    }

    @Test
    void testUpdatePassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        assertDoesNotThrow(() -> userService.changePassword(ONE, PASSWORD, PASSWORD, PASSWORD));
    }

    @Test
    void testResetPassword() throws DAOException, ServiceException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        String password = userService.changePassword(ID_VALUE);
        assertEquals(8, password.length());
        assertEquals("1aA", password.substring(0,3));
    }

    @Test
    void testEditWrongNewPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        assertThrows(IncorrectFormatException.class,
                () -> userService.changePassword(ONE, PASSWORD, WRONG_PASSWORD, WRONG_PASSWORD));
    }

    @Test
    void testEditWrongOldPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        assertThrows(IncorrectPasswordException.class,
                () -> userService.changePassword(ONE, WRONG_PASSWORD, PASSWORD, PASSWORD));
    }

    @Test
    void testEditWrongConfirmPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        assertThrows(PasswordMatchingException.class,
                () -> userService.changePassword(ONE, PASSWORD, PASSWORD, WRONG_PASSWORD));
    }

//    @Test
//    void testSetRole() throws DAOException {
//        doNothing().when(userDAO).setUserRole(isA(String.class), isA(Role.class));
//        assertDoesNotThrow(() -> userService.setRole(EMAIL, ROLE_ID));
//    }

    @Test
    void testDeleteUser() throws DAOException {
        doNothing().when(userDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> userService.delete(String.valueOf(ONE)));
    }

    @Test
    void testViewSortedUsers() throws DAOException, ServiceException {
        List<User> users = new ArrayList<>();
        List<UserDTO> userDTOs = new ArrayList<>();
        users.add(getTestUser());
        userDTOs.add(getTestUserDTO());
        String query = userQueryBuilder().getQuery();
        when(userDAO.getSorted(query)).thenReturn(users);
        assertIterableEquals(userDTOs, userService.getSortedUsers(query));
    }

    @Test
    void testNumberOfRecords() throws DAOException, ServiceException {
        String filter = userQueryBuilder().getRecordQuery();
        when(userDAO.getNumberOfRecords(filter)).thenReturn(1);
        assertEquals(1, userService.getNumberOfRecords(filter));
    }

    @Test
    void testServiceException() {
        assertNotEquals(new ServiceException(), new ServiceException());
    }

    private UserDTO getTestUserDTO() {
        return UserDTO.builder()
                .id(ONE)
                .email(EMAIL)
                .name(NAME)
                .surname(SURNAME)
                .role(ROLE_USER)
                .build();
    }

    private User getTestUser() {
        return User.builder()
                .id(ONE)
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .surname(SURNAME)
                .roleId(4)
                .build();
    }
}