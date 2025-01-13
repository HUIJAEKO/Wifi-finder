package wifi.wifi.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import wifi.wifi.db.WifiDb;
import wifi.wifi.service.WifiDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WifiDao {
    public static void createTable() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS wifi_info (
                    mgr_no TEXT PRIMARY KEY,
                    wrdofc TEXT,
                    main_nm TEXT,
                    adres1 TEXT,
                    adres2 TEXT,
                    instl_floor TEXT,
                    instl_ty TEXT,
                    instl_mby TEXT,
                    svc_se TEXT,
                    cmcwr TEXT,
                    cnstc_year TEXT,
                    inout_door TEXT,
                    remars3 TEXT,
                    lat REAL,
                    lnt REAL,
                    work_dttm TEXT
                );
                """;

        try (Connection connection = WifiDb.connectDB();
             PreparedStatement pstmt = connection.prepareStatement(createTableSQL)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int insertOrUpdatePublicWifi(String jsonArrayString) {
        String upsertSQL = """
                INSERT OR REPLACE INTO wifi_info (
                    mgr_no, wrdofc, main_nm, adres1, adres2, instl_floor, instl_ty, instl_mby,
                    svc_se, cmcwr, cnstc_year, inout_door, remars3, lat, lnt, work_dttm
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        int count = 0;
        ObjectMapper objectMapper = new ObjectMapper();

        try (Connection connection = WifiDb.connectDB();
             PreparedStatement pstmt = connection.prepareStatement(upsertSQL)) {

            JsonNode jsonArray = objectMapper.readTree(jsonArrayString);
            for (JsonNode wifi : jsonArray) {
                pstmt.setString(1, wifi.get("X_SWIFI_MGR_NO").asText());
                pstmt.setString(2, wifi.get("X_SWIFI_WRDOFC").asText());
                pstmt.setString(3, wifi.get("X_SWIFI_MAIN_NM").asText());
                pstmt.setString(4, wifi.get("X_SWIFI_ADRES1").asText());
                pstmt.setString(5, wifi.get("X_SWIFI_ADRES2").asText());
                pstmt.setString(6, wifi.get("X_SWIFI_INSTL_FLOOR").asText());
                pstmt.setString(7, wifi.get("X_SWIFI_INSTL_TY").asText());
                pstmt.setString(8, wifi.get("X_SWIFI_INSTL_MBY").asText());
                pstmt.setString(9, wifi.get("X_SWIFI_SVC_SE").asText());
                pstmt.setString(10, wifi.get("X_SWIFI_CMCWR").asText());
                pstmt.setString(11, wifi.get("X_SWIFI_CNSTC_YEAR").asText());
                pstmt.setString(12, wifi.get("X_SWIFI_INOUT_DOOR").asText());
                pstmt.setString(13, wifi.get("X_SWIFI_REMARS3").asText());
                pstmt.setDouble(14, wifi.get("LAT").asDouble());
                pstmt.setDouble(15, wifi.get("LNT").asDouble());
                pstmt.setString(16, wifi.get("WORK_DTTM").asText());
                pstmt.addBatch();
                count++;
            }
            pstmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public static List<WifiDto> findNearestWifi(double userLat, double userLnt) {
        String query = """
                SELECT *, (
                    6371 * acos(
                        cos(radians(?)) *
                        cos(radians(lat)) *
                        cos(radians(lnt) - radians(?)) +
                        sin(radians(?)) *
                        sin(radians(lat))
                    )
                ) AS distance
                FROM wifi_info
                ORDER BY distance ASC
                LIMIT 20;
                """;

        List<WifiDto> wifiList = new ArrayList<>();

        try (Connection connection = WifiDb.connectDB();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setDouble(1, userLat);
            pstmt.setDouble(2, userLnt);
            pstmt.setDouble(3, userLat);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    wifiList.add(new WifiDto(
                            rs.getString("mgr_no"),
                            rs.getString("wrdofc"),
                            rs.getString("main_nm"),
                            rs.getString("adres1"),
                            rs.getString("adres2"),
                            rs.getString("instl_floor"),
                            rs.getString("instl_ty"),
                            rs.getString("instl_mby"),
                            rs.getString("svc_se"),
                            rs.getString("cmcwr"),
                            rs.getString("cnstc_year"),
                            rs.getString("inout_door"),
                            rs.getString("remars3"),
                            rs.getDouble("lat"),
                            rs.getDouble("lnt"),
                            rs.getString("work_dttm"),
                            rs.getDouble("distance")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wifiList;
    }
}


