package ua.epam.travelagencyms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.actions.ActionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.ERROR_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.ACTION;

/**
 * Controller  class. Implements Front-controller pattern. Chooses action to execute and redirect or forward result.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
@MultipartConfig
public class Controller extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    /**
     * Calls and executes action and then forwards requestDispatcher
     * @param request comes from user
     * @param response comes from user
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(process(request, response)).forward(request, response);
    }

    /**
     * Calls and executes action and then sendRedirect for PRG pattern implementation
     * @param request comes from user
     * @param response comes from user
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(process(request, response));
    }

    /**
     * Obtains path to use in doPost/doGet methods. In case of error will return error page
     * @return path
     */
    private String process(HttpServletRequest request, HttpServletResponse response) {
        Action action = ACTION_FACTORY.createAction(request.getParameter(ACTION));
        String path = ERROR_PAGE;
        try {
            path = action.execute(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return path;
    }
}
