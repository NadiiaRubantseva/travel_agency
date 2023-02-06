package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.ERROR_PAGE;

class ErrorActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @Test
    void testExecute() {
        assertEquals(ERROR_PAGE, new ErrorAction().execute(request, response));
    }
}