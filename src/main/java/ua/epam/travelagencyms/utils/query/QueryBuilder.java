package ua.epam.travelagencyms.utils.query;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.ASCENDING_ORDER;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.DESCENDING_ORDER;

/**
 * Abstract queryBuilder. Defines all methods to build query to obtain sorted, ordered and limited list of entities
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public abstract class QueryBuilder {
    protected final List<String> filters = new ArrayList<>();
    protected String sortField;
    protected String order = ASCENDING_ORDER;
    protected int offset = 0;
    protected int records = 8;

    /**
     * @param sortField by default.
     */
    protected QueryBuilder(String sortField) {
        this.sortField = sortField;
    }

    /**
     * Creates concrete filter for query
     * @param userIdFilter user id for query
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setUserIdFilter(long userIdFilter) {
        filters.add("user_id=" + userIdFilter);
        return this;
    }

    /**
     * Creates role filter for users query
     * @param roleFilter can be any role value (1-4)
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setRoleFilter(String roleFilter) {
        if (roleFilter != null && isPositiveInt(roleFilter)) {
            filters.add("role_id=" + roleFilter);
        }
        return this;
    }

    /**
     * Creates type filter for tours query
     * @param typeFilter can be any type value (1-3)
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setTypeFilter(String typeFilter) {
        if (typeFilter != null && isPositiveInt(typeFilter)) {
            filters.add("type_id=" + typeFilter);
        }
        return this;
    }

    /**
     * Creates status filter for orders query
     * @param statusFilter can be any status value (1-3)
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setStatusFilter(String statusFilter) {
        if (statusFilter != null && isPositiveInt(statusFilter)) {
            filters.add("order_status_id=" + statusFilter);
        }
        return this;
    }

    /**
     * Creates date filter for orders query
     * @param startDate - startDate.
     * @param endDate - endDate.
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setDateFilter(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            return this;
        }

        if (!startDate.isEmpty() && endDate.equals("")) {
            filters.add("date >= '" + startDate + "'");
            return this;
        }

        if (startDate.equals("") && !endDate.isEmpty()) {
            filters.add("date <= '" + endDate + "'");
            return this;
        }

        if (!startDate.equals("") && !endDate.equals("")) {
            filters.add("date BETWEEN '" + startDate + "' AND '" + endDate + "'");
            return this;
        }
        return this;
    }

    /**
     * Creates price filter for tours query
     * @param priceMinFilter - min price. can be any not negative number.
     * @param priceMaxFilter - max price. can be any not negative number.
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setPriceFilter(String priceMinFilter, String priceMaxFilter) {
        if (priceMinFilter == null || priceMaxFilter == null) {
            return this;
        }

        if (priceMinFilter.equals("") && !priceMaxFilter.isEmpty()) {
            priceMinFilter = "1";
        } else if (!priceMinFilter.isEmpty() && priceMaxFilter.equals("")) {
            filters.add("price > " + priceMinFilter);
            return this;
        }

        if (isPositiveInt(priceMinFilter) && isPositiveInt(priceMaxFilter)) {
            filters.add("price BETWEEN " + priceMinFilter + " AND " + priceMaxFilter);
        }

        return this;
    }

    /**
     * Creates hotel filter for tours query
     * @param hotelFilter can be any type value (1-3)
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setHotelFilter(String hotelFilter) {
        if (hotelFilter != null && isPositiveInt(hotelFilter)) {
            filters.add("hotel_id=" + hotelFilter);
        }
        return this;
    }

    /**
     * Creates persons filter for tours query
     * @param personsFilter can be any not negative integer value
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setPersonsFilter(String personsFilter) {
        if (personsFilter != null && isPositiveInt(personsFilter)) {
            filters.add("persons=" + personsFilter);
        }
        return this;
    }

    /**
     * Sets sort field, but will check if it
     * @param sortField will be checked in subclasses to avoid injections
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setSortField(String sortField) {
        if (sortField != null) {
            this.sortField = checkSortField(sortField);
        }
        return this;
    }

    /**
     * Sets sorting order
     * @param order - sorting order (ASC by default)
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setOrder(String order) {
        if (order != null && order.equalsIgnoreCase(DESCENDING_ORDER)) {
            this.order = DESCENDING_ORDER;
        }
        return this;
    }

    /**
     * Sets limits for pagination
     * @param offset - record to start with. Checks if valid, set by default if not
     * @param records - number of records per page. Checks if valid, set by default if not
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setLimits(String offset, String records) {
        if (offset != null && isPositiveInt(offset)) {
            this.offset = Integer.parseInt(offset);
        }
        if (records != null && isPositiveInt(records)) {
            this.records = Integer.parseInt(records);
        }
        return this;
    }

    /**
     * @return complete query to use in DAO to obtain list of Entities
     */
    public String getQuery() {
        return getFilterQuery() + getGroupByQuery() + getSortQuery() + getLimitQuery();
    }

    /**
     * @return filter query to use in DAO to obtain number of records
     */
    public String getRecordQuery() {
        return getFilterQuery();
    }

    private String getFilterQuery() {
        if (filters.isEmpty()) {
            return "";
        }
        StringJoiner stringJoiner = new StringJoiner(" AND ", " WHERE ", " ");
        filters.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    /**
     * Should be implemented in subclasses
     * @return group by some field or empty
     */
    protected abstract String getGroupByQuery();

    protected String getSortQuery() {
        return " ORDER BY " + sortField + " " + order;
    }

    private String getLimitQuery() {
        return " LIMIT " + offset + ", " + records;
    }

    /**
     * Should be implemented in subclasses
     * @return sort field if it's suitable or default
     */
    protected abstract String checkSortField(String sortField);

    private boolean isPositiveInt(String intString) {
        try {
            int i = Integer.parseInt(intString);
            if (i < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
