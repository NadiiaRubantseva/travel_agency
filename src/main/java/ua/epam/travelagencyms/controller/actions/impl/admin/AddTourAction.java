package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.ConvertorUtil.getTourDTOFromAddRequest;

/**
 * This is AddTourAction class. Accessible by admin. Allows to add a new tour. Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class AddTourAction implements Action {
    private final TourService tourService;

    /**
     * @param appContext contains TourService instance to use in action
     */
    public AddTourAction(AppContext appContext) {
        tourService = appContext.getTourService();
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
     * to request. Executes if only error happens.
     *
     * @param request to get error and tour attribute from session and put it in request
     * @return add tour page after failing to add a new tour
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferTourDTOFromSessionToRequest(request);
        return getPath(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to add a new tour.
     * Logs error in case if not able
     *
     * @param request to get tour fields and sets some attributes to session
     * @return ViewTourByAdmin page if successful or AddTour page if not.
     */
    private String executePost(HttpServletRequest request) {

        System.out.println("in execute post of add tour action");

        String path = VIEW_TOUR_BY_ADMIN_PAGE;

        try {
            TourDTO tour = getTourDTOFromAddRequest(request);
            request.getSession().setAttribute(TOUR, tour);
            tourService.add(tour);
            tour.setId(tourService.getByTitle(tour.getTitle()).getId());
            request.getSession().setAttribute(MESSAGE, SUCCEED_ADDED);
        } catch (Exception e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = ADD_TOUR_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect(ADD_TOUR_ACTION);
    }
}