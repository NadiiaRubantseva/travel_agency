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

import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.EDIT;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;


public class SearchLoyaltyProgramAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SearchLoyaltyProgramAction.class);
    private final LoyaltyProgramService loyaltyProgramService;

    /**
     * @param appContext contains LoyaltyProgramService instance to use in action
     */
    public SearchLoyaltyProgramAction(AppContext appContext) {
        loyaltyProgramService = appContext.getLoyaltyProgramService();
    }

    /**
     * Search loyalty program from database.
     * Logs error in case if not able
     *
     * @param request to get necessary attributes
     * @return path to redirect through front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        // transferring success message from session to request if available
        transferStringFromSessionToRequest(request, MESSAGE);

        // view loyalty program page
        String path = VIEW_LOYALTY_PROGRAM_BY_ADMIN_PAGE;

        // check if view for edit is needed
        String purpose = request.getParameter(PURPOSE);

        if (purpose != null && purpose.equals(EDIT)) {
            path = EDIT_LOYALTY_PROGRAM_PAGE; }

        try {
            LoyaltyProgramDTO loyaltyProgramDTO = loyaltyProgramService.get();
            request.setAttribute(LOYALTY_PROGRAM, loyaltyProgramDTO);
            logger.info("Successfully retrieved loyalty program from database -> " + loyaltyProgramDTO);

        } catch (ServiceException e) {
            request.setAttribute(ERROR, e.getMessage());
            path = INDEX_PAGE;
            logger.info("Failed to retrieve loyalty program record from database -> " + e.getMessage());
        }

        return path;
    }
}