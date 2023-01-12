package ua.epam.travelagencyms.utils;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.utils.query.UserQueryBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static ua.epam.travelagencyms.Constants.*;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.*;

class QueryBuilderTest {

    @Test
    void testUserQueryBuilder() {
        assertInstanceOf(UserQueryBuilder.class, userQueryBuilder());
    }


    @Test
    void testSetRoleFilter() {
        String query = userQueryBuilder().setRoleFilter("4").getQuery();
        assertTrue(query.contains(" role_id=4 "));
    }

    @Test
    void testSetRoleFilterBadFilter() {
        String query = userQueryBuilder().setRoleFilter("a").getQuery();
        assertFalse(query.contains("role_id"));
    }

    @Test
    void testSetRoleFilterNoRole() {
        String query = userQueryBuilder().getQuery();
        assertFalse(query.contains("role_id"));
    }

    @Test
    void testSetSortField() {
        String query = userQueryBuilder().setSortField(EMAIL_FIELD).getQuery();
        assertTrue(query.contains(" ORDER BY email ASC "));
    }



    @Test
    void testSetSortFieldEmpty() {
        String query = userQueryBuilder().getQuery();
        assertTrue(query.contains(" ORDER BY id ASC "));
    }


    @Test
    void testSetOrder() {
        String query = userQueryBuilder().setOrder(DESC).getQuery();
        assertTrue(query.contains(" ORDER BY id DESC "));
    }

    @Test
    void testSetLimitsNoLimits() {
        String query = userQueryBuilder().getQuery();
        assertTrue(query.contains(" LIMIT 0, 5"));
    }

    @Test
    void testSetLimits() {
        String query = userQueryBuilder().setLimits("5", "20").getQuery();
        assertTrue(query.contains(" LIMIT 5, 20"));
    }

    @Test
    void testSetWrongLimits() {
        String query = userQueryBuilder().setLimits("a", "a").getQuery();
        assertTrue(query.contains(" LIMIT 0, 5"));
    }

    @Test
    void testGetQuery() {
        String check = " WHERE role_id=3 ORDER BY name DESC LIMIT 3, 3";
        String query = userQueryBuilder()
                .setRoleFilter("3")
                .setSortField(NAME_FIELD)
                .setOrder(DESC)
                .setLimits("3", "3")
                .getQuery()
                .replaceAll("\\s+", " ");
        assertEquals(check, query);
    }

    @Test
    void testGetRecordQuery() {
        String check = " WHERE role_id=3 ";
        String query = userQueryBuilder()
                .setRoleFilter("3")
                .getRecordQuery()
                .replaceAll("\\s+", " ");
        assertEquals(check, query);
    }

}