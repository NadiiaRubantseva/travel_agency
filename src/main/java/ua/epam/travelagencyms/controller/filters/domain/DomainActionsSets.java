package ua.epam.travelagencyms.controller.filters.domain;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;


/**
 * Contains action sets for anonymous user and different roles logged user. Defines if user has access to the action
 *
 * @author  Nadiia Rubantseva
 * @version 1.0
 */
public final class DomainActionsSets {
    private DomainActionsSets() {}

    @Getter private static final Set<String> anonymousUserActions = new HashSet<>();
    private static final Set<String> loggedUserActions = new HashSet<>();
    @Getter private static final Set<String> adminActions = new HashSet<>();
    @Getter private static final Set<String> userActions = new HashSet<>();

    static {
        anonymousUserActions.add(DEFAULT_ACTION);
        anonymousUserActions.add(SIGN_IN_ACTION);
        anonymousUserActions.add(SIGN_UP_ACTION);
        anonymousUserActions.add(PASSWORD_RESET_ACTION);
        anonymousUserActions.add(ERROR_ACTION);
        anonymousUserActions.add(SIGN_OUT_ACTION);
    }

    static {
        loggedUserActions.addAll(anonymousUserActions);
        loggedUserActions.add(EDIT_PROFILE_ACTION);
        loggedUserActions.add(CHANGE_PASSWORD_ACTION);
        loggedUserActions.add(VERIFY_CODE_ACTION);
        loggedUserActions.add(UPLOAD_AVATAR_ACTION);
    }

    static {
        adminActions.addAll(loggedUserActions);
        adminActions.add(SEARCH_USER_BY_ID_ACTION);
        adminActions.add(SEARCH_USER_BY_EMAIL_ACTION);
        adminActions.add(SEARCH_TOUR_ACTION);
        adminActions.add(VIEW_TOURS_ACTION);
        adminActions.add(VIEW_TOUR_ACTION);
        adminActions.add(UPLOAD_IMAGE_TOUR_ACTION);
        adminActions.add(EDIT_TOUR_ACTION);
        adminActions.add(DELETE_TOUR_ACTION);
        adminActions.add(DELETE_USER_ACTION);
        adminActions.add(SET_ROLE_ACTION);
        adminActions.add(SET_USER_STATUS_ACTION);
        adminActions.add(VIEW_USERS_ACTION);
        adminActions.add(VIEW_ORDERS_ACTION);
        adminActions.add(UPDATE_ORDER_STATUS_ACTION);
        adminActions.add(UPDATE_ORDER_DISCOUNT_ACTION);
        adminActions.add(USERS_PDF_ACTION);
        adminActions.add(TOURS_PDF_ACTION);
    }

    static {
        loggedUserActions.addAll(anonymousUserActions);
        userActions.add(BOOK_TOUR_ACTION);
        userActions.add(VIEW_ORDERS_OF_USER_ACTION);
        userActions.add(CANCEL_ORDER_ACTION);
        userActions.add(SEARCH_ORDER_ACTION);
    }
}