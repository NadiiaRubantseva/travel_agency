package ua.epam.travelagencyms.utils.query;

import java.util.HashSet;
import java.util.Set;

import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * OrderQueryBuilder. Able to build query to obtain sorted, ordered and limited list of orders
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class OrderQueryBuilder extends QueryBuilder {

    /** Contains set of allowed sort fields */
    private static final Set<String> ORDER_SORT_FIELDS_SET = new HashSet<>();

    static {
        ORDER_SORT_FIELDS_SET.add(ID);
        ORDER_SORT_FIELDS_SET.add(ORDER_STATUS_ID);
    }

    /**
     * set id as default sort field
     */
    public OrderQueryBuilder() {
        super(DATE);
    }

    /**
     * @return empty String - no need to group by in userQuery
     */
    @Override
    protected String getGroupByQuery() {
        return "";
    }

    @Override
    protected String checkSortField(String sortField) {
        if (ORDER_SORT_FIELDS_SET.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return ID;
    }
}
