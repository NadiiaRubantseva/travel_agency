package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.ONE;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SEARCH_USER_BY_ID_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

class SetRoleActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecute() throws ServiceException {
        String id = ONE;
        String role = Role.USER.name();
        MyRequest myRequest = new MyRequest(request);
        when(request.getParameter(ID)).thenReturn(id);
        when(request.getParameter(ROLE)).thenReturn(role);
        when(appContext.getUserService()).thenReturn(userService);
        doNothing().when(userService).setRole(id, 1);
        UserDTO user = UserDTO.builder().role(role).build();
        when(userService.getById(id)).thenReturn(user);

        String path = new SetRoleAction(appContext).execute(myRequest, response);
        assertEquals(getActionToRedirect(SEARCH_USER_BY_ID_ACTION, ID, id), path);
        assertEquals(user, myRequest.getAttribute(USER));
    }
}