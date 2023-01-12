package ua.epam.travelagencyms.model.dao.mysql.constants;

public class TourSQLQueries {

    public static final String ADD_TOUR = "INSERT INTO tour (title, persons, price, hot, type_id, hotel_id, image) VALUES (?,?,?,?,?,?,?)";
    public static final String GET_TOUR_BY_ID = "SELECT * FROM tour WHERE id=?";

    public static final String GET_TOURS = "SELECT * FROM tour";
    public static final String GET_TOUR_BY_TITLE = "SELECT * FROM tour WHERE title=?";
    public static final String UPDATE_TOUR = "UPDATE tour SET title=?, persons=?, price=?, hot=?, type_id=?, hotel_id=?, image=? WHERE id=?";
    public static final String DELETE_TOUR = "DELETE FROM tour WHERE id=?";
    public static final String UPDATE_TITLE = "UPDATE tour SET title=? WHERE id=?";
    public static final String UPDATE_IMAGE_CONTENT = "UPDATE tour SET image=? WHERE id=?";
    public static final String GET_SORTED = "SELECT * FROM tour %s";
    public static final String GET_NUMBER_OF_RECORDS = "SELECT COUNT(id) AS numberOfRecords FROM tour %s";
    public static final String NUMBER_OF_RECORDS = "numberOfRecords";



}
