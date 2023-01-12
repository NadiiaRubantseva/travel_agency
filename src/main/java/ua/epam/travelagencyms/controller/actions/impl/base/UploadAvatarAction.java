package ua.epam.travelagencyms.controller.actions.impl.base;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.InputStream;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_PROFILE_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class UploadAvatarAction implements Action {
    private final UserService userService;

    public UploadAvatarAction(AppContext appContext) {
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
        String userId = request.getParameter(ID);
        try {
            Part part = request.getPart(AVATAR);
            try (InputStream inputStream = part.getInputStream()) {
                byte[] newImage = inputStream.readAllBytes();
                userService.setAvatar(userId, newImage);
             }
        } catch (Exception e) {
            request.getSession().setAttribute(ERROR, ERROR);
        }
        request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        request.getSession().setAttribute(LOGGED_USER, userService.getById(userId));
        return getActionToRedirect(EDIT_PROFILE_ACTION);
    }
}
