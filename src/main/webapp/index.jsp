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
    <a href="#">홈</a> |
    <a href="#">위치 히스토리 목록</a> |
    <a href="#" id="fetch-wifi-link">Open API 와이파이 정보 가져오기</a>
</nav>

<div>
    <label for="lat">LAT:</label>
    <input type="text" id="lat" name="lat" readonly>
    <label for="lnt">LNT:</label>
    <input type="text" id="lnt" name="lnt" readonly>
    <button id="getLocationBtn">내 위치</button>
    <button id="getWifiInfoBtn">근처 WIFI 정보 보기</button>
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
    <tbody id="wifi-table-body">
    <tr>
        <td colspan="15" class="info">위치를 가져온 후 "근처 WIFI 정보 보기"를 클릭하세요.</td>
    </tr>
    </tbody>
</table>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        // Open API 와이파이 데이터 가져오기
        document.getElementById('fetch-wifi-link').addEventListener('click', async function (event) {
            event.preventDefault();
            try {
                const response = await fetch('<%= request.getContextPath() %>/fetch-wifi', { method: 'POST' });

                if (!response.ok) {
                    throw new Error('데이터 가져오기 실패');
                }

                const data = await response.json();

                if (data.success) {
                    alert("데이터 저장이 완료되었습니다.");
                } else {
                    alert(data.message || '데이터를 가져오는 중 문제가 발생했습니다.');
                }
            } catch (error) {
                console.error(error);
                alert('오류 발생: ' + error.message);
            }
        });

        // 내 위치 가져오기 버튼 클릭
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

        // 근처 WIFI 정보 가져오기 버튼 클릭
        document.getElementById('getWifiInfoBtn').addEventListener('click', async function () {
            const lat = document.getElementById('lat').value;
            const lng = document.getElementById('lnt').value;

            if (!lat || !lng) {
                alert('위치를 먼저 가져와주세요.');
                return;
            }

            try {
                console.log("근처 WIFI 정보를 가져오는 요청 시작");
                console.log("전송할 데이터:", { lat: parseFloat(lat), lnt: parseFloat(lng) });

                const response = await fetch('<%= request.getContextPath() %>/find-nearest-wifi', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ lat: parseFloat(lat), lnt: parseFloat(lng) })
                });

                console.log("서버 응답 수신");

                if (!response.ok) {
                    console.error("응답 상태 코드:", response.status);
                    console.error("응답 상태 텍스트:", response.statusText);
                    throw new Error(`서버에서 데이터를 가져오는데 실패했습니다.`);
                }

                const data = await response.json();
                console.log("응답 데이터 수신:", data);

                const tableBody = document.getElementById('wifi-table-body');

                // 테이블 초기화
                tableBody.innerHTML = '';
                console.log("테이블 초기화 완료");

                // 데이터 처리 및 테이블 업데이트
                if (!Array.isArray(data)) {
                    throw new Error("서버 응답 데이터가 배열 형식이 아닙니다.");
                }

                console.log("데이터 처리 시작 (" + data.length + "개의 항목)");
                data.forEach(wifi => {
                    const row = `
                        <tr>
                            <td>${wifi.distance}</td>
                            <td>${wifi.mgrNo}</td>
                            <td>${wifi.wrdofc}</td>
                            <td>${wifi.mainNm || ''}</td>
                            <td>${wifi.adres1 || ''}</td>
                            <td>${wifi.adres2 || ''}</td>
                            <td>${wifi.instlMby || ''}</td>
                            <td>${wifi.svcSe || ''}</td>
                            <td>${wifi.cmcwr || ''}</td>
                            <td>${wifi.cnstcYear || ''}</td>
                            <td>${wifi.inoutDoor || ''}</td>
                            <td>${wifi.remars3 || ''}</td>
                            <td>${wifi.lat ? wifi.lat.toFixed(6) : ''}</td>
                            <td>${wifi.lnt ? wifi.lnt.toFixed(6) : ''}</td>
                            <td>${wifi.workDttm || ''}</td>
                        </tr>
                     `;
                    tableBody.insertAdjacentHTML('beforeend', row);
                });

                console.log("테이블 업데이트 완료");
            } catch (error) {
                console.error("근처 WIFI 정보 가져오기 중 오류:", error.message);
                console.error(error);
                alert('데이터를 가져오는 중 오류가 발생했습니다. 콘솔을 확인하세요.');
            }

        });
    });
</script>
</body>
</html>
