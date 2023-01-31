package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchUserException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.SEARCH_USER_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_USER_BY_ADMIN;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class SearchUserByEmailAction implements Action {
    private final UserService userService;

    public SearchUserByEmailAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String path = VIEW_USER_BY_ADMIN;
        try {
            request.setAttribute(USER, userService.getByEmail(request.getParameter(EMAIL)));
        } catch (NoSuchUserException | IncorrectFormatException e) {
            request.setAttribute(ERROR, e.getMessage());
            path = SEARCH_USER_PAGE;
        }
        return path;
    }
}