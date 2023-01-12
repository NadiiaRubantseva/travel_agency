package ua.epam.travelagencyms.utils.query;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.ASCENDING_ORDER;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.DESCENDING_ORDER;

public abstract class QueryBuilder {

    private final List<String> filters = new ArrayList<>();
    private String sortField;
    private String order = ASCENDING_ORDER;
    private int offset = 0;
    private int records = 5;

    protected QueryBuilder(String sortField) {
        this.sortField = sortField;
    }

    public QueryBuilder setUserIdFilter(long userIdFilter) {
        filters.add("user_id=" + userIdFilter);
        return this;
    }

    public QueryBuilder setRoleFilter(String roleFilter) {
        if (roleFilter != null && isPositiveInt(roleFilter)) {
            filters.add("role_id=" + roleFilter);
        }
        return this;
    }

    public QueryBuilder setTypeFilter(String typeFilter) {
        if (typeFilter != null && isPositiveInt(typeFilter)) {
            filters.add("type_id=" + typeFilter);
        }
        return this;
    }

    public QueryBuilder setStatusFilter(String typeFilter) {
        if (typeFilter != null && isPositiveInt(typeFilter)) {
            filters.add("order_status_id=" + typeFilter);
        }
        return this;
    }


    public QueryBuilder setPriceFilter(String priceMinFilter, String priceMaxFilter) {
        if (priceMinFilter != null && isPositiveInt(priceMinFilter) &&
                priceMaxFilter != null && isPositiveInt(priceMaxFilter)) {
            filters.add("price BETWEEN " + priceMinFilter + " AND " + priceMaxFilter);
        }
        return this;
    }

    public QueryBuilder setHotelFilter(String hotelFilter) {
        if (hotelFilter != null && isPositiveInt(hotelFilter)) {
            filters.add("hotel_id=" + hotelFilter);
        }
        return this;
    }

    public QueryBuilder setPersonsFilter(String personsFilter) {
        if (personsFilter != null && isPositiveInt(personsFilter)) {
            filters.add("persons=" + personsFilter);
        }
        return this;
    }

    public QueryBuilder setHotFilter(String hotFilter) {
        if (hotFilter != null && isPositiveInt(hotFilter)) {
            filters.add("hot=" + hotFilter);
        }
        return this;
    }

    public QueryBuilder setSortField(String sortField) {
        if (sortField != null) {
            this.sortField = checkSortField(sortField);
        }
        return this;
    }

    public QueryBuilder setOrder(String order) {
        if (order != null && order.equalsIgnoreCase(DESCENDING_ORDER)) {
            this.order = DESCENDING_ORDER;
        }
        return this;
    }

    public QueryBuilder setLimits(String offset, String records) {
        if (offset != null && isPositiveInt(offset)) {
            this.offset = Integer.parseInt(offset);
        }
        if (records != null && isPositiveInt(records)) {
            this.records = Integer.parseInt(records);
        }
        return this;
    }

    public String getQuery() {
        return getFilterQuery() + getGroupByQuery() + getSortQuery() + getLimitQuery();
    }

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

    protected abstract String getGroupByQuery();

    private String getSortQuery() {
        return " ORDER BY " + sortField + " " + order;
    }

    private String getLimitQuery() {
        return " LIMIT " + offset + ", " + records;
    }

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
