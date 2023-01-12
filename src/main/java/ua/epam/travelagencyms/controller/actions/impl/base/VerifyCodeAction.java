package ua.epam.travelagencyms.controller.actions.impl.base;


import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.VERIFY_CODE_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class VerifyCodeAction implements Action {
    private final UserService userService;

    public VerifyCodeAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = PROFILE_PAGE;
        UserDTO user = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
        String email = user.getEmail();
        String enteredCode = request.getParameter(AUTHENTIFICATION_CODE);
        try {
            userService.verifySecurityCode(email, enteredCode);
        } catch (IncorrectCodeException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = VERIFY_EMAIL_PAGE;
        }
       request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect(VERIFY_CODE_ACTION);
    }
}