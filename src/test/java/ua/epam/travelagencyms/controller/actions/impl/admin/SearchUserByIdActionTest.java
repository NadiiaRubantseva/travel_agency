package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchUserException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.MethodsForTest.getTestUserDTO;
import static ua.epam.travelagencyms.TestUtils.ID_STRING_VALUE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SEARCH_USER_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_USER_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_EMAIL;
import static ua.epam.travelagencyms.exceptions.constants.Message.NO_USER;

class SearchUserByIdActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecute() throws ServiceException {
        // arrange
        when(request.getParameter(ID)).thenReturn(ID_STRING_VALUE);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.getById(isA(String.class))).thenReturn(getTestUserDTO());

        // act
        String path = new SearchUserByIdAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_USER_BY_ADMIN_PAGE, path);
        assertEquals(getTestUserDTO(), myRequest.getAttribute(USER));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteNoSuchUser() throws ServiceException {
        // arrange
        when(request.getParameter(ID)).thenReturn(ID_STRING_VALUE);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.getById(isA(String.class))).thenThrow(new NoSuchUserException());

        // act
        String path = new SearchUserByIdAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SEARCH_USER_PAGE, path);
        assertEquals(NO_USER, myRequest.getAttribute(ERROR));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testExecuteNoIds(String id) throws ServiceException {
        // arrange
        when(request.getParameter(ID)).thenReturn(id);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getUserService()).thenReturn(userService);
        if (id==null) {
            when(userService.getById(null)).thenThrow(new IncorrectFormatException(ENTER_CORRECT_EMAIL));
        } else {
            when(userService.getById(isA(String.class))).thenThrow(new IncorrectFormatException(ENTER_CORRECT_EMAIL));
        }

        // act
        String path = new SearchUserByIdAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SEARCH_USER_PAGE, path);
        assertEquals(ENTER_CORRECT_EMAIL, myRequest.getAttribute(ERROR));
    }
}