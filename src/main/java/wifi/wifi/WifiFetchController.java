package wifi.wifi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet("/fetch-wifi") // 두 경로를 처리
public class WifiFetchController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            WifiDao.createTable(); // 테이블 생성
            int savedCount = WifiService.fetchAndSaveWifiData(); // 데이터 저장
            resp.getWriter().write("{\"success\": true, \"count\": " + savedCount + "}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"success\": false, \"message\": \"데이터 저장 중 오류 발생\"}");
        }
    }
}
