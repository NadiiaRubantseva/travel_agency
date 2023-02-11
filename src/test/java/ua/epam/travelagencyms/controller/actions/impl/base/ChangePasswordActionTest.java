package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.*;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.CHANGE_PASSWORD_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.CHANGE_PASSWORD_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.PASSWORD;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_PASSWORD;

class ChangePasswordActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecutePost() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(LOGGED_USER, UserDTO.builder().id(1).build());
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        doNothing().when(userService).changePassword(ID_VALUE, PASSWORD_VALUE, PASSWORD_VALUE, PASSWORD_VALUE);
        String path = new ChangePasswordAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(CHANGE_PASSWORD_ACTION), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
    }

    @Test
    void testExecutePostIncorrectFormatException() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(LOGGED_USER, UserDTO.builder().id(1).build());
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        doThrow(new IncorrectFormatException(ENTER_CORRECT_PASSWORD)).when(userService)
                .changePassword(isA(long.class), isA(String.class), isA(String.class), isA(String.class));
        String path = new ChangePasswordAction(appContext).execute(myRequest, response);
        assertEquals(getActionToRedirect(CHANGE_PASSWORD_ACTION), path);
        assertEquals(ENTER_CORRECT_PASSWORD, myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteGet() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setGetRequest(myRequest);
        String path = new ChangePasswordAction(appContext).execute(myRequest, response);

        assertEquals(CHANGE_PASSWORD_PAGE, path);
        assertEquals(SUCCEED_UPDATE, myRequest.getAttribute(MESSAGE));
        assertEquals(ENTER_CORRECT_PASSWORD, myRequest.getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    void setPostRequest() {
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(OLD_PASSWORD)).thenReturn(PASSWORD_VALUE);
        when(request.getParameter(PASSWORD)).thenReturn(PASSWORD_VALUE);
        when(request.getParameter(CONFIRM_PASSWORD)).thenReturn(PASSWORD_VALUE);
    }

    void setGetRequest(MyRequest myRequest) {
        when(request.getMethod()).thenReturn(GET);
        HttpSession session = myRequest.getSession();
        session.setAttribute(MESSAGE, SUCCEED_UPDATE);
        session.setAttribute(ERROR, ENTER_CORRECT_PASSWORD);
    }
}