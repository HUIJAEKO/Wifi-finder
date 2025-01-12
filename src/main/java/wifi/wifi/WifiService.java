package wifi.wifi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WifiService {
    private static final String API_URL = "http://openapi.seoul.go.kr:8088/6a78484e4d6b6a683730506f5a677a/json/TbPublicWifiInfo/";
    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static int fetchAndSaveWifiData() throws IOException {
        int totalCnt = getTotalCount(); // 전체 데이터 개수 확인
        int count = 0;

        System.out.println("총 데이터 개수: " + totalCnt);

        for (int i = 0; i <= totalCnt / 1000; i++) {
            int start = 1 + (1000 * i);
            int end = Math.min(totalCnt, (i + 1) * 1000);

            String url = API_URL + start + "/" + end;
            System.out.println("API 호출: " + url);

            Request request = new Request.Builder().url(url).get().build();
            try (Response response = okHttpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    System.out.println("응답 데이터 크기: " + responseBody.length());

                    JsonNode jsonNode = objectMapper.readTree(responseBody);
                    JsonNode rows = jsonNode.get("TbPublicWifiInfo").get("row");

                    if (rows != null && rows.isArray()) {
                        System.out.println("데이터 저장 시작");
                        count += WifiDao.insertOrUpdatePublicWifi(rows.toString());
                        System.out.println("현재까지 저장된 데이터 개수: " + count);
                    } else {
                        System.err.println("응답 데이터에 'row' 키가 없습니다: " + responseBody);
                    }
                } else {
                    System.err.println("API 호출 실패: 상태 코드 = " + response.code());
                    System.err.println("응답 메시지: " + response.message());
                }
            } catch (Exception e) {
                System.err.println("API 호출 중 예외 발생: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return count;
    }

    private static int getTotalCount() throws IOException {
        String url = API_URL + "1/1";

        Request request = new Request.Builder().url(url).get().build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                return jsonNode.get("TbPublicWifiInfo")
                        .get("list_total_count")
                        .asInt();
            }
        }
        throw new IOException("API 호출 실패 또는 데이터 없음");
    }
}
