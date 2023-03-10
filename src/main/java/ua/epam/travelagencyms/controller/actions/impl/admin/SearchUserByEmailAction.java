package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchUserException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SEARCH_USER_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_USER_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is SearchUserByEmailAction class. Accessible by admin. Allows to find user from database by email.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class SearchUserByEmailAction implements Action {
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public SearchUserByEmailAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Obtains required path and sets user to request if it finds
     *
     * @param request to get user email and put user in request or error if it can't find user
     * @return view user by admin page if it finds or search user page if not
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        // transfer attributes from session to request if any
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);

        // return page if user was found
        String path = VIEW_USER_BY_ADMIN_PAGE;

        try {

            // retrieving user record from db
            request.setAttribute(USER, userService.getByEmail(request.getParameter(EMAIL)));
        } catch (NoSuchUserException | IncorrectFormatException e) {

            // setting error message
            request.setAttribute(ERROR, e.getMessage());

            // setting search tour page so user will be staying on the same one
            path = SEARCH_USER_PAGE;
        }

        // return path depending on whether user was found or not
        return path;
    }
}