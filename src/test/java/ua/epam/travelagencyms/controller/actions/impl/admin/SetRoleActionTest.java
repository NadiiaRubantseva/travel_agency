package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.POST;
import static ua.epam.travelagencyms.TestUtils.ID_STRING_VALUE;
import static ua.epam.travelagencyms.TestUtils.ROLE_VALUE;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SEARCH_USER_BY_ID_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_ID;

public class SetRoleActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecute() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(ID)).thenReturn(ID_STRING_VALUE);
        when(request.getParameter(ROLE)).thenReturn(ROLE_VALUE);
        when(appContext.getUserService()).thenReturn(userService);
        doNothing().when(userService).setRole(isA(String.class), isA(int.class));

        // act
        String path = new SetRoleAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(SEARCH_USER_BY_ID_ACTION, ID, ID_STRING_VALUE), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteFail() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(ID)).thenReturn(ID_STRING_VALUE);
        when(request.getParameter(ROLE)).thenReturn(ROLE_VALUE);
        when(appContext.getUserService()).thenReturn(userService);
        doThrow(new IncorrectFormatException(ENTER_CORRECT_ID)).when(userService).setRole(isA(String.class), isA(int.class));

        // act
        String path = new SetRoleAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(SEARCH_USER_BY_ID_ACTION, ID, ID_STRING_VALUE), path);
        assertEquals(ENTER_CORRECT_ID, myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
    }
}