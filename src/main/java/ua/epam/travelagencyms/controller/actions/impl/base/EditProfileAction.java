package ua.epam.travelagencyms.controller.actions.impl.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.services.*;
import ua.epam.travelagencyms.utils.ConvertorUtil;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.isPostMethod;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferUserDTOFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.EDIT_PROFILE_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_PROFILE_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.EMAIL;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.ERROR;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.LOGGED_USER;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.MESSAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.NAME;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.SURNAME;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.USER;

public class EditProfileAction implements Action {
    private final UserService userService;

    public EditProfileAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserDTOFromSessionToRequest(request);
        return EDIT_PROFILE_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        UserDTO sessionUser = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
        UserDTO user = ConvertorUtil.getUserDTOFromEditUserAction(request, sessionUser);
        try {
            userService.update(user);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
            updateSessionUser(sessionUser, user);
        } catch (IncorrectFormatException | DuplicateEmailException e) {
            request.getSession().setAttribute(USER, user);
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(EDIT_PROFILE_ACTION);
    }

    private void updateSessionUser(UserDTO currentUser, UserDTO user) {
        currentUser.setEmail(user.getEmail());
        currentUser.setName(user.getName());
        currentUser.setSurname(user.getSurname());
    }
}