<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="wifi.wifi.service.WifiDto" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="css/wifi-display.css">

  <title>근처 WIFI 정보</title>
</head>
<body>
<h1>근처 WIFI 정보</h1>

<%
  List<WifiDto> wifiList = (List<WifiDto>) request.getAttribute("wifiList");
  if (wifiList == null || wifiList.isEmpty()) {
%>
<p class="no-data">근처 와이파이 정보를 찾을 수 없습니다. 다시 시도해주세요.</p>
<%
} else {
%>
<table>
  <thead>
  <tr>
    <th>거리(Km)</th>
    <th>관리번호</th>
    <th>자치구</th>
    <th>와이파이명</th>
    <th>도로명주소</th>
    <th>상세주소</th>
    <th>설치기관</th>
    <th>서비스구분</th>
    <th>망종류</th>
    <th>설치년도</th>
    <th>실내외구분</th>
    <th>WIFI접속환경</th>
    <th>X좌표</th>
    <th>Y좌표</th>
    <th>작업일자</th>
  </tr>
  </thead>
  <tbody>
  <%
    for (WifiDto wifi : wifiList) {
  %>
  <tr>
    <td><%= wifi.getDistance() %></td>
    <td><%= wifi.getMgrNo() %></td>
    <td><%= wifi.getWrdofc() %></td>
    <td><%= wifi.getMainNm() %></td>
    <td><%= wifi.getAdres1() %></td>
    <td><%= wifi.getAdres2() %></td>
    <td><%= wifi.getInstlMby() %></td>
    <td><%= wifi.getSvcSe() %></td>
    <td><%= wifi.getCmcwr() %></td>
    <td><%= wifi.getCnstcYear() %></td>
    <td><%= wifi.getInoutDoor() %></td>
    <td><%= wifi.getRemars3() %></td>
    <td><%= wifi.getLat() %></td>
    <td><%= wifi.getLnt() %></td>
    <td><%= wifi.getWorkDttm() %></td>
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
