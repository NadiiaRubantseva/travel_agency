package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchTourException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is SearchTourAction class. Accessible by admin. Allows to find a tour from database by id.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class SearchTourAction implements Action {
    private final TourService tourService;

    /**
     * @param appContext contains TourService instance to use in action
     */
    public SearchTourAction(AppContext appContext) {
        tourService = appContext.getTourService();
    }

    /**
     * Obtains required path and sets tour to request if it finds
     *
     * @param request to get tour id and put tour in request or error if it can't find tour
     * @return view tour by admin page if it finds or search tour page if not
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String id = request.getParameter(ID);
        String path = VIEW_TOUR_BY_ADMIN_PAGE;
        try {
            TourDTO tour = tourService.getById(id);
            request.setAttribute(TOUR, tour);
            request.setAttribute(IMAGE, tour.getDecodedImage());
        } catch (NoSuchTourException | IncorrectFormatException e) {
            request.setAttribute(ERROR, e.getMessage());
            path = SEARCH_TOUR_PAGE;
        }
        return path;
    }
}