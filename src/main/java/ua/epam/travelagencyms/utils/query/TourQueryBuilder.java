package ua.epam.travelagencyms.utils.query;

import java.util.HashSet;
import java.util.Set;

import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * TourQueryBuilder. Able to build query to obtain sorted, ordered and limited list of tours
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class TourQueryBuilder extends QueryBuilder {

    /**
     * Contains set of allowed sort fields
     */
    private static final Set<String> TOUR_SORT_FIELDS_SET = new HashSet<>();

    static {
        TOUR_SORT_FIELDS_SET.add(ID);
        TOUR_SORT_FIELDS_SET.add(TITLE);
        TOUR_SORT_FIELDS_SET.add(PERSONS);
        TOUR_SORT_FIELDS_SET.add(PRICE);
        TOUR_SORT_FIELDS_SET.add(HOT);
        TOUR_SORT_FIELDS_SET.add(DISCOUNT);
    }

    /**
     * set id as default sort field
     */
    public TourQueryBuilder() {
        super(ID);
    }

    /**
     * @return empty String - no need to group by in tourQuery
     */
    @Override
    protected String getGroupByQuery() {
        return "";
    }

    @Override
    protected String checkSortField(String sortField) {
        if (TOUR_SORT_FIELDS_SET.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return ID;
    }

    @Override
    protected String getSortQuery() {
        return " ORDER BY hot DESC, " + sortField + " " + order;
    }
}

