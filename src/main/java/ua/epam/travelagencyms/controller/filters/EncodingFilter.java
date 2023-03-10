package ua.epam.travelagencyms.controller.filters;


import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * EncodingFilter  class. Sets encoding for any values from view
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class EncodingFilter extends HttpFilter {
    private String encoding;

    /**
     * Sets default encoding
     * @param config passed by application
     */
    @Override
    public void init(FilterConfig config) {
        encoding = config.getInitParameter("encoding");
    }

    /**
     * Sets default encoding for any values from user.
     * @param request passed by application
     * @param response passed by application
     * @param chain passed by application
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }
}