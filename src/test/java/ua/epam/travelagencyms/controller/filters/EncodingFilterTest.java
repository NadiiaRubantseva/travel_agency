package ua.epam.travelagencyms.controller.filters;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EncodingFilterTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final FilterChain chain = mock(FilterChain.class);
    private final FilterConfig config = mock(FilterConfig.class);

    @Test
    void testDoFilter() throws ServletException, IOException {
        when(config.getInitParameter("encoding")).thenReturn("UTF-8");
        EncodingFilter filter = new EncodingFilter();
        filter.init(config);
        MyRequest myRequest = new MyRequest(request);
        filter.doFilter(myRequest, response, chain);
        assertEquals("UTF-8", myRequest.getCharacterEncoding());
    }
}