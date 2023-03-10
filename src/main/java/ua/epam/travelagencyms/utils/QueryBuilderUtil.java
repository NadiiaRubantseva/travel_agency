package ua.epam.travelagencyms.utils;

import ua.epam.travelagencyms.utils.query.OrderQueryBuilder;
import ua.epam.travelagencyms.utils.query.QueryBuilder;
import ua.epam.travelagencyms.utils.query.TourQueryBuilder;
import ua.epam.travelagencyms.utils.query.UserQueryBuilder;

/**
 * Factory to return concrete query builders
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class QueryBuilderUtil {

    /**
     * @return UserQueryBuilder to create query for get sorted list of users
     */
    public static QueryBuilder userQueryBuilder() {
        return new UserQueryBuilder();
    }

    /**
     * @return TourQueryBuilder to create query for get sorted list of tours
     */
    public static QueryBuilder orderQueryBuilder() {
        return new OrderQueryBuilder();
    }

    /**
     * @return OrderQueryBuilder to create query for get sorted list of orders
     */
    public static QueryBuilder tourQueryBuilder() {
        return new TourQueryBuilder();
    }

    private QueryBuilderUtil() {}
}