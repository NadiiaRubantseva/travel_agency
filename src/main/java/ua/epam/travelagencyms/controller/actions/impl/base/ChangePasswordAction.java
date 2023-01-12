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
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.CHANGE_PASSWORD_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.CHANGE_PASSWORD_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.CONFIRM_PASSWORD;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.ERROR;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.LOGGED_USER;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.MESSAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.OLD_PASSWORD;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.PASSWORD;

public class ChangePasswordAction implements Action {
    private final UserService userService;

    public ChangePasswordAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        return CHANGE_PASSWORD_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        try {
            userServiceChangePassword(request);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        } catch (IncorrectFormatException | IncorrectPasswordException | NoSuchUserException | PasswordMatchingException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(CHANGE_PASSWORD_ACTION);
    }

    private void userServiceChangePassword(HttpServletRequest request) throws ServiceException {
        long id = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String oldPassword = request.getParameter(OLD_PASSWORD);
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        userService.changePassword(id, oldPassword, password, confirmPassword);
    }
}