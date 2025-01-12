package wifi.wifi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@WebServlet("/find-nearest-wifi") // /find-nearest-wifi 경로 처리
public class WifiFindController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // 요청에서 사용자 위치 가져오기
            ObjectMapper objectMapper = new ObjectMapper();
            var requestBody = objectMapper.readTree(req.getReader());
            double userLat = requestBody.get("lat").asDouble();
            double userLnt = requestBody.get("lnt").asDouble();

            // 가장 가까운 와이파이 검색
            List<WifiDto> wifiList = WifiDao.findNearestWifi(userLat, userLnt);

            // JSON 응답 생성
            String jsonResponse = objectMapper.writeValueAsString(wifiList);
            resp.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"success\": false, \"message\": \"와이파이 정보를 가져오는 중 오류가 발생했습니다.\"}");
        }
    }
}
