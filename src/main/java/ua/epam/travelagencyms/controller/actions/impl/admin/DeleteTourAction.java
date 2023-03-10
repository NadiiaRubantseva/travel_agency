package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.VIEW_TOURS_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_DELETE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.MESSAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.TOUR_ID;

/**
 * This is DeleteTourAction class. Accessible by admin.
 * Allows to delete tour from database. Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class DeleteTourAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(DeleteTourAction.class);
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

        // getting tour id from request
        String id = request.getParameter(TOUR_ID);

        // deleting tour from database
        tourService.delete(id);

        // setting success message
        request.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);

        // logging deleted tour
        logger.info(String.format("successfully deleted a tour with id %s", id));

        // getting redirected to all tours page
        return getActionToRedirect(VIEW_TOURS_ACTION);
    }

}