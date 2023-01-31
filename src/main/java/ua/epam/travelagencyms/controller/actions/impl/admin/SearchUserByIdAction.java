package ua.epam.travelagencyms.controller.actions.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.services.*;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is SearchUserByIdAction class. Accessible by admin. Allows to find user from database by id.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class SearchUserByIdAction implements Action {
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public SearchUserByIdAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Obtains required path and sets user to request if it finds
     *
     * @param request to get user id and put user in request or error if it can't find user
     * @return view user by admin page if it finds or search user page if not
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String path = VIEW_USER_BY_ADMIN;
        try {
            request.setAttribute(USER, userService.getById(request.getParameter(ID)));
        } catch (NoSuchUserException | IncorrectFormatException e) {
            request.setAttribute(ERROR, e.getMessage());
            path = SEARCH_USER_PAGE;
        }
        return path;
    }
}