package ua.epam.travelagencyms.utils.query;

import java.util.HashSet;
import java.util.Set;

import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class UserQueryBuilder extends QueryBuilder {
    private static final Set<String> USER_SORT_FIELDS_SET = new HashSet<>();

    static {
        USER_SORT_FIELDS_SET.add(ID);
        USER_SORT_FIELDS_SET.add(EMAIL);
        USER_SORT_FIELDS_SET.add(NAME);
        USER_SORT_FIELDS_SET.add(SURNAME);
    }

    public UserQueryBuilder() {
        super(ID);
    }

    @Override
    protected String getGroupByQuery() {
        return "";
    }

    @Override
    protected String checkSortField(String sortField) {
        if (USER_SORT_FIELDS_SET.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return ID;
    }
}
