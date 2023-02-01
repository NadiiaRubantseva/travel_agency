package ua.epam.travelagencyms.controller.actions;

import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

import java.util.StringJoiner;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.CONTROLLER_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * ActionUtil  class. Contains utils methods to use in actions.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class ActionUtil {

    /**
     * Checks if method is POST method
     * @param request passed by action
     * @return true if POST method
     */
    public static boolean isPostMethod(HttpServletRequest request) {
        return request.getMethod().equals("POST");
    }

    /**
     * Transfers sessions attributes to request. Delete then
     * @param request passed by action
     */
    public static void transferStringFromSessionToRequest(HttpServletRequest request, String attributeName) {
        String attributeValue = (String) request.getSession().getAttribute(attributeName);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }

    /**
     * Transfers sessions attributes to request. Delete then
     * @param request passed by action
     */
    public static void transferUserDTOFromSessionToRequest(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute(USER);
        if (user != null) {
            request.setAttribute(USER, user);
            request.getSession().removeAttribute(USER);
        }
    }

    /**
     * Transfers sessions attributes to request. Delete then
     * @param request passed by action
     */
    public static void transferTourDTOFromSessionToRequest(HttpServletRequest request) {
        TourDTO tour = (TourDTO) request.getSession().getAttribute(TOUR);
        if (tour != null) {
            request.setAttribute(TOUR, tour);
            request.getSession().removeAttribute(TOUR);
        }
    }

    /**
     * Transfers sessions attributes to request. Delete then
     * @param request passed by action
     */
    public static void transferTourImageFromSessionToRequest(HttpServletRequest request) {
        String image = (String) request.getSession().getAttribute(IMAGE);
        if (image != null) {
            request.setAttribute(IMAGE, image);
            request.getSession().removeAttribute(IMAGE);
        }
    }

    /**
     * Retrieves current path set from session
     * @param request passed by action
     * @return - current path
     */
    public static String getPath(HttpServletRequest request){
        return (String) request.getSession().getAttribute(CURRENT_PATH);
    }

    /**
     * Creates path to another Action
     * @param action - Action to be sent
     * @param parameters - required parameters
     * @return - path
     */
    public static String getActionToRedirect(String action, String... parameters) {
        String base = CONTROLLER_PAGE + "?" + ACTION + "=" + action;
        StringJoiner stringJoiner = new StringJoiner("&", "&", "");
        for (int i = 0; i < parameters.length; i+=2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }
        return base + (parameters.length > 0 ? stringJoiner : "");
    }

    /**
     * Obtain Web App domain address. Common usage - email sender
     * @param request passed by action
     * @return - Web App domain address
     */
    public static String getURL(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String requestURL = request.getRequestURL().toString();
        return requestURL.replace(servletPath, "");
    }
}
