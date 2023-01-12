package ua.epam.travelagencyms.utils.query;

import java.util.HashSet;
import java.util.Set;

import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class OrderQueryBuilder extends QueryBuilder {

    private static final Set<String> ORDER_SORT_FIELDS_SET = new HashSet<>();

    static {
        ORDER_SORT_FIELDS_SET.add(ID);
        ORDER_SORT_FIELDS_SET.add(ORDER_STATUS_ID);
    }

    public OrderQueryBuilder() {
        super(ID);
    }

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
