<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/index.css">
    <title>와이파이 정보 구하기</title>
</head>
<body>
<h1>와이파이 정보 구하기</h1>
<nav>
    <a href="#">위치 히스토리 목록</a> |
    <a href="#" id="fetch-wifi-link">Open API 와이파이 정보 가져오기</a>
</nav>

<div>
    <form id="wifiForm" action="<%= request.getContextPath() %>/find-nearest-wifi" method="post">
        <label for="lat">LAT:</label>
        <input type="text" id="lat" name="lat" readonly>
        <label for="lnt">LNT:</label>
        <input type="text" id="lnt" name="lnt" readonly>
        <button type="button" id="getLocationBtn">내 위치</button>
        <button type="submit" id="getWifiInfoBtn">근처 WIFI 정보 보기</button>
    </form>
</div>

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
    <tr>
        <td colspan="15" class="info">위치를 가져온 후 "근처 WIFI 정보 보기"를 클릭하세요.</td>
    </tr>
    </tbody>
</table>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        // 위치 가져오기
        document.getElementById('getLocationBtn').addEventListener('click', () => {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(
                    (position) => {
                        document.getElementById('lat').value = position.coords.latitude.toFixed(6);
                        document.getElementById('lnt').value = position.coords.longitude.toFixed(6);
                        alert('위치가 성공적으로 가져와졌습니다.');
                    },
                    (error) => {
                        alert('위치를 가져오는 데 실패했습니다.');
                    }
                );
            } else {
                alert('현재 브라우저는 위치 정보를 지원하지 않습니다.');
            }
        });

        // Open API 와이파이 데이터 가져오기
        document.getElementById('fetch-wifi-link').addEventListener('click', async function (event) {
            event.preventDefault();
            try {
                const response = await fetch('<%= request.getContextPath() %>/fetch-wifi', { method: 'POST' });
                if (!response.ok) throw new Error('데이터 가져오기 실패');
                const data = await response.json();
                alert(data.success ? `데이터 저장 완료 (${data.count}개)` : '데이터 저장 중 문제가 발생했습니다.');
            } catch (error) {
                alert('오류 발생: ' + error.message);
            }
        });
    });
</script>
</body>
</html>
