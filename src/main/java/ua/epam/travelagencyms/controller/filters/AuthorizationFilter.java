package ua.epam.travelagencyms.controller.filters;

import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.controller.filters.domain.Domain.getDomain;

/**
 * AuthorizationFilter class. Controls access to pages for logged user
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class AuthorizationFilter extends HttpFilter {

    UserService userService = AppContext.getAppContext().getUserService();

    /**
     * Checks for role in session and then checks if user has access to page or action.
     * Also checks if user is blocked.
     * @param request passed by application
     * @param response passed by application
     * @param chain passed by application
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        UserDTO user = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
        String role = (String) request.getSession().getAttribute(ROLE);
        String servletPath = request.getServletPath();
        String action = request.getParameter(ACTION);
        try {
            if (isUserBlocked(user)) {
                request.getSession().removeAttribute(LOGGED_USER);
                request.getRequestDispatcher(BLOCKED_USER_PAGE).forward(request, response);
            } else if (role != null && isAccessDenied(servletPath, action, role)) {
                request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean isUserBlocked(UserDTO userDTO) throws ServiceException {
        if (userDTO != null) {
            return userService.isBlocked(userDTO.getId());
        }
        return false;
    }

    private boolean isAccessDenied(String servletPath, String action, String role) {
        return (getDomain(servletPath, action, role).checkAccess());
    }
}