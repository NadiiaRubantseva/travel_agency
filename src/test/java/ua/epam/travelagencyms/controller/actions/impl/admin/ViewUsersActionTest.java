package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.utils.query.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.TestUtils.ROLE_VALUE;
import static ua.epam.travelagencyms.TestUtils.getTestUserDTO;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_USERS_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.userQueryBuilder;

public class ViewUsersActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);
    private final String ZERO = "0";
    private final String EIGHT = "8";
    private final String ASC = "ASC";
    @Test
    void testExecute() throws ServiceException {
        // arrange
        setRequest();
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getUserService()).thenReturn(userService);

        when(userService.getSortedUsers(getQueryBuilder().getQuery())).thenReturn(getUserDTOs());
        when(userService.getNumberOfRecords(getQueryBuilder().getRecordQuery())).thenReturn(10);

        // act
        String path = new ViewUsersAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_USERS_PAGE, path);
        assertEquals(getUserDTOs(), myRequest.getAttribute(USERS));
        assertEquals(0, myRequest.getAttribute(OFFSET));
        assertEquals(8, myRequest.getAttribute(RECORDS));
        assertEquals(2, myRequest.getAttribute(PAGES));
        assertEquals(1, myRequest.getAttribute(CURRENT_PAGE));
        assertEquals(1, myRequest.getAttribute(START));
        assertEquals(2, myRequest.getAttribute(END));
    }

    private void setRequest() {
        when(request.getParameter(ROLE)).thenReturn(ROLE_VALUE);
        when(request.getParameter(SORT)).thenReturn(DATE);
        when(request.getParameter(ORDER)).thenReturn(ASC);
        when(request.getParameter(OFFSET)).thenReturn(ZERO);
        when(request.getParameter(RECORDS)).thenReturn(EIGHT);
    }

    private QueryBuilder getQueryBuilder() {
        return userQueryBuilder()
                .setSortField(DATE)
                .setOrder(ASC)
                .setLimits(ZERO, EIGHT);
    }

    public static List<UserDTO> getUserDTOs(){
        List<UserDTO> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserDTO user = getTestUserDTO();
            user.setId(i);
            users.add(user);
        }
        return users;
    }
}
