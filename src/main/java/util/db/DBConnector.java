package util.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnector{

    private static String url;
    private static String user;
    private static String password;
    private static Connection connection;

    private DBConnector() {
    }

    private static void readProperties() throws IOException {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        properties.load(loader.getResourceAsStream("db.properties"));
        url = properties.getProperty("jdbc.url") ;
        user = properties.getProperty("jdbc.user");
        password = properties.getProperty("jdbc.password") ;
    }

    public static Connection getConnection()  {
        try {
            if(connection==null) {
                readProperties();
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public static void close() throws Exception {
        connection.close();
    }
}
