package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;
import ua.epam.travelagencyms.utils.ImageEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.ADD_TOUR_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.ADD_TOUR_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_TOUR_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is AddTourAction class. Accessible by admin.
 * Allows to add a new tour. Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class AddTourAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(AddTourAction.class);
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
     * Called from doPost method in front-controller.
     * Executes HTTP POST request and adds a new tour to the database.
     * Logs error in case if not able
     *
     * @param request the HTTP request to process the tour fields and set attributes to session.
     * @return ViewTourByAdmin page if successful or AddTour page if not.
     */
    private String executePost(HttpServletRequest request) {

        // page that admin will see if tour add action is successful
        String path = VIEW_TOUR_BY_ADMIN_PAGE;

        try {

            // getting tour information from request
            TourDTO tour = getTourDTOFromAddRequest(request);

            // setting tour attribute to request
            request.getSession().setAttribute(TOUR, tour);

            // adding new tour to database
            tourService.add(tour);

            // setting success message
            request.getSession().setAttribute(MESSAGE, SUCCEED_ADDED);

            // logging new tour added
            logger.info("Successfully added a new tour with title: " + tour.getTitle());

        } catch (ServiceException | ServletException | IOException e) {
            // setting fail message
            request.getSession().setAttribute(ERROR, e.getMessage());

            // setting addTour.jsp page for keep staying on the same page.
            path = ADD_TOUR_PAGE;

            logger.info(String.format("Unsuccessful attempt to add a new tour -> %s", e.getMessage()));

        }

        // setting current path attribute
        request.getSession().setAttribute(CURRENT_PATH, path);

        // redirecting according to set path attribute
        return getActionToRedirect(ADD_TOUR_ACTION);
    }

    /**
     * Maps new TourDTO instance from request.
     *
     * @param request the HTTP request to process the tour fields and set attributes to session.
     * @return mapped TourDTO instance from HttpServletRequest.
     */
    private TourDTO getTourDTOFromAddRequest(HttpServletRequest request) throws ServletException, IOException {
        return TourDTO.builder()
                .title(request.getParameter(TITLE))
                .persons(Integer.parseInt(request.getParameter(PERSONS)))
                .price(Double.parseDouble(request.getParameter(PRICE)))
                .hot(Objects.isNull(request.getParameter(HOT)) ? FALSE : TRUE)
                .type(request.getParameter(TYPE))
                .hotel(request.getParameter(HOTEL))
                .image(ImageEncoder.encode(request.getPart(IMAGE).getInputStream().readAllBytes()))
                .description(request.getParameter(DESCRIPTION))
                .build();
    }
}