package wifi.wifi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import wifi.wifi.dao.HistoryDao;
import wifi.wifi.dto.HistoryDto;

import java.io.IOException;
import java.util.List;

@WebServlet("/history-list")
public class HistoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<HistoryDto> historyList = HistoryDao.getSearchHistory();
        req.setAttribute("historyList", historyList);
        req.getRequestDispatcher("/history-list.jsp").forward(req, resp);
    }
}
