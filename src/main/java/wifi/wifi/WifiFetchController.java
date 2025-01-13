package wifi.wifi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/fetch-wifi")
public class WifiFetchController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        WifiDao.createTable();
        int savedCount = WifiService.fetchAndSaveWifiData();
        resp.getWriter().write("{\"success\": true, \"count\": " + savedCount + "}");
    }
}
