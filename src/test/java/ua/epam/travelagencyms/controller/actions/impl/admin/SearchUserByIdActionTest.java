package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.NoSuchUserException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.ConstantsForTest.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SEARCH_USER_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_USER_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.NO_USER;

class SearchUserByIdActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecute() throws ServiceException {
        String id = ONE;
        when(request.getParameter(ID)).thenReturn(id);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.getById(id)).thenReturn(getTestUserDTO());

        assertEquals(VIEW_USER_BY_ADMIN_PAGE, new SearchUserByIdAction(appContext).execute(myRequest, response));
        assertEquals(getTestUserDTO(), myRequest.getAttribute(USER));
    }

    @Test
    void testExecuteNoId() throws ServiceException {
        when(request.getParameter(ID)).thenReturn("5");
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.getById("5")).thenThrow(new NoSuchUserException());

        assertEquals(SEARCH_USER_PAGE, new SearchUserByIdAction(appContext).execute(myRequest, response));
        assertEquals(NO_USER, myRequest.getAttribute(ERROR));
    }
}