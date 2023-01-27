package ua.epam.travelagencyms.utils.query;

import java.util.HashSet;
import java.util.Set;

import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class TourQueryBuilder extends QueryBuilder {
    private static final Set<String> TOUR_SORT_FIELDS_SET = new HashSet<>();

    static {
        TOUR_SORT_FIELDS_SET.add(ID);
        TOUR_SORT_FIELDS_SET.add(TITLE);
        TOUR_SORT_FIELDS_SET.add(PERSONS);
        TOUR_SORT_FIELDS_SET.add(PRICE);
        TOUR_SORT_FIELDS_SET.add(HOT);
        TOUR_SORT_FIELDS_SET.add(DISCOUNT);
    }

    public TourQueryBuilder() {
        super(ID);
    }

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


}
