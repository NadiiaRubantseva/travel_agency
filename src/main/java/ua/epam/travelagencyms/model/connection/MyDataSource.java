package ua.epam.travelagencyms.model.connection;

import com.zaxxer.hikari.*;
import javax.sql.DataSource;
import java.util.Properties;

import static ua.epam.travelagencyms.model.connection.ConnectionConstants.*;


public class MyDataSource {
    private static DataSource dataSource;

    public static synchronized DataSource getDataSource(Properties properties) {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty(URL_PROPERTY));
            config.setUsername(properties.getProperty(USER_NAME));
            config.setPassword(properties.getProperty(PASSWORD));
            config.setDriverClassName(properties.getProperty(DRIVER));
            config.setDataSourceProperties(properties);
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    private MyDataSource() {
    }
}