package ua.epam.travelagencyms.controller.actions.impl.base;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.DuplicateEmailException;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.utils.ImageEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.EDIT_PROFILE_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_PROFILE_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.PROFILE_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

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
        return getPath(request);
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
        String path = PROFILE_PAGE;

        try {
            // getting userDTO instance from request
            UserDTO user = getUserDTO(request);

            // setting updated logged user to session
            request.getSession().setAttribute(LOGGED_USER, user);

            // updating user record in db
            userService.update(user);

            // setting success message
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);

        } catch (IncorrectFormatException | DuplicateEmailException | IOException |ServletException e) {

            // setting error message
            request.getSession().setAttribute(ERROR, e.getMessage());

            // for keep staying on the same page
            path = EDIT_PROFILE_PAGE;
        }

        // setting current page
        request.getSession().setAttribute(CURRENT_PATH, path);

        // redirecting
        return getActionToRedirect(EDIT_PROFILE_ACTION);
    }

    private UserDTO getUserDTO(HttpServletRequest request) throws IOException, ServletException, ServiceException {
        UserDTO sessionUser = (UserDTO) request.getSession().getAttribute(LOGGED_USER);

        return UserDTO.builder()
                .id(sessionUser.getId())
                .email(sessionUser.getEmail())
                .role(sessionUser.getRole())
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
                .avatar(getAvatar(request, String.valueOf(sessionUser.getId())))
                .build();

    }

    private String getAvatar(HttpServletRequest request, String id) throws IOException, ServletException, ServiceException {
        byte[] avatar = request.getPart(AVATAR).getInputStream().readAllBytes();
        String encodedImage;

        if (avatar.length == 0) {
            encodedImage = userService.getAvatar(id);
        } else {
            encodedImage = ImageEncoder.encode(avatar);
        }

        return encodedImage;
    }

}