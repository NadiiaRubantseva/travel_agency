package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.NoSuchUserException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.utils.EmailSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.*;
import static ua.epam.travelagencyms.MethodsForTest.getUserDTO;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.PASSWORD_RESET_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.RESET_PASSWORD_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.CHECK_EMAIL;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.EMAIL;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.MESSAGE;
import static ua.epam.travelagencyms.exceptions.constants.Message.ERROR;
import static ua.epam.travelagencyms.exceptions.constants.Message.NO_USER;

class ResetPasswordActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);
    private final EmailSender emailSender = mock(EmailSender.class);

    @Test
    void testExecutePost() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        doNothing().when(emailSender).send(isA(String.class), isA(String.class), isA(String.class));
        when(userService.getByEmail(EMAIL_VALUE)).thenReturn(getUserDTO());
        when(userService.changePassword(getUserDTO().getId())).thenReturn(PASS_VALUE);
        String path = new ResetPasswordAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(PASSWORD_RESET_ACTION), path);
        assertEquals(EMAIL_VALUE, myRequest.getSession().getAttribute(EMAIL));
        assertEquals(CHECK_EMAIL, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        doThrow(new NoSuchUserException()).when(userService).getByEmail(isA(String.class));
        String path = new ResetPasswordAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(PASSWORD_RESET_ACTION), path);
        assertEquals(EMAIL_VALUE, myRequest.getSession().getAttribute(EMAIL));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
    }

    @Test
    void testExecuteGet() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setGetRequest(myRequest);
        String path = new ResetPasswordAction(appContext).execute(myRequest, response);

        assertEquals(RESET_PASSWORD_PAGE, path);
        assertEquals(CHECK_EMAIL, myRequest.getAttribute(MESSAGE));
        assertEquals(EMAIL_VALUE , myRequest.getAttribute(EMAIL));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(EMAIL));
    }

    void setPostRequest() {
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(EMAIL)).thenReturn(EMAIL_VALUE);
        when(request.getServletPath()).thenReturn(SERVLET_PATH);
        when(request.getRequestURL()).thenReturn(REQUEST_URL);
    }

    void setGetRequest(MyRequest myRequest) {
        when(request.getMethod()).thenReturn(GET);
        HttpSession session = myRequest.getSession();
        session.setAttribute(MESSAGE, CHECK_EMAIL);
        session.setAttribute(ERROR, NO_USER);
        session.setAttribute(EMAIL, EMAIL_VALUE);
    }
}