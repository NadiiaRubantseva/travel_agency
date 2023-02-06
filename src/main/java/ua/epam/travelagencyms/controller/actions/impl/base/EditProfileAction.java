package ua.epam.travelagencyms.controller.actions.impl.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.services.*;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.isPostMethod;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferUserDTOFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.EDIT_PROFILE_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_PROFILE_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.SURNAME;

/**
 * This is EditProfileAction class. Accessible by any logged user. Allows to change user's email, name and surname.
 * Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class EditProfileAction implements Action {
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public EditProfileAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Checks method and calls required implementation
     *
     * @param request to get method, session and set all required attributes
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     *
     * @param request to get message, UserDTO or error attribute from session and put it in request
     * @return edit profile page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserDTOFromSessionToRequest(request);
        return EDIT_PROFILE_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to change users email, name, password via service.
     * Sets UserDTO to session so user will not need to enter fields again. Update logged user in session if
     * editing was successful
     *
     * @param request to get users id and all required fields. Also, to set message in case of successful deleting and
     *                error in another case.
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest request) throws ServiceException {

        UserDTO sessionUser = (UserDTO) request.getSession().getAttribute(LOGGED_USER);

        UserDTO user = UserDTO.builder()
                .id(sessionUser.getId())
                .email(sessionUser.getEmail())
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
                .build();

        try {
            userService.update(user);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
            sessionUser.setName(user.getName());
            sessionUser.setSurname(user.getSurname());

        } catch (IncorrectFormatException | DuplicateEmailException e) {
            request.getSession().setAttribute(USER, user);
            request.getSession().setAttribute(ERROR, e.getMessage());
        }

        return getActionToRedirect(EDIT_PROFILE_ACTION);
    }
}