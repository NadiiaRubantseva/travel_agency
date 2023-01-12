package ua.epam.travelagencyms.controller.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.epam.travelagencyms.controller.actions.constants.Parameters.LOCALE;

public class LocaleFilter implements Filter {
    private static final String REFERER = "referer";
    private String defaultLocale;

    @Override
    public void init(FilterConfig config) {
        defaultLocale = config.getInitParameter("defaultLocale");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String locale = httpRequest.getParameter(LOCALE);
        if (isNotBlank(locale)) {
            httpRequest.getSession().setAttribute(LOCALE, locale);
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getHeader(REFERER));
        } else {
            String sessionLocale = (String) httpRequest.getSession().getAttribute(LOCALE);
            if (isBlank(sessionLocale)) {
                httpRequest.getSession().setAttribute(LOCALE, defaultLocale);
            }
            chain.doFilter(request, response);
        }
    }

    private boolean isBlank(String locale) {
        return locale == null || locale.isEmpty();
    }

    private boolean isNotBlank(String locale) {
        return !isBlank(locale);
    }
}