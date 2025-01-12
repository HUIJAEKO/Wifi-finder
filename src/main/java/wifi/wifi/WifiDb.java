package wifi.wifi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class WifiDb {
    public static Connection connectDB() {
        String url = "jdbc:sqlite:wifi_info.db";


        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQLite 데이터베이스 연결 실패");
            e.printStackTrace();
        }
        return null;
    }
}
