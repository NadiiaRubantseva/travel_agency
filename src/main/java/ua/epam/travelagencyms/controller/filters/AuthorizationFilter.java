package ua.epam.travelagencyms.controller.filters;

import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.model.services.implementation.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.ERROR_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.INDEX_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class AuthorizationFilter implements Filter {

    UserService userService = AppContext.getAppContext().getUserService();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        UserDTO userDTO = (UserDTO) httpRequest.getSession().getAttribute(LOGGED_USER);
        if (userDTO != null) {
            long id = userDTO.getId();
            try {
                if (userService.isBlocked(id)) {
                    httpRequest.setAttribute("blockedUser", userService.getById(String.valueOf(id)));
                    httpRequest.getSession().removeAttribute(LOGGED_USER);

                    request.getRequestDispatcher("blocked_profile.jsp").forward(request, response);
                } else {
                    chain.doFilter(request, response);
                }
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}