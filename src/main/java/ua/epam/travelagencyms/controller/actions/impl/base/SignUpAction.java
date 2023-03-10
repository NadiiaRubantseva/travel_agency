package ua.epam.travelagencyms.controller.actions.impl.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.DuplicateEmailException;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.PasswordMatchingException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SIGN_UP_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SIGN_UP_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_REGISTER;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is SignUpAction class. Accessible by any user. Allows to create account. Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class SignUpAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SignUpAction.class);
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public SignUpAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Checks method and calls required implementation
     *
     * @param request  to get method, session and set all required attributes
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
     * @param request to get message or error attribute from session and put it in request.
     * @return either sign-in page if everything is fine or sign-up if not
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserDTOFromSessionToRequest(request);
        return getPath(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to register user. Sets different path to session depends on
     * success or not. Checks captcha. Sends email if registration was successful
     *
     * @param request to get users fields from parameters
     * @return path to redirect to executeGet method
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = SIGN_IN_PAGE;

        // mapping user dto from request
        UserDTO user = getUserDTO(request);
        try {

            // add new user in db
            userService.add(user, request.getParameter(PASSWORD), request.getParameter(CONFIRM_PASSWORD));

            // setting success message
            request.getSession().setAttribute(MESSAGE, SUCCEED_REGISTER);

            // logging new user
            logger.atLevel(Level.INFO).log(String.format("New user registered - %s", user.getEmail()));

        } catch (IncorrectFormatException | PasswordMatchingException | DuplicateEmailException e) {
            // setting user dto to session
            request.getSession().setAttribute(USER, user);

            // setting error message
            request.getSession().setAttribute(ERROR, e.getMessage());

            // setting page, so user keep staying on the same page
            path = SIGN_UP_PAGE;
        }

        // setting current path
        request.getSession().setAttribute(CURRENT_PATH, path);

        // redirecting
        return getActionToRedirect(SIGN_UP_ACTION);
    }

    public static UserDTO getUserDTO(HttpServletRequest request) {
        return UserDTO.builder()
                .email(request.getParameter(EMAIL))
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
                .build();
    }
}