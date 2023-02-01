package ua.epam.travelagencyms.controller.filters.domain;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;

/**
 * Contains pages sets for anonymous user and different roles logged user. Defines if user has access to the page
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public final class DomainPagesSets {

    private DomainPagesSets() {
    }

    @Getter
    private static final Set<String> anonymousUserPages = new HashSet<>();
    @Getter
    private static final Set<String> loggedUserPages = new HashSet<>();
    @Getter
    private static final Set<String> adminPages = new HashSet<>();
    @Getter
    private static final Set<String> userPages = new HashSet<>();

    static {
        anonymousUserPages.add(CONTROLLER_PAGE);
        anonymousUserPages.add(INDEX_PAGE);
        anonymousUserPages.add(ABOUT_PAGE);
        anonymousUserPages.add(CONTACTS_PAGE);
        anonymousUserPages.add(ERROR_PAGE);
        anonymousUserPages.add(SIGN_IN_PAGE);
        anonymousUserPages.add(VERIFY_EMAIL_PAGE);
        anonymousUserPages.add(SIGN_UP_PAGE);
        anonymousUserPages.add(RESET_PASSWORD_PAGE);
        anonymousUserPages.add(BLOCKED_USER_PAGE);
    }

    static {
        loggedUserPages.addAll(anonymousUserPages);
        loggedUserPages.add(PROFILE_PAGE);
        loggedUserPages.add(EDIT_PROFILE_PAGE);
        loggedUserPages.add(CHANGE_PASSWORD_PAGE);
    }

    static {
        adminPages.addAll(loggedUserPages);
        adminPages.add(ADD_TOUR_PAGE);
        adminPages.add(EDIT_TOUR_PAGE);
        adminPages.add(VIEW_TOURS_BY_ADMIN_PAGE);
        adminPages.add(VIEW_TOUR_BY_ADMIN_PAGE);
        adminPages.add(VIEW_USERS_PAGE);
        adminPages.add(VIEW_ORDERS_BY_ADMIN_PAGE);
        adminPages.add(VIEW_ORDER_BY_ADMIN_PAGE);
        adminPages.add(VIEW_USER_BY_ADMIN_PAGE);
        adminPages.add(SEARCH_TOUR_PAGE);
        adminPages.add(SEARCH_ORDER_PAGE);
        adminPages.add(SEARCH_USER_PAGE);
    }

    static {
        userPages.addAll(loggedUserPages);
        userPages.add(VIEW_TOURS_BY_USER_PAGE);
        userPages.add(VIEW_ORDERS_BY_USER_PAGE);
        userPages.add(BOOK_TOUR_CONFIRMATION_PAGE);
    }
}