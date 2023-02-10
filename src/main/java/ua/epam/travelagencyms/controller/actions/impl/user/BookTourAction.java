package ua.epam.travelagencyms.controller.actions.impl.user;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_BOOK;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is BookTourAction class. Accessible by user. Allows to book tour.
 * Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class BookTourAction implements Action {
    private final OrderService orderService;

    /**
     * @param appContext contains OrderService and UserService instances to use in action
     */
    public BookTourAction(AppContext appContext) {
        orderService = appContext.getOrderService();
    }

    /**
     * Tries to book a tour from database.
     * Logs error in case if not able
     *
     * @param request to get tour's id, user's id, tour's price and set error message in case of unsuccessful ordering
     * @return path to redirect through front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String userId = request.getParameter(USER_ID);
        String tourId = request.getParameter(TOUR_ID);
        String tourPrice = request.getParameter(PRICE);
        String discount = request.getParameter(DISCOUNT);
        String total = request.getParameter(TOTAL);

        OrderDTO order = OrderDTO.builder()
                .userId(Long.parseLong(userId))
                .tourId(Long.parseLong(tourId))
                .tourPrice(Double.parseDouble(tourPrice))
                .discount(Integer.parseInt(discount))
                .totalCost(Double.parseDouble(total))
                .date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .build();

        System.out.println(order);

        try {
            orderService.addOrder(order);
            System.out.println("successfully added an order");
            request.getSession().setAttribute(MESSAGE, SUCCEED_BOOK);
        } catch (Exception e) {
            request.setAttribute(ERROR, e.getMessage());
            System.out.println("unsuccessfully added an order");
            return VIEW_TOUR_PAGE;
        }
        return getActionToRedirect(VIEW_ORDERS_OF_USER_ACTION);
    }

}