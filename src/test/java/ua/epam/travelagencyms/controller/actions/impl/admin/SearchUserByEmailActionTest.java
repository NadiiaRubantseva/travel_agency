package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchUserException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.ConstantsForTest.EMAIL_VALUE;
import static ua.epam.travelagencyms.MethodsForTest.getTestUserDTO;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SEARCH_USER_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_USER_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_EMAIL;
import static ua.epam.travelagencyms.exceptions.constants.Message.NO_USER;

class SearchUserByEmailActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecute() throws ServiceException {
        // arrange
        when(request.getParameter(EMAIL)).thenReturn(EMAIL_VALUE);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.getByEmail(isA(String.class))).thenReturn(getTestUserDTO());

        // act
        String path = new SearchUserByEmailAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_USER_BY_ADMIN_PAGE, path);
        assertEquals(getTestUserDTO(), myRequest.getAttribute(USER));
    }

    @ParameterizedTest
    @ValueSource(strings = {"email.com", "@email.com", "email@email"})
    void testExecuteBadEmails(String email) throws ServiceException {
        // arrange
        when(request.getParameter(EMAIL)).thenReturn(email);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.getByEmail(isA(String.class))).thenThrow(new IncorrectFormatException(ENTER_CORRECT_EMAIL));

        // act
        String path = new SearchUserByEmailAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SEARCH_USER_PAGE, path);
        assertEquals(ENTER_CORRECT_EMAIL, myRequest.getAttribute(ERROR));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testExecuteNoEmails(String email) throws ServiceException {
        // arrange
        when(request.getParameter(EMAIL)).thenReturn(email);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getUserService()).thenReturn(userService);

        if (email==null) {
            when(userService.getByEmail(null)).thenThrow(new IncorrectFormatException(ENTER_CORRECT_EMAIL));
        } else {
            when(userService.getByEmail(isA(String.class))).thenThrow(new IncorrectFormatException(ENTER_CORRECT_EMAIL));
        }

        // act
        String path = new SearchUserByEmailAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SEARCH_USER_PAGE, path);
        assertEquals(ENTER_CORRECT_EMAIL, myRequest.getAttribute(ERROR));
    }

    @ParameterizedTest
    @ValueSource(strings = {"noUser@epam.com", "noUser2@epam.com"})
    void testExecuteNoUser(String email) throws ServiceException {
        // arrange
        when(request.getParameter(EMAIL)).thenReturn(email);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.getByEmail(isA(String.class))).thenThrow(new NoSuchUserException());

        // act
        String path = new SearchUserByEmailAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SEARCH_USER_PAGE, path);
        assertEquals(NO_USER, myRequest.getAttribute(ERROR));
    }
}