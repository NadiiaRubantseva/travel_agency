package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.*;
import static ua.epam.travelagencyms.MethodsForTest.getUserDTO;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.EDIT_PROFILE_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_PROFILE_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.NAME;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.SURNAME;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_NAME;

class EditProfileActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecutePost() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        UserDTO user = getUserDTO();
        myRequest.getSession().setAttribute(LOGGED_USER, user);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        doNothing().when(userService).update(isA(UserDTO.class));
        String path = new EditProfileAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(EDIT_PROFILE_ACTION), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
        assertEquals(NEW_NAME, user.getName());
        assertEquals(NEW_SURNAME, user.getSurname());
    }

    @Test
    void testExecuteGet() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setGetRequest(myRequest);
        String path = new EditProfileAction(appContext).execute(myRequest, response);

        assertEquals(EDIT_PROFILE_PAGE, path);
        assertEquals(SUCCEED_UPDATE, myRequest.getAttribute(MESSAGE));
        assertEquals(ENTER_CORRECT_NAME, myRequest.getAttribute(ERROR));
        assertEquals(getNewUserDTO(), myRequest.getAttribute(USER));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(USER));
    }

    private static UserDTO getNewUserDTO() {
        return UserDTO.builder().id(1).email(NEW_EMAIL).name(NEW_NAME).surname(NEW_SURNAME).build();
    }

    void setPostRequest() {
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(EMAIL)).thenReturn(NEW_EMAIL);
        when(request.getParameter(NAME)).thenReturn(NEW_NAME);
        when(request.getParameter(SURNAME)).thenReturn(NEW_SURNAME);
    }

    void setGetRequest(MyRequest myRequest) {
        when(request.getMethod()).thenReturn(GET);
        HttpSession session = myRequest.getSession();
        session.setAttribute(MESSAGE, SUCCEED_UPDATE);
        session.setAttribute(ERROR, ENTER_CORRECT_NAME);
        session.setAttribute(USER, getNewUserDTO());
    }
}