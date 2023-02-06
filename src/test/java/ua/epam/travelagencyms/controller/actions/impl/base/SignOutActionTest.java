package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.LOCALE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.LOGGED_USER;

class SignOutActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @Test
    void testExecute() {
        String en = "en";
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(LOGGED_USER, UserDTO.builder().build());
        myRequest.getSession().setAttribute(LOCALE, en);
        assertEquals(SIGN_IN_PAGE, new SignOutAction().execute(myRequest, response));
        assertEquals(en, myRequest.getSession().getAttribute(LOCALE));
    }
}