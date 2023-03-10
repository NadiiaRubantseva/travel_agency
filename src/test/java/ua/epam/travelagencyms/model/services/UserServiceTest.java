package ua.epam.travelagencyms.model.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.dao.UserDAO;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.entities.user.User;
import ua.epam.travelagencyms.model.services.implementation.UserServiceImpl;
import ua.epam.travelagencyms.utils.ConvertorUtil;
import ua.epam.travelagencyms.utils.PasswordHashUtil;
import ua.epam.travelagencyms.utils.ValidatorUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.*;
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
        try (MockedStatic<ValidatorUtil> validator = mockStatic(ValidatorUtil.class);
             MockedStatic<ConvertorUtil> convertor = mockStatic(ConvertorUtil.class);
             MockedStatic<PasswordHashUtil> passwordHash = mockStatic(PasswordHashUtil.class)) {
            validator.when(() -> ValidatorUtil.validateEmail(anyString())).thenAnswer(invocationOnMock -> null);
            validator.when(() -> ValidatorUtil.validateName(anyString(), anyString())).thenAnswer(invocationOnMock -> null);
            validator.when(() -> ValidatorUtil.validatePassword(anyString())).thenAnswer(invocationOnMock -> null);
            convertor.when(() -> ConvertorUtil.convertDTOToUser(userDTO)).thenReturn(getTestUser());
            passwordHash.when(() -> PasswordHashUtil.encode(anyString())).thenReturn(PASSWORD_VALUE);
            assertDoesNotThrow(() -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
            validator.verify(() -> ValidatorUtil.validateEmail(anyString()));
            validator.verify(() -> ValidatorUtil.validateName(anyString(), anyString()), times(2));
            validator.verify(() -> ValidatorUtil.validatePassword(anyString()));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "email.com", "email@com", "email@epam.m", "email@epam.mmmmmmmm", "email@epam.444"})
    void testWrongEmailRegistration(String email) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(email);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullEmailRegistration(String email) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(email);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Pass1", "PASSWORD", "password", "12345678", "PASSWORD1", "password1", "password1Password1234"})
    void testWrongPassRegistration(String password) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, password, password));
        assertEquals(ENTER_CORRECT_PASSWORD, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullPassRegistration(String password) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, password, password));
        assertEquals(ENTER_CORRECT_PASSWORD, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "!!!", "$*$", "Julian 2", "X Æ A-12"})
    void testWrongNameRegistration(String name) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullNameRegistration(String name) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "!!!", "$*$", "Julian 2", "X Æ A-12"})
    void testWrongSurnameRegistration(String surname) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(surname);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullSurnameRegistration(String surname) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(surname);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @Test
    void testDuplicateEmailRegistration() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        DuplicateEmailException e = assertThrows(DuplicateEmailException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(DUPLICATE_EMAIL, e.getMessage());
    }

    @Test
    void testPasswordsDoNotMatch() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        PasswordMatchingException e = assertThrows(PasswordMatchingException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, INCORRECT_PASSWORD_VALUE));
        assertEquals(PASSWORD_MATCHING, e.getMessage());
    }

    @Test
    void testDbIsDown() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        ServiceException e = assertThrows(ServiceException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testSignIn() throws DAOException, ServiceException {
        User user = getTestUser();
        user.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getByEmail(EMAIL_VALUE)).thenReturn(Optional.of(user));
        assertEquals(getTestUserDTO(), userService.signIn(EMAIL_VALUE, PASSWORD_VALUE));
    }

    @Test
    void testWrongEmailSignIn() throws DAOException {
        when(userDAO.getByEmail(EMAIL_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.signIn(EMAIL_VALUE, PASSWORD_VALUE));
    }

    @Test
    void testWrongPasswordSignIn() throws DAOException {
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getByEmail(EMAIL_VALUE)).thenReturn(Optional.of(testUser));
        assertThrows(IncorrectPasswordException.class, () -> userService.signIn(EMAIL_VALUE, INCORRECT_PASSWORD_VALUE));
    }

    @Test
    void testWrongDbSignIn() throws DAOException {
        when(userDAO.getByEmail(EMAIL_VALUE)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> userService.signIn(EMAIL_VALUE, PASSWORD_VALUE));
    }

    @Test
    void testNullSignIn() {
        assertThrows(ServiceException.class, () -> userService.signIn(null, null));
    }

    @Test
    void testViewProfile() throws DAOException, ServiceException {
        when(userDAO.getById(ONE)).thenReturn(Optional.of(getTestUser()));
        assertEquals(getTestUserDTO(), userService.getById(String.valueOf(ONE)));
    }

    @Test
    void testViewProfileNoUser() throws DAOException {
        when(userDAO.getById(ONE)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.getById(String.valueOf(ONE)));
    }

    @ParameterizedTest
    @EmptySource
    void testViewProfileEmptyId(String id) {
        assertThrows(IncorrectFormatException.class, () -> userService.getById(id));
    }

    @Test
    void testViewProfileWrongId2() {
        assertThrows(IncorrectFormatException.class, () -> userService.getById("id"));
    }

    @Test
    void testSQLErrorViewProfile() throws DAOException {
        when(userDAO.getById(ONE)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> userService.getById(String.valueOf(ONE)));
    }

    @Test
    void testNullViewProfile() {
        assertThrows(ServiceException.class, () -> userService.getById(String.valueOf(ONE)));
    }

    @Test
    void testSearchUser() throws DAOException, ServiceException {
        when(userDAO.getByEmail(EMAIL_VALUE)).thenReturn(Optional.of(getTestUser()));
        assertEquals(getTestUserDTO(), userService.getByEmail(EMAIL_VALUE));
    }

    @Test
    void testSearchNoUser() throws DAOException {
        when(userDAO.getByEmail(EMAIL_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.getByEmail(EMAIL_VALUE));
    }

    @Test
    void testSearchIncorrectFormat() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class,
                () -> userService.getByEmail(INCORRECT_EMAIL_VALUE));
        assertEquals(ENTER_CORRECT_EMAIL, exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testSearchIncorrectFormat2(String email) {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class,
                () -> userService.getByEmail(email));
        assertEquals(ENTER_CORRECT_EMAIL, exception.getMessage());
    }

    @Test
    void testSQLErrorSearchUser() throws DAOException {
        when(userDAO.getByEmail(EMAIL_VALUE)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> userService.getByEmail(EMAIL_VALUE));
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
    void testSQLErrorViewUsers() throws DAOException {
        when(userDAO.getAll()).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, userService::getAll);
    }

    @Test
    void testEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        assertDoesNotThrow(() -> userService.update(getTestUserDTO()));
    }

    @Test
    void testSQLErrorEditProfile() throws DAOException {

        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        ServiceException e = assertThrows(ServiceException.class, () -> userService.update(userDTO));
        assertEquals(e.getCause(), exception);

    }

    @Test
    void testWrongEmailEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(INCORRECT_EMAIL_VALUE);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
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
        userDTO.setName(INCORRECT_NAME_VALUE);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullNameEditProfile(String name) throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "!!!", "$*$", "Julian 2", "X Æ A-12"})
    void testWrongSurnameEditProfile(String surname) throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(surname);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullSurnameEditProfile(String surname) throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(surname);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @Test
    void testDuplicateEmailEditProfile() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        DuplicateEmailException e = assertThrows(DuplicateEmailException.class,
                () -> userService.update(userDTO));
        assertEquals(DUPLICATE_EMAIL, e.getMessage());
    }


    @Test
    void testUpdatePassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        try (MockedStatic<ValidatorUtil> validator = mockStatic(ValidatorUtil.class)) {
            validator.when(() -> ValidatorUtil.validatePassword(anyString()))
                    .thenAnswer(invocation -> null);
            assertDoesNotThrow(() -> userService.changePassword(ONE, PASSWORD_VALUE, PASSWORD_VALUE, PASSWORD_VALUE));
        }
    }

    @Test
    void testUpdatePasswordException() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        try (MockedStatic<ValidatorUtil> validator = mockStatic(ValidatorUtil.class)) {
            validator.when(() -> ValidatorUtil.validatePassword(anyString()))
                    .thenThrow(IncorrectFormatException.class);
            assertThrows(IncorrectFormatException.class,
                    () -> userService.changePassword(ONE, PASSWORD_VALUE, PASSWORD_VALUE, PASSWORD_VALUE));
        }
    }

    @Test
    void testResetPassword() throws DAOException, ServiceException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        String password = userService.changePassword(ID_VALUE);
        assertEquals(11, password.length());
        assertEquals("1aA", password.substring(0, 3));
    }

    @Test
    void testEditWrongNewPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        assertThrows(IncorrectFormatException.class,
                () -> userService.changePassword(ONE, PASSWORD_VALUE, INCORRECT_PASSWORD_VALUE, INCORRECT_PASSWORD_VALUE));
    }

    @Test
    void testEditWrongOldPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        assertThrows(IncorrectPasswordException.class,
                () -> userService.changePassword(ONE, INCORRECT_PASSWORD_VALUE, PASSWORD_VALUE, PASSWORD_VALUE));
    }

    @Test
    void testEditWrongConfirmPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        assertThrows(PasswordMatchingException.class,
                () -> userService.changePassword(ONE, PASSWORD_VALUE, PASSWORD_VALUE, INCORRECT_PASSWORD_VALUE));
    }

    @Test
    void testEditNullPassword() {
        assertThrows(ServiceException.class,
                () -> userService.changePassword(ONE, null, null, null));
    }

    @Test
    void testSQLErrorEditPassword() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).getById(isA(Long.class));
        ServiceException e = assertThrows(ServiceException.class,
                () -> userService.changePassword(ONE, PASSWORD_VALUE, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testSQLErrorEditPassword2() throws DAOException {
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).updatePassword(isA(User.class));
        ServiceException e = assertThrows(ServiceException.class,
                () -> userService.changePassword(ONE, PASSWORD_VALUE, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testSQLErrorEditPassword3() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).updatePassword(isA(User.class));
        ServiceException e = assertThrows(ServiceException.class,
                () -> userService.changePassword(ONE));
        assertEquals(e.getCause(), exception);
        assertFalse(e.getMessage().contains("Duplicate entry"));
    }

    @Test
    void testSetRole() throws DAOException {
        doNothing().when(userDAO).setUserRole(isA(long.class), isA(Role.class));
        assertDoesNotThrow(() -> userService.setRole(ID_STRING_VALUE, ROLE_ID_VALUE));
    }

    @Test
    void testSQLErrorSetRole() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).setUserRole(isA(long.class), isA(Role.class));
        ServiceException e = assertThrows(ServiceException.class, () -> userService.setRole(ID_STRING_VALUE, ROLE_ID_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testDeleteUser() throws DAOException {
        doNothing().when(userDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> userService.delete(String.valueOf(ONE)));
    }

    @Test
    void testSQLErrorDeleteUser() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).delete(isA(Long.class));
        ServiceException e = assertThrows(ServiceException.class, () -> userService.delete(String.valueOf(ONE)));
        assertEquals(e.getCause(), exception);
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
    void testSQLErrorViewSortedUsers() throws DAOException {
        when(userDAO.getSorted(QUERY)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> userService.getSortedUsers(QUERY));
    }

    @Test
    void testNumberOfRecords() throws DAOException, ServiceException {
        String filter = userQueryBuilder().getRecordQuery();
        when(userDAO.getNumberOfRecords(filter)).thenReturn(1);
        assertEquals(1, userService.getNumberOfRecords(filter));
    }

    @Test
    void testSQLErrorNumberOfRecords() throws DAOException {
        when(userDAO.getNumberOfRecords(FILTER)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> userService.getNumberOfRecords(FILTER));
    }

    @Test
    void testServiceException() {
        assertNotEquals(new ServiceException(), new ServiceException());
    }

    @Test
    void testSetVerificationCode() throws DAOException {
        doNothing().when(userDAO).setVerificationCode(isA(long.class), isA(String.class));
        assertDoesNotThrow(() -> userService.setVerificationCode(ID_VALUE));
    }

    @Test
    void testSQLErrorSetVerificationCode() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).setVerificationCode(isA(long.class), isA(String.class));
        ServiceException e = assertThrows(ServiceException.class, () -> userService.setVerificationCode(ID_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testSetAvatar() throws ServiceException, DAOException {
        doNothing().when(userDAO).setAvatar(isA(long.class), isA(byte[].class));
        userService.setAvatar(ID_STRING_VALUE, AVATAR_VALUE);
        verify(userDAO).setAvatar(ONE, AVATAR_VALUE);
    }

    @Test
    void testSetAvatarServiceException() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).setAvatar(isA(long.class), isA(byte[].class));
        ServiceException e = assertThrows(ServiceException.class, () -> userService.setAvatar(ID_STRING_VALUE, AVATAR_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    public void testIsBlocked_ReturnsTrue() throws ServiceException, DAOException {
        boolean expected = true;
        when(userDAO.isBlocked(ONE)).thenReturn(expected);
        boolean result = userService.isBlocked(ONE);
        assertEquals(expected, result);
    }

    @Test
    void testIsBlocked_ReturnsFalse() throws ServiceException, DAOException {
        boolean expected = false;
        when(userDAO.isBlocked(ONE)).thenReturn(expected);
        boolean result = userService.isBlocked(ONE);
        assertEquals(expected, result);
    }

    @Test
    void testIsBlockedSQLException() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).isBlocked(isA(long.class));
        ServiceException e = assertThrows(ServiceException.class, () -> userService.isBlocked(ONE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testSetStatus_SetsStatusNotBlocked() throws ServiceException, DAOException {
        String status = "Active";
        byte expected = 0;
        userService.setStatus(ID_STRING_VALUE, status);
        verify(userDAO).setStatus(ONE, expected);
    }

    @Test
    void testSetStatus_SetsStatusBlocked() throws ServiceException, DAOException {
        String status = "Blocked";
        byte expected = 1;
        userService.setStatus(ID_STRING_VALUE, status);
        verify(userDAO).setStatus(ONE, expected);

    }

    @Test
    void testSetStatusSQLException() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).setStatus(isA(Long.class), isA(byte.class));
        ServiceException e = assertThrows(ServiceException.class, () -> userService.setStatus(ID_STRING_VALUE, ID_STRING_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testVerifyCorrectSecurityCode() throws DAOException  {
        when(userDAO.getVerificationCode(ONE)).thenReturn(VERIFICATION_CODE_VALUE);
        assertDoesNotThrow(() -> userService.verifySecurityCode(ONE, VERIFICATION_CODE_VALUE));
        verify(userDAO).setEmailVerified(ONE);
    }

    @Test
    void testVerifyIncorrectSecurityCode() throws DAOException {
        when(userDAO.getVerificationCode(ONE)).thenReturn(VERIFICATION_CODE_VALUE);
        IncorrectCodeException e = assertThrows(IncorrectCodeException.class,
                () -> userService.verifySecurityCode(ONE, INCORRECT_VERIFICATION_CODE_VALUE));
        assertTrue(e.getMessage().contains(INCORRECT_CODE));
    }

    @Test
    void testVerifySqlExceptionVerifySecurityCode() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).getVerificationCode(isA(long.class));
        ServiceException e = assertThrows(ServiceException.class,
                () -> userService.verifySecurityCode(ONE, VERIFICATION_CODE_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testSetDiscount() throws DAOException {
        doNothing().when(userDAO).setDiscount(isA(int.class), isA(long.class));
        assertDoesNotThrow(() -> userService.setDiscount(DISCOUNT_VALUE, ID_STRING_VALUE));
    }
    @Test
    void testSqlExceptionSetDiscount() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).setDiscount(isA(int.class), isA(long.class));
        ServiceException e = assertThrows(ServiceException.class,
                () -> userService.setDiscount(DISCOUNT_VALUE, ID_STRING_VALUE));
        assertEquals(e.getCause(), exception);
    }
}