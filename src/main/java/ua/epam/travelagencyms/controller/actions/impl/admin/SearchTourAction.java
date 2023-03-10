package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchTourException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.*;
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
     * @param appContext contains TourService and UserService instances to use in action
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
        // transfer attributes from session to request if any
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);


        // getting tour id from request
        String tourId = request.getParameter(TOUR_ID);

        // checking if tour needed for edit purpose
        String purpose = request.getParameter(PURPOSE);

        // getting user from request
        UserDTO user = (UserDTO) request.getSession().getAttribute(LOGGED_USER);

        try {
            // retrieving tour record by id
            TourDTO tour = tourService.getById(tourId);

            // setting tour information to request
            request.setAttribute(TOUR, tour);
            request.setAttribute(TOTAL, tour.getPrice());

            // returning edit page if purpose of search is edit tour
            if (purpose != null && purpose.equals(EDIT)) {
                return EDIT_TOUR_PAGE;
            }

            // returning view tour page by admin otherwise
            if (user != null && (user.getRole().equals(ADMIN) || user.getRole().equals(MANAGER))) {
                return VIEW_TOUR_BY_ADMIN_PAGE;
            }

            // calculating user discount and setting to request as an attribute,
            // updating total attribute, returning view tour page
            if (user != null) {
                int discount = user.getDiscount();
                request.setAttribute(DISCOUNT, discount);
                request.setAttribute(TOTAL, calculateTotalPrice(tour.getPrice(), discount));
                return VIEW_TOUR_PAGE;
            }

        } catch (NoSuchTourException | IncorrectFormatException e) {
            // setting error message
            request.setAttribute(ERROR, e.getMessage());

            // returning search tour if role is not user
            if (user != null && (user.getRole().equals(ADMIN) || user.getRole().equals(MANAGER))) {
                return SEARCH_TOUR_PAGE;
            } else {
                return VIEW_TOURS_PAGE;
            }
        }
        return VIEW_TOURS_PAGE;
    }

    /**
     * Calculating final tour price based on tour price and personal discount
     *
     * @param price tour price
     * @param discount user personal discount
     * @return final tour price that was calculated based on tour price and user discount
     */
    private double calculateTotalPrice(double price, int discount) {
        if (discount == 0) {
            return price;
        }
        double discountValue = (price * discount) / 100;
        return price - discountValue;
    }
}