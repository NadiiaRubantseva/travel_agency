package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.NoSuchUserException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.ConstantsForTest.*;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.VIEW_USERS_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_DELETE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.MESSAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.USER_ID;

class DeleteUserActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecute() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(USER_ID)).thenReturn(ONE);
        when(appContext.getUserService()).thenReturn(userService);
        doNothing().when(userService).delete(ONE);
        String path = new DeleteUserAction(appContext).execute(myRequest, response);
        assertEquals(getActionToRedirect(VIEW_USERS_ACTION), path);
        assertEquals(SUCCEED_DELETE, myRequest.getSession().getAttribute(MESSAGE));
    }

    @Test
    void testExecuteNull() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(USER_ID)).thenReturn(null);
        when(appContext.getUserService()).thenReturn(userService);
        doThrow(NoSuchUserException.class).when(userService).delete(null);
        Executable exec = () -> new DeleteUserAction(appContext).execute(myRequest, response);
        assertThrows(NoSuchUserException.class, exec);
    }

}