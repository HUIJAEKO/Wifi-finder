package wifi.wifi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wifi.wifi.dao.WifiDao;

import java.io.IOException;

public class WifiService {
    private static final String API_URL = "http://openapi.seoul.go.kr:8088/6a78484e4d6b6a683730506f5a677a/json/TbPublicWifiInfo/";
    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static int fetchAndSaveWifiData() throws IOException {
        int totalCnt = getTotalCount();
        int count = 0;

        for (int i = 0; i <= totalCnt / 1000; i++) {
            int start = 1 + (1000 * i);
            int end = Math.min(totalCnt, (i + 1) * 1000);

            String url = API_URL + start + "/" + end;
            Request request = new Request.Builder().url(url).get().build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonNode rows = objectMapper.readTree(response.body().string())
                            .get("TbPublicWifiInfo")
                            .get("row");

                    if (rows != null && rows.isArray()) {
                        count += WifiDao.insertOrUpdatePublicWifi(rows.toString());
                    }
                }
            }
        }

        return count;
    }

    private static int getTotalCount() throws IOException {
        String url = API_URL + "1/1";

        Request request = new Request.Builder().url(url).get().build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return objectMapper.readTree(response.body().string())
                        .get("TbPublicWifiInfo")
                        .get("list_total_count")
                        .asInt();
            }
        }
        throw new IOException("API 호출 실패 또는 데이터 없음");
    }
}
