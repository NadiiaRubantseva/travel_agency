package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.DuplicateEmailException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.EDIT_PROFILE_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_PROFILE_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_EMAIL;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_NAME;

class EditProfileActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    private final Part part = mock(Part.class);
    private final InputStream inputStream = mock(InputStream.class);

    @Test
    void testExecutePost() throws ServiceException, ServletException, IOException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        UserDTO user = getTestUserDTO();
        myRequest.getSession().setAttribute(LOGGED_USER, user);
        setPostRequest();
        when(part.getInputStream()).thenReturn(inputStream);
        when(inputStream.readAllBytes()).thenReturn(IMAGE_VALUE);
        when(appContext.getUserService()).thenReturn(userService);
        doNothing().when(userService).update(isA(UserDTO.class));

        // act
        String path = new EditProfileAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(EDIT_PROFILE_ACTION), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertEquals(NAME_VALUE, user.getName());
        assertEquals(SURNAME_VALUE, user.getSurname());
        assertEquals(IMAGE_ENCODED_VALUE, user.getAvatar());
    }

    @Test
    void testExecutePostNoImage() throws ServiceException, ServletException, IOException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        UserDTO user = getTestUserDTO();
        myRequest.getSession().setAttribute(LOGGED_USER, user);
        setPostRequest();
        when(part.getInputStream()).thenReturn(inputStream);
        when(inputStream.readAllBytes()).thenReturn(new byte[]{});
        when(appContext.getUserService()).thenReturn(userService);
        doNothing().when(userService).update(isA(UserDTO.class));
        when(userService.getAvatar(isA(String.class))).thenReturn(IMAGE_ENCODED_VALUE);

        // act
        String path = new EditProfileAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(EDIT_PROFILE_ACTION), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertEquals(NAME_VALUE, user.getName());
        assertEquals(SURNAME_VALUE, user.getSurname());
        assertEquals(IMAGE_ENCODED_VALUE, user.getAvatar());
    }

    @Test
    void testExecuteBadPost() throws ServiceException, ServletException, IOException {
        MyRequest myRequest = new MyRequest(request);
        UserDTO user = getTestUserDTO();
        myRequest.getSession().setAttribute(LOGGED_USER, user);
        setPostRequest();
        when(part.getInputStream()).thenReturn(inputStream);
        when(inputStream.readAllBytes()).thenReturn(IMAGE_VALUE);
        when(appContext.getUserService()).thenReturn(userService);
        doThrow(new DuplicateEmailException()).when(userService).update(isA(UserDTO.class));
        String path = new EditProfileAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(EDIT_PROFILE_ACTION), path);
        assertEquals(DUPLICATE_EMAIL, myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteGet() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setGetRequest(myRequest);

        String path = new EditProfileAction(appContext).execute(myRequest, response);

        assertEquals(EDIT_PROFILE_PAGE, path);
        assertEquals(SUCCEED_UPDATE, myRequest.getAttribute(MESSAGE));
        assertEquals(ENTER_CORRECT_NAME, myRequest.getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(USER));
    }

    void setPostRequest() throws ServletException, IOException {
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(NAME)).thenReturn(NAME_VALUE);
        when(request.getParameter(SURNAME)).thenReturn(SURNAME_VALUE);
        when(request.getPart(AVATAR)).thenReturn(part);
    }

    void setGetRequest(MyRequest myRequest) {
        when(request.getMethod()).thenReturn(GET);
        HttpSession session = myRequest.getSession();
        session.setAttribute(MESSAGE, SUCCEED_UPDATE);
        session.setAttribute(ERROR, ENTER_CORRECT_NAME);
        session.setAttribute(CURRENT_PATH, EDIT_PROFILE_PAGE);
    }
}
