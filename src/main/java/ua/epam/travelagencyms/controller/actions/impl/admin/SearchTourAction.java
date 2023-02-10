package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchTourException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.ADMIN;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.EDIT;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is SearchTourAction class. Accessible by admin. Allows to find a tour from database by id.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class SearchTourAction implements Action {
    private final TourService tourService;
    private final UserService userService;

    /**
     * @param appContext contains TourService and UserService instances to use in action
     */
    public SearchTourAction(AppContext appContext) {
        tourService = appContext.getTourService();
        userService = appContext.getUserService();
    }

    /**
     * Obtains required path and sets tour to request if it finds
     *
     * @param request to get tour id and put tour in request or error if it can't find tour
     * @return view tour by admin page if it finds or search tour page if not
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String path = VIEW_TOUR_PAGE;
        String id = request.getParameter(ID);
        String purpose = request.getParameter(PURPOSE);
        UserDTO user = (UserDTO) request.getSession().getAttribute(LOGGED_USER);

        try {
            TourDTO tour = tourService.getById(id);
            request.setAttribute(TOUR, tour);

            if(user != null) {
                int discount = userService.getDiscount(user.getId());
                request.setAttribute(DISCOUNT, discount);
                request.setAttribute(TOTAL, calculateTotalPrice(tour.getPrice(), discount));
            }

        } catch (NoSuchTourException | IncorrectFormatException e) {
            request.setAttribute(ERROR, e.getMessage());
            if (user != null && user.getRole().equals(ADMIN)) {
                path = SEARCH_TOUR_PAGE;
            }
        }

        if (user != null && user.getRole().equals(ADMIN)) {
            path = VIEW_TOUR_BY_ADMIN_PAGE;
        }

        if (purpose != null && purpose.equals(EDIT)) {
            path = EDIT_TOUR_PAGE;
        }

        return path;
    }

    private double calculateTotalPrice(double price, int discount) {
        if (discount == 0) {
            return price;
        }
        double discountValue = (price * discount) / 100;
        return price - discountValue;
    }
}