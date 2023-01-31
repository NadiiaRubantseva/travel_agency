package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.InputStream;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is UploadTourImageAction class. Accessible by admin. Allows to change tour's image.
 * Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class UploadTourImageAction implements Action {
    private final TourService tourService;

    /**
     * @param appContext contains TourService instance to use in action
     */
    public UploadTourImageAction(AppContext appContext) {
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
     * to request
     *
     * @param request to get message, error and tour attributes from session and put it in request
     * @return view tour by admin page after trying to update tour's image
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferTourDTOFromSessionToRequest(request);
        transferTourImageFromSessionToRequest(request);
        return VIEW_TOUR_BY_ADMIN_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to upload tour image.
     *
     * @param request to get tour id and tour image and set message in case of successful uploading or error
     * @return path to redirect to executeGet method through front-controller with required parameters
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        String tourId = request.getParameter(ID);

        try {
            Part part = request.getPart(IMAGE);
            try (InputStream inputStream = part.getInputStream()) {
                byte[] newImage = inputStream.readAllBytes();
                tourService.createImage(newImage, tourId);
                TourDTO tour = tourService.getById(tourId);
                request.getSession().setAttribute(TOUR, tour);
                request.getSession().setAttribute(IMAGE, tour.getDecodedImage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        return getActionToRedirect(VIEW_TOUR_ACTION, ID, tourId);
    }
}
