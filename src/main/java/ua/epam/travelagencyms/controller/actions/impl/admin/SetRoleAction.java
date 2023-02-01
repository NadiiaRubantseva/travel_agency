package ua.epam.travelagencyms.controller.actions.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.services.*;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SEARCH_USER_BY_ID_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is SetRoleAction class. Accessible by admin. Allows to set users role. Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class SetRoleAction implements Action {
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public SetRoleAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Obtains required path and sets users role
     *
     * @param request to get user id and new role and put user in request
     * @return path to redirect to executeGet method in SearchUserAction through front-controller with required
     * parameters to find user.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String id = request.getParameter(ID);
        int roleId = Role.valueOf(request.getParameter(ROLE)).getValue();
        userService.setRole(id, roleId);
        request.setAttribute(USER, userService.getById(id));
        return getActionToRedirect(SEARCH_USER_BY_ID_ACTION, ID, id);
    }
}
