package ua.epam.travelagencyms.utils;

import ua.epam.travelagencyms.utils.query.*;

public class QueryBuilderUtil {
    public static QueryBuilder userQueryBuilder() {
        return new UserQueryBuilder();
    }

    public static QueryBuilder orderQueryBuilder() {
        return new OrderQueryBuilder();
    }

    public static QueryBuilder tourQueryBuilder() {
        return new TourQueryBuilder();
    }

    private QueryBuilderUtil() {}
}