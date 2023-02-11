package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.NoSuchUserException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.ConstantsForTest.*;
import static ua.epam.travelagencyms.MethodsForTest.getUserDTO;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SIGN_IN_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.PROFILE_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.PASSWORD;
import static ua.epam.travelagencyms.exceptions.constants.Message.NO_USER;

class SignInActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecutePost() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.signIn(EMAIL_VALUE, PASSWORD_VALUE)).thenReturn(getUserDTO());
        String path = new SignInAction(appContext).execute(myRequest, response);

        assertEquals(PROFILE_PAGE, path);
        assertEquals(getUserDTO(), myRequest.getSession().getAttribute(LOGGED_USER));
        assertEquals(getUserDTO().getRole(), myRequest.getSession().getAttribute(ROLE));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.signIn(EMAIL_VALUE, PASSWORD_VALUE)).thenThrow(new NoSuchUserException());
        String path = new SignInAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(SIGN_IN_ACTION), path);
        assertEquals(EMAIL_VALUE, myRequest.getSession().getAttribute(EMAIL));
        assertEquals(NO_USER, myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteGet() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setGetRequest(myRequest);
        String path = new SignInAction(appContext).execute(myRequest, response);

        assertEquals(SIGN_IN_PAGE, path);
        assertEquals(NO_USER, myRequest.getAttribute(ERROR));
        assertEquals(EMAIL_VALUE , myRequest.getAttribute(EMAIL));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(EMAIL));
    }

    void setPostRequest() {
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(EMAIL)).thenReturn(EMAIL_VALUE);
        when(request.getParameter(PASSWORD)).thenReturn(PASSWORD_VALUE);
        when(request.getServletPath()).thenReturn(SERVLET_PATH);
        when(request.getRequestURL()).thenReturn(REQUEST_URL);
    }

    void setGetRequest(MyRequest myRequest) {
        when(request.getMethod()).thenReturn(GET);
        HttpSession session = myRequest.getSession();
        session.setAttribute(ERROR, NO_USER);
        session.setAttribute(EMAIL, EMAIL_VALUE);
    }
}