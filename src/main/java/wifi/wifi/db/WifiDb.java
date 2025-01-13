package wifi.wifi.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class WifiDb {
    public static Connection connectDB() {
        String url = "jdbc:sqlite:wifi_info.db";
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
