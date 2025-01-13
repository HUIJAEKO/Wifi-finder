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
        try {
            double userLat = Double.parseDouble(req.getParameter("lat"));
            double userLnt = Double.parseDouble(req.getParameter("lnt"));
            // 가장 가까운 와이파이 검색
            List<WifiDto> wifiList = WifiDao.findNearestWifi(userLat, userLnt);

            // JSP로 데이터 전달
            req.setAttribute("wifiList", wifiList);

            // JSP 페이지로 포워딩
            req.getRequestDispatcher("/wifi-display.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "와이파이 정보를 가져오는 중 오류가 발생했습니다.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}