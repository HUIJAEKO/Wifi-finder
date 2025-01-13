package wifi.wifi.dao;

import wifi.wifi.dto.HistoryDto;
import wifi.wifi.db.WifiDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao {
    public static void createHistoryTable() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS search_history (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    lat REAL NOT NULL,
                    lnt REAL NOT NULL,
                    search_time TEXT DEFAULT CURRENT_TIMESTAMP
                );
                """;
        try (Connection connection = WifiDb.connectDB();
             PreparedStatement pstmt = connection.prepareStatement(createTableSQL)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveSearchHistory(double lat, double lnt) {
        String insertSQL = "INSERT INTO search_history (lat, lnt) VALUES (?, ?);";
        try (Connection connection = WifiDb.connectDB();
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lnt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<HistoryDto> getSearchHistory() {
        String query = "SELECT * FROM search_history ORDER BY search_time DESC;";
        List<HistoryDto> historyList = new ArrayList<>();
        try (Connection connection = WifiDb.connectDB();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                historyList.add(new HistoryDto(
                        rs.getInt("id"),
                        rs.getDouble("lat"),
                        rs.getDouble("lnt"),
                        rs.getString("search_time")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }
}

