package wifi.wifi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import wifi.wifi.dao.HistoryDao;
import wifi.wifi.dao.WifiDao;
import wifi.wifi.service.WifiService;

import java.io.IOException;

@WebServlet("/fetch-wifi")
public class WifiFetchController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        WifiDao.createTable();
        HistoryDao.createHistoryTable();

        int savedCount = WifiService.fetchAndSaveWifiData();
        resp.getWriter().write("{\"success\": true, \"count\": " + savedCount + "}");
    }
}
