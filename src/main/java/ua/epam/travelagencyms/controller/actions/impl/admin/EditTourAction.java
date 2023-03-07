package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.DuplicateTitleException;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;
import ua.epam.travelagencyms.utils.ImageEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is EditTourAction class. Accessible by admin. Allows to change tour's text information.
 * Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class EditTourAction implements Action {
    private final TourService tourService;

    /**
     * @param appContext contains TourService instance to use in action
     */
    public EditTourAction(AppContext appContext) {
        tourService = appContext.getTourService();
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

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferTourDTOFromSessionToRequest(request);
        return EDIT_TOUR_PAGE;
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     *
     * @param request to get message, TourDTO or error attribute from session and put it in request
     * @return viewTourByAdmin.jsp in case of successful edit, otherwise editTour.jsp.
     */
    private String executePost(HttpServletRequest request) throws ServiceException {

        try {

            TourDTO tour = getTourDTO(request);
            request.getSession().setAttribute(TOUR, tour);
            tourService.update(tour);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
            return getActionToRedirect(SEARCH_TOUR_ACTION, TOUR_ID, request.getParameter(TOUR_ID));

        } catch (ServletException | IOException | IncorrectFormatException | DuplicateTitleException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            return getActionToRedirect(EDIT_TOUR_ACTION);
        }
    }

    private TourDTO getTourDTO(HttpServletRequest request) throws IOException, ServletException, ServiceException {
        return TourDTO.builder()
                .id(Long.parseLong(request.getParameter(TOUR_ID)))
                .title(request.getParameter(TITLE))
                .persons(Integer.parseInt(request.getParameter(PERSONS)))
                .price(Double.parseDouble(request.getParameter(PRICE)))
                .hot(request.getParameter(HOT) == null ? FALSE : TRUE)
                .type(request.getParameter(TYPE))
                .hotel(request.getParameter(HOTEL))
                .image(getImage(request))
                .description(request.getParameter(DESCRIPTION))
                .build();
    }

    private String getImage(HttpServletRequest request) throws IOException, ServletException, ServiceException {
        byte[] image = request.getPart(IMAGE).getInputStream().readAllBytes();
        String encodedImage;

        if (image.length == 0) {
            encodedImage = tourService.getImage(request.getParameter(TOUR_ID));
        } else {
            encodedImage = ImageEncoder.encode(image);
        }

        return encodedImage;
    }
}