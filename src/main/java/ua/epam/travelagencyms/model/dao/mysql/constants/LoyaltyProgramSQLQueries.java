package ua.epam.travelagencyms.model.dao.mysql.constants;

public class LoyaltyProgramSQLQueries {

    public static final String UPDATE = "UPDATE loyalty_program SET step=?, discount=?, max_discount=? WHERE id=1";
    public static final String GET = "SELECT * FROM loyalty_program WHERE id=1";

}
