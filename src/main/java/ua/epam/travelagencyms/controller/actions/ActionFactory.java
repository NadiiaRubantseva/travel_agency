package ua.epam.travelagencyms.controller.actions;

import lombok.Getter;
import ua.epam.travelagencyms.controller.actions.impl.admin.*;
import ua.epam.travelagencyms.controller.actions.impl.base.*;
import ua.epam.travelagencyms.controller.actions.impl.user.BookTourAction;
import ua.epam.travelagencyms.controller.actions.impl.user.CancelOrderAction;
import ua.epam.travelagencyms.controller.actions.impl.user.ViewOrdersOfUserAction;
import ua.epam.travelagencyms.controller.context.AppContext;

import java.util.HashMap;
import java.util.Map;

import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;

/**
 * ActionFactory class. Contains all available actions and method to get any of them.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class ActionFactory {

    /** Action factory instance. Singleton */
    @Getter private static final ActionFactory ACTION_FACTORY = new ActionFactory();

    /** Map of all available actions, name as key and class instance as value */
    private static final Map<String, Action> ACTION_MAP = new HashMap<>();

    /** Application context instance. Contains all required services and utils */
    private static final AppContext APP_CONTEXT = AppContext.getAppContext();

    static {

        // ACCOUNT
        ACTION_MAP.put(DEFAULT_ACTION, new DefaultAction());
        ACTION_MAP.put(SIGN_UP_ACTION, new SignUpAction(APP_CONTEXT));
        ACTION_MAP.put(SIGN_IN_ACTION, new SignInAction(APP_CONTEXT));
        ACTION_MAP.put(SIGN_OUT_ACTION, new SignOutAction());
        ACTION_MAP.put(CHANGE_PASSWORD_ACTION, new ChangePasswordAction(APP_CONTEXT));
        ACTION_MAP.put(PASSWORD_RESET_ACTION, new ResetPasswordAction(APP_CONTEXT));
        ACTION_MAP.put(VERIFY_CODE_ACTION, new VerifyCodeAction(APP_CONTEXT));
        ACTION_MAP.put(ERROR_ACTION, new ErrorAction());
        ACTION_MAP.put(EDIT_PROFILE_ACTION, new EditProfileAction(APP_CONTEXT));

        // ADMIN
        ACTION_MAP.put(VIEW_USERS_ACTION, new ViewUsersAction(APP_CONTEXT));
        ACTION_MAP.put(SEARCH_USER_BY_ID_ACTION, new SearchUserByIdAction(APP_CONTEXT));
        ACTION_MAP.put(SEARCH_USER_BY_EMAIL_ACTION, new SearchUserByEmailAction(APP_CONTEXT));
        ACTION_MAP.put(SET_ROLE_ACTION, new SetRoleAction(APP_CONTEXT));
        ACTION_MAP.put(SET_USER_STATUS_ACTION, new SetUserStatusAction(APP_CONTEXT));
        ACTION_MAP.put(DELETE_USER_ACTION, new DeleteUserAction(APP_CONTEXT));
        ACTION_MAP.put(VIEW_TOURS_ACTION, new ViewToursAction(APP_CONTEXT));
        ACTION_MAP.put(ADD_TOUR_ACTION, new AddTourAction(APP_CONTEXT));
        ACTION_MAP.put(SEARCH_TOUR_ACTION, new SearchTourAction(APP_CONTEXT));
        ACTION_MAP.put(EDIT_TOUR_ACTION, new EditTourAction(APP_CONTEXT));
        ACTION_MAP.put(DELETE_TOUR_ACTION, new DeleteTourAction(APP_CONTEXT));
        ACTION_MAP.put(UPDATE_ORDER_STATUS_ACTION, new UpdateOrderStatusAction(APP_CONTEXT));
        ACTION_MAP.put(VIEW_ORDERS_ACTION, new ViewOrdersAction(APP_CONTEXT));
        ACTION_MAP.put(USERS_PDF_ACTION, new UsersToPdfAction(APP_CONTEXT));
        ACTION_MAP.put(TOURS_PDF_ACTION, new ToursToPdfAction(APP_CONTEXT));
        ACTION_MAP.put(ORDERS_PDF_ACTION, new OrdersToPdfAction(APP_CONTEXT));
        ACTION_MAP.put(SEARCH_LOYALTY_PROGRAM_ACTION, new SearchLoyaltyProgramAction(APP_CONTEXT));
        ACTION_MAP.put("edit-loyalty-program", new EditLoyaltyProgramAction(APP_CONTEXT));

        // USER
        ACTION_MAP.put(VIEW_ORDERS_OF_USER_ACTION, new ViewOrdersOfUserAction(APP_CONTEXT));
        ACTION_MAP.put(BOOK_TOUR_ACTION, new BookTourAction(APP_CONTEXT));
        ACTION_MAP.put(SEARCH_ORDER_ACTION, new SearchOrderAction(APP_CONTEXT));
        ACTION_MAP.put(CANCEL_ORDER_ACTION, new CancelOrderAction(APP_CONTEXT));

    }

    private ActionFactory() {}

    public static ActionFactory getActionFactory() {
        return ACTION_FACTORY;
    }

    /**
     * Obtains action by its name
     *
     * @param actionName - to search in map
     * @return required action implementation or DefaultAction if there is no such action
     */
    public Action createAction(String actionName) {
        return ACTION_MAP.getOrDefault(actionName, new DefaultAction());
    }

}
