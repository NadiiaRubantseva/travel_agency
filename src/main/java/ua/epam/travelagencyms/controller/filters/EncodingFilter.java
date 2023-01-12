package ua.epam.travelagencyms.controller.filters;


import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig config) {
        encoding = config.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }
}