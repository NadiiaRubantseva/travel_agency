package ua.epam.travelagencyms.controller.filters.domain;

import ua.epam.travelagencyms.model.entities.user.Role;

import java.util.Set;

/**
 * Checks if user can access page or use action
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class Domain {
    /**
     * contains page name
     */
    private final String servletPath;

    /**
     * contains action name
     */
    private final String action;

    /**
     * contains user allowed pages
     */
    private Set<String> domainPages;

    /**
     * contains user allowed actions
     */
    private Set<String> domainActions;

    private Domain(String servletPath, String action) {
        this.servletPath = servletPath;
        this.action = action;
        setDomains();
    }

    private Domain(String servletPath, String action, String role) {
        this.servletPath = servletPath;
        this.action = action;
        setDomains(role);
    }

    /**
     * Obtains Domain for anonymous user
     *
     * @param servletPath - contains page to access
     * @param action      - contains action to call
     * @return Domain
     */
    public static Domain getDomain(String servletPath, String action) {
        return new Domain(servletPath, action);
    }

    /**
     * Obtains Domain for logged user
     *
     * @param servletPath - contains page to access
     * @param action      - contains action to call
     * @param role        - user's role
     * @return Domain
     */
    public static Domain getDomain(String servletPath, String action, String role) {
        return new Domain(servletPath, action, role);
    }

    private void setDomains() {
        domainPages = DomainPagesSets.getAnonymousUserPages();
        domainActions = DomainActionsSets.getAnonymousUserActions();
    }

    private void setDomains(String role) {
        Role roleValue = Role.valueOf(role);
        switch (roleValue) {
            case ADMIN:
                domainPages = DomainPagesSets.getAdminPages();
                domainActions = DomainActionsSets.getAdminActions();
                break;
            case USER:
                domainPages = DomainPagesSets.getUserPages();
                domainActions = DomainActionsSets.getUserActions();
                break;
            default:
               domainPages = DomainPagesSets.getAnonymousUserPages();
               domainActions = DomainActionsSets.getAnonymousUserActions();
        }
    }

    /**
     * Checks if user allowed to access page or call action
     *
     * @return false if not allowed
     */
    public boolean checkAccess() {
        return !checkPages() || !checkActions();
    }

    private boolean checkPages() {
        if (servletPath != null) {
            System.out.println(servletPath + " <- servlet path");
            if (servletPath.startsWith("/img/")) return true;
            return domainPages.contains(servletPath.substring(1));
        }
        return true;
    }

    private boolean checkActions() {
        if (action != null) {
            return domainActions.contains(action);
        }
        return true;
    }
}
