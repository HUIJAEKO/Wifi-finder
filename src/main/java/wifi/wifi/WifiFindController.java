package wifi.wifi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/find-nearest-wifi")
public class WifiFindController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double userLat = Double.parseDouble(req.getParameter("lat"));
        double userLnt = Double.parseDouble(req.getParameter("lnt"));

        List<WifiDto> wifiList = WifiDao.findNearestWifi(userLat, userLnt);

        req.setAttribute("wifiList", wifiList);
        req.getRequestDispatcher("/wifi-display.jsp").forward(req, resp);
    }
}