<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="wifi.wifi.dto.HistoryDto" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="css/history-list.css">
  <title>검색 히스토리</title>
</head>
<body>
<h1>검색 히스토리 목록</h1>

<%
  List<HistoryDto> historyList = (List<HistoryDto>) request.getAttribute("historyList");
  if (historyList == null || historyList.isEmpty()) {
%>
<p class="no-data">저장된 검색 히스토리가 없습니다.</p>
<%
} else {
%>
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>LAT</th>
    <th>LNT</th>
    <th>검색 시간</th>
  </tr>
  </thead>
  <tbody>
  <%
    for (HistoryDto history : historyList) {
  %>
  <tr>
    <td><%= history.getId() %></td>
    <td><%= history.getLat() %></td>
    <td><%= history.getLnt() %></td>
    <td><%= history.getSearchTime() %></td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>
<%
  }
%>

</body>
</html>
