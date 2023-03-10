package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Objects;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.EDIT_TOUR_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_TOUR_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_TOUR_BY_ADMIN_PAGE;
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
    private static final Logger logger = LoggerFactory.getLogger(EditTourAction.class);
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

    /**
     * Called from doGet method in front-controller.
     * Obtains required path and transfer attributes from session to request.
     *
     * @param request to get error and tour attributes from session and put it in request
     * @return a String representing the path for the next view.
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferTourDTOFromSessionToRequest(request);
        return getPath(request);
    }

    /**
     * Called from doGet method in front-controller.
     * Obtains required path and transfer attributes from session to request
     *
     * @param request to get message, TourDTO or error attribute from session and put it in request
     * @return viewTourByAdmin.jsp in case of successful edit, otherwise editTour.jsp.
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = VIEW_TOUR_BY_ADMIN_PAGE;

        try {
            // mapping TourDTO instance from request
            TourDTO tour = getTourDTO(request);

            // setting TourDTO attribute
            request.getSession().setAttribute(TOUR, tour);

            // updating tour record accordingly
            tourService.update(tour);

            // setting success message
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);

            // logging successfully updated tour
            logger.info(String.format("Successfully updated tour -> %s", tour));

        } catch (ServletException | IOException | IncorrectFormatException | DuplicateTitleException e) {
            // setting error page
            request.getSession().setAttribute(ERROR, e.getMessage());

            // setting page to keep staying on the same one.
            path = EDIT_TOUR_PAGE;

            // logging failed attempt to update tour
            logger.info(String.format("Failed to update tour -> %s", e.getMessage()));
        }

        // setting current path attribute
        request.getSession().setAttribute(CURRENT_PATH, path);

        // redirecting accordingly
        return getActionToRedirect(EDIT_TOUR_ACTION);

    }

    /**
     * Maps new TourDTO instance from request.
     *
     * @param request the HTTP request to process the tour fields and set attributes to session.
     * @return mapped TourDTO instance from HttpServletRequest.
     */
    private TourDTO getTourDTO(HttpServletRequest request) throws IOException, ServletException, ServiceException {
        return TourDTO.builder()
                .id(Long.parseLong(request.getParameter(TOUR_ID)))
                .title(request.getParameter(TITLE))
                .persons(Integer.parseInt(request.getParameter(PERSONS)))
                .price(Double.parseDouble(request.getParameter(PRICE)))
                .hot(Objects.isNull(request.getParameter(HOT)) ? FALSE : TRUE)
                .type(request.getParameter(TYPE))
                .hotel(request.getParameter(HOTEL))
                .image(getImage(request))
                .description(request.getParameter(DESCRIPTION))
                .build();
    }

    /**
     * Gets encoded tour image from request.
     *
     * @param request the HTTP request to process the tour image.
     * @return encoded tour image.
     */
    private String getImage(HttpServletRequest request) throws IOException, ServletException, ServiceException {
        // getting image from request
        byte[] image = request.getPart(IMAGE).getInputStream().readAllBytes();

        String encodedImage;

        // if image is empty, then getting the old one from db
        if (image.length == 0) {
            encodedImage = tourService.getImage(request.getParameter(TOUR_ID));
        // otherwise encoding image accordingly
        } else {
            encodedImage = ImageEncoder.encode(image);
        }

        // returning encoded image
        return encodedImage;
    }
}