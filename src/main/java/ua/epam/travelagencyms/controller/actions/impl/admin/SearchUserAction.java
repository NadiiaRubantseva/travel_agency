package ua.epam.travelagencyms.controller.actions.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.services.*;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class SearchUserAction implements Action {
    private final UserService userService;

    public SearchUserAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String path = USER_BY_EMAIL_PAGE;
        try {
            request.setAttribute(USER, userService.getByEmail(request.getParameter(EMAIL)));
        } catch (NoSuchUserException | IncorrectFormatException e) {
            request.setAttribute(ERROR, e.getMessage());
            path = SEARCH_USER_PAGE;
        }
        return path;
    }
}