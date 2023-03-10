package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectCodeException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.VERIFY_CODE_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.PROFILE_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VERIFY_EMAIL_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.INCORRECT_CODE;

public class VerifyCodeActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecutePost() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        HttpSession session = myRequest.getSession();
        session.setAttribute(LOGGED_USER, getTestUserDTO());
        when(request.getParameter(SECURITY_CODE)).thenReturn(VERIFICATION_CODE_VALUE);
        when(appContext.getUserService()).thenReturn(userService);
        doNothing().when(userService).verifySecurityCode(isA(long.class), isA(String.class));

        // act
        String path = new VerifyCodeAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(VERIFY_CODE_ACTION), path);
        assertEquals(PROFILE_PAGE, myRequest.getSession().getAttribute(CURRENT_PATH));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        HttpSession session = myRequest.getSession();
        session.setAttribute(LOGGED_USER, getTestUserDTO());
        when(request.getParameter(SECURITY_CODE)).thenReturn(VERIFICATION_CODE_VALUE);
        when(appContext.getUserService()).thenReturn(userService);
        doThrow(new IncorrectCodeException()).when(userService).verifySecurityCode(isA(long.class), isA(String.class));

        // act
        String path = new VerifyCodeAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(VERIFY_CODE_ACTION), path);
        assertEquals(VERIFY_EMAIL_PAGE, myRequest.getSession().getAttribute(CURRENT_PATH));
        assertEquals(INCORRECT_CODE, myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteGet() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        setGetRequest(myRequest);

        // act
        String path = new VerifyCodeAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VERIFY_EMAIL_PAGE, path);
        assertEquals(INCORRECT_CODE, myRequest.getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(USER));
    }

    void setGetRequest(MyRequest myRequest) {
        when(request.getMethod()).thenReturn(GET);
        HttpSession session = myRequest.getSession();
        session.setAttribute(ERROR, INCORRECT_CODE);
        session.setAttribute(CURRENT_PATH, VERIFY_EMAIL_PAGE);
    }

}
