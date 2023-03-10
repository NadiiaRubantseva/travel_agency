package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.NoSuchUserException;
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
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SIGN_IN_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.VIEW_TOURS_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.ADMIN;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.NO_USER;

public class SignInActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);
    private final EmailSender emailSender = mock(EmailSender.class);

    @Test
    void testExecutePostSignInByVerifiedUser() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.signIn(isA(String.class), isA(String.class))).thenReturn(getTestUserDTO());

        // act
        String path = new SignInAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(VIEW_TOURS_ACTION), path);
        assertEquals(getTestUserDTO(), myRequest.getSession().getAttribute(LOGGED_USER));
        assertEquals(getTestUserDTO().getRole(), myRequest.getSession().getAttribute(ROLE));
    }

    @Test
    void testExecutePostSignInByAdmin() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        UserDTO userDTO = getTestUserDTO();
        userDTO.setRole(ADMIN);
        when(userService.signIn(isA(String.class), isA(String.class))).thenReturn(userDTO);

        // act
        String path = new SignInAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(SIGN_IN_ACTION), path);
        assertEquals(userDTO, myRequest.getSession().getAttribute(LOGGED_USER));
        assertEquals(userDTO.getRole(), myRequest.getSession().getAttribute(ROLE));
    }

    @Test
    void testExecutePostSignInByNotVerifiedUser() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        UserDTO userDTO = getTestUserDTO();
        userDTO.setIsEmailVerified("No");
        when(userService.signIn(isA(String.class), isA(String.class))).thenReturn(userDTO);
        when(userService.setVerificationCode(isA(long.class))).thenReturn(VERIFICATION_CODE_VALUE);
        doNothing().when(emailSender).send(isA(String.class), isA(String.class), isA(String.class));

        // act
        String path = new SignInAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(SIGN_IN_ACTION), path);
        assertEquals(userDTO, myRequest.getSession().getAttribute(LOGGED_USER));
        assertEquals(userDTO.getRole(), myRequest.getSession().getAttribute(ROLE));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.signIn(EMAIL_VALUE, PASSWORD_VALUE)).thenThrow(new NoSuchUserException());

        // act
        String path = new SignInAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(SIGN_IN_ACTION), path);
        assertEquals(EMAIL_VALUE, myRequest.getSession().getAttribute(EMAIL));
        assertEquals(NO_USER, myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteGet() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        setGetRequest(myRequest);

        // act
        new SignInAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(NO_USER, myRequest.getAttribute(ERROR));
        assertEquals(SIGN_IN_PAGE, myRequest.getSession().getAttribute(CURRENT_PATH));
        assertEquals(EMAIL_VALUE , myRequest.getAttribute(EMAIL));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(EMAIL));
    }

    void setPostRequest() {
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(EMAIL)).thenReturn(EMAIL_VALUE);
        when(request.getParameter(PASSWORD)).thenReturn(PASSWORD_VALUE);
    }

    void setGetRequest(MyRequest myRequest) {
        when(request.getMethod()).thenReturn(GET);
        HttpSession session = myRequest.getSession();
        session.setAttribute(ERROR, NO_USER);
        session.setAttribute(EMAIL, EMAIL_VALUE);
        session.setAttribute(CURRENT_PATH, SIGN_IN_PAGE);
    }
}
