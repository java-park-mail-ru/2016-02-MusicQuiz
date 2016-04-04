package main;

import database.UsersDataSet;
import org.hibernate.cfg.Configuration;

/**
 * Created by seven-teen on 03.04.16.
 */
public class Config {
    public static Configuration getConfiguration(String dbName) {
        final Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/" + dbName);
        configuration.setProperty("hibernate.connection.username", "user");
        configuration.setProperty("hibernate.connection.password", "1234");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        return configuration;
    }
}
