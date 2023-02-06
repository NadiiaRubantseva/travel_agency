package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.INDEX_PAGE;

class DefaultActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @Test
    void testExecute() {
        assertEquals(INDEX_PAGE, new DefaultAction().execute(request, response));
    }
}