package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.LoyaltyProgramDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.implementation.LoyaltyProgramService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.EDIT_LOYALTY_PROGRAM_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_LOYALTY_PROGRAM_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_LOYALTY_PROGRAM_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.UNSUCCESSFUL_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;


public class EditLoyaltyProgramAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(EditLoyaltyProgramAction.class);

    private final LoyaltyProgramService loyaltyProgramService;

    /**
     * @param appContext contains LoyaltyProgramService instance to use in action
     */
    public EditLoyaltyProgramAction(AppContext appContext) {
        loyaltyProgramService = appContext.getLoyaltyProgramService();
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
     * Called from doGet method in front-controller.
     * Obtains required path and transfer attributes from session to request.
     *
     * @param request to get error and loyalty program attributes from session and put it in request
     * @return a String representing the path for the next view.
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        return getPath(request);
    }

    /**
     * Called from doPost method in front-controller.
     * Executes HTTP POST request for updating a loyalty program record and returns the redirect action path.
     * Logs error in case if not able
     *
     * @param request the HttpServletRequest object containing the request parameters and attributes.
     * @return a String representing the action to redirect the user after the update operation.
     */
    private String executePost(HttpServletRequest request) {

        String path = VIEW_LOYALTY_PROGRAM_BY_ADMIN_PAGE;

        // mapping LoyaltyProgramDTO instance from request
        LoyaltyProgramDTO loyaltyProgram = getLoyaltyProgramDTO(request);

        try {
            // updating loyalty program record in db
            loyaltyProgramService.update(loyaltyProgram);

            // setting success message
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);

            // logging update
            logger.info("successfully updated loyalty program record in db -> " + loyaltyProgram);

        } catch (ServiceException e) {
            // setting error message
            request.getSession().setAttribute(ERROR, UNSUCCESSFUL_UPDATE);

            // setting page to keep staying on the same one.
            path = EDIT_LOYALTY_PROGRAM_PAGE;

            // logging unsuccessful update attempt
            logger.info("failed to update loyalty program record in db -> " + loyaltyProgram);
        }

        // setting current path attribute
        request.getSession().setAttribute(CURRENT_PATH, path);

        // redirecting accordingly
        return getActionToRedirect(EDIT_LOYALTY_PROGRAM_ACTION);
    }

    /**
     * Maps new LoyaltyProgramDTO instance from request.
     *
     * @param request the HTTP request to process the loyalty program fields and set attributes to session.
     * @return mapped LoyaltyProgramDTO instance from HttpServletRequest.
     */
    private LoyaltyProgramDTO getLoyaltyProgramDTO(HttpServletRequest request) {
        return LoyaltyProgramDTO.builder()
                .step(Integer.parseInt(request.getParameter(STEP)))
                .discount(Integer.parseInt(request.getParameter(DISCOUNT)))
                .maxDiscount(Integer.parseInt(request.getParameter(MAX_DISCOUNT)))
                .build();
    }
}