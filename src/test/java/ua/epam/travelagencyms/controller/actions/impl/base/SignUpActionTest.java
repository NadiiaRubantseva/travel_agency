package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.DuplicateEmailException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.utils.EmailSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SIGN_UP_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SIGN_UP_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_REGISTER;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_EMAIL;

class SignUpActionTest {
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
        doNothing().when(userService).add(isA(UserDTO.class), isA(String.class), isA(String.class));
        doNothing().when(emailSender).send(isA(String.class), isA(String.class), isA(String.class));
        String path = new SignUpAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(SIGN_UP_ACTION), path);
        assertEquals(SUCCEED_REGISTER, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        doThrow(new DuplicateEmailException()).when(userService).add(isA(UserDTO.class), isA(String.class), isA(String.class));
        doNothing().when(emailSender).send(isA(String.class), isA(String.class), isA(String.class));
        UserDTO userDTO = UserDTO.builder().email(EMAIL_VALUE).name(NAME_VALUE).surname(SURNAME_VALUE).build();
        String path = new SignUpAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(SIGN_UP_ACTION), path);
        assertEquals(userDTO, myRequest.getSession().getAttribute(USER));
        assertEquals(DUPLICATE_EMAIL, myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(EMAIL));
    }

    @Test
    void testExecuteGet() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setGetRequest(myRequest);
        String path = new SignUpAction(appContext).execute(myRequest, response);

        assertEquals(SIGN_UP_PAGE, path);
        assertEquals(getTestUserDTO() , myRequest.getAttribute(USER));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(USER));
    }

    void setPostRequest() {
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(EMAIL)).thenReturn(EMAIL_VALUE);
        when(request.getParameter(NAME)).thenReturn(NAME_VALUE);
        when(request.getParameter(SURNAME)).thenReturn(SURNAME_VALUE);
        when(request.getParameter(PASSWORD)).thenReturn(PASSWORD_VALUE);
        when(request.getParameter(CONFIRM_PASSWORD)).thenReturn(PASSWORD_VALUE);
    }

    void setGetRequest(MyRequest myRequest) {
        when(request.getMethod()).thenReturn(GET);
        HttpSession session = myRequest.getSession();
        session.setAttribute(MESSAGE, SUCCEED_REGISTER);
        session.setAttribute(USER, getTestUserDTO());
        session.setAttribute(CURRENT_PATH, SIGN_UP_PAGE);
    }
}