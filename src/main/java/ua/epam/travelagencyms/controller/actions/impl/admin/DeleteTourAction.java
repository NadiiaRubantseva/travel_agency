package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_DELETE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is DeleteTourAction class. Accessible by admin. Allows to delete tour from database. Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class DeleteTourAction implements Action {
    private final TourService tourService;

    /**
     * @param appContext contains TourService instance to use in action
     */
    public DeleteTourAction(AppContext appContext) {
        tourService = appContext.getTourService();
    }

    /**
     * Tries to delete tour from database.
     * Logs error in case if not able
     *
     * @param request to get tour's id and set message in case of successful deleting
     * @return path to redirect through front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        tourService.delete(request.getParameter(TOUR_ID));
        request.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);
        return getActionToRedirect(VIEW_TOURS_ACTION);
    }

}