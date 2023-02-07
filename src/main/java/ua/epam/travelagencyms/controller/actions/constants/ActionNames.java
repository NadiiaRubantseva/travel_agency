package ua.epam.travelagencyms.controller.actions.constants;

/**
 *
 * This is ActionNames class. It contains all action names
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 *
 */
public class ActionNames {

    /** Basic actions */
    public static final String ERROR_ACTION = "error";
    public static final String DEFAULT_ACTION = "default";
    public static final String SIGN_UP_ACTION = "sign-up";
    public static final String SIGN_IN_ACTION = "sign-in";
    public static final String SIGN_OUT_ACTION = "sign-out";
    public static final String CHANGE_PASSWORD_ACTION = "change-password";
    public static final String PASSWORD_RESET_ACTION = "password-reset";
    public static final String VERIFY_CODE_ACTION = "verify-code";
    public static final String EDIT_PROFILE_ACTION = "edit-profile";
    public static final String UPLOAD_AVATAR_ACTION = "update-avatar";

    /** Admin's actions */
    public static final String VIEW_USERS_ACTION = "view-users";
    public static final String VIEW_ORDERS_ACTION = "view-orders-by-admin";
    public static final String SEARCH_USER_BY_ID_ACTION = "search-user-by-id";
    public static final String SEARCH_USER_BY_EMAIL_ACTION = "search-user-by-email";
    public static final String UPDATE_ORDER_STATUS_ACTION = "update-order-status";
    public static final String UPDATE_ORDER_DISCOUNT_ACTION = "update-order-discount";
    public static final String SET_ROLE_ACTION = "set-role";
    public static final String SET_USER_STATUS_ACTION = "set-user-status";
    public static final String DELETE_USER_ACTION = "delete-user";
    public static final String SEARCH_TOUR_ACTION = "search-tour";
    public static final String VIEW_TOURS_ACTION = "view-tours";
    public static final String VIEW_TOUR_ACTION = "view-tour";
    public static final String ADD_TOUR_ACTION = "add-tour";
    public static final String UPLOAD_IMAGE_TOUR_ACTION = "upload-tour-image";
    public static final String EDIT_TOUR_ACTION = "edit-tour";
    public static final String DELETE_TOUR_ACTION = "delete-tour";
    public static final String USERS_PDF_ACTION = "users-pdf";
    public static final String TOURS_PDF_ACTION = "tours-pdf";
    public static final String ORDERS_PDF_ACTION = "orders-pdf";

    /** User's actions */
    public static final String BOOK_TOUR_ACTION = "book-tour";
    public static final String VIEW_ORDERS_OF_USER_ACTION = "view-orders-of-user";
    public static final String CANCEL_ORDER_ACTION = "cancel-order";
    public static final String SEARCH_ORDER_ACTION = "search-order";

}
