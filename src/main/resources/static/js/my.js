var currentParkingLotId = null;

var mapContainer = document.getElementById('map');
var mapOption = {
    center: new kakao.maps.LatLng(37.28444, 127.0444),
    level: 2,
    mapTypeId: kakao.maps.MapTypeId.ROADMAP
};
var map = new kakao.maps.Map(mapContainer, mapOption);
map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
var markers = []; // Array to store markers




// HTML5의 geolocation으로 사용할 수 있는지 확인합니다
if (navigator.geolocation) {

    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function(position) {

        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
        currentUserLocation = {
            latitude: lat,
            longitude: lon
        };



    });

} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
        message = 'geolocation을 사용할수 없어요..'


}










function addMarker(data) {
    var marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(data.latitude, data.longitude),
        map: map,
        title: data.parkingFacilityName
    });

    var infowindow = new kakao.maps.InfoWindow({
        content: '<div style="padding:5px;">' + data.parkingFacilityName + '</div>'
    });

    kakao.maps.event.addListener(marker, 'click', function () {
        infowindow.open(map, marker);
    });

    markers.push(marker); // Store the marker reference
}

function clearMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = []; // Clear the markers array
}

function searchParkingLot() {
    clearMarkers(); // Clear existing markers before making a new search
    var roadName = $('#searchRoadName').val();


    navigator.geolocation.getCurrentPosition(function(position) {
        var currentUserLocation = {
            latitude: position.coords.latitude,
            longitude: position.coords.longitude
        };


        $.ajax({
            type: 'GET',
            url: '/api/parkinglot/search?location=' + roadName,
            success: function (data, currentUserLocation) {
                displaySearchResult(data);
                if (data.length > 0) {
                    // Add a marker at the location of the first result
                    addMarker(data[0]);

                    // Center the map on the location of the first result
                    map.setCenter(new kakao.maps.LatLng(data[0].latitude, data[0].longitude));
                }
            },
            error: function () {
                alert('Error fetching data');
            }
        });
    });
}


function displaySearchResult(data) {
    var resultDiv = $('#searchResult');
    resultDiv.empty();

    if (data.length > 0) {
        // Sort data based on distance in ascending order
        data.sort(function (a, b) {
            var distanceA = calculateDistance(
                currentUserLocation.latitude,
                currentUserLocation.longitude,
                a.latitude,
                a.longitude
            );
            var distanceB = calculateDistance(
                currentUserLocation.latitude,
                currentUserLocation.longitude,
                b.latitude,
                b.longitude
            );
            return distanceA - distanceB;
        });

        for (var i = 0; i < data.length; i++) {
            resultDiv.append('<p>주차장명: <a href="#" class="parking-link" data-lat="' + data[i].latitude + '" data-lng="' + data[i].longitude + '" data-parking-lot-id="' + data[i].id + '">' + data[i].parkingFacilityName + '</a></p>');
            resultDiv.append('<p>도로명: ' + data[i].locationRoadNameAddress + '</p>');
            resultDiv.append('<p>지번: ' + data[i].locationLandParcelAddress + '</p>');
            var distance = calculateDistance(
                currentUserLocation.latitude,
                currentUserLocation.longitude,
                data[i].latitude,
                data[i].longitude
            );

            resultDiv.append('<p>거리: ' + distance.toFixed(2) + ' km</p>');
            resultDiv.append('<hr>');
        }

        // Add click event for parking lot names
        $('.parking-link').click(function () {
            var lat = $(this).data('lat');
            var lng = $(this).data('lng');
            currentParkingLotId = $(this).data('parking-lot-id'); // Store the current parking lot ID

            map.setCenter(new kakao.maps.LatLng(lat, lng));
            fetchReviews(currentParkingLotId);

            // Fetch and display detailed parking lot information
            fetchDetailedParkingLotInfo(currentParkingLotId);
        });
    } else {
        resultDiv.append('<p>No matching parking lots found</p>');
    }

    // Add markers to the map
    for (var i = 0; i < data.length; i++) {
        addMarker(data[i]);
    }

}






















function toRadians(degrees) {
    return degrees * Math.PI / 180;
}

function calculateDistance(lat1, lon1, lat2, lon2) {
    const R = 6371; // 지구의 반지름 (단위: 킬로미터)

    // 위도 및 경도를 라디안으로 변환
    const radLat1 = toRadians(lat1);
    const radLon1 = toRadians(lon1);
    const radLat2 = toRadians(lat2);
    const radLon2 = toRadians(lon2);

    // 위도 및 경도의 차이 계산
    const dLat = radLat2 - radLat1;
    const dLon = radLon2 - radLon1;

    // Haversine 공식을 이용한 거리 계산
    const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(radLat1) * Math.cos(radLat2) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);

    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    const distance = R * c; // 거리 (단위: 킬로미터)

    return distance;
}




// Add a new function to fetch and display detailed parking lot information
function fetchDetailedParkingLotInfo(parkingLotId) {
    $.ajax({
        type: 'GET',
        url: '/api/parkinglot/' + parkingLotId,
        success: function (parkingLot) {
            // Display detailed information in the designated div
            displayDetailedParkingLotInfo(parkingLot);

        },
        error: function () {
            alert('Error fetching detailed parking lot information');
        }
    });
}

// Add a new function to display detailed parking lot information
function displayDetailedParkingLotInfo(parkingLot) {
    var detailedInfoDiv = $('#detailedParkingLotInfo');
    detailedInfoDiv.empty();
    // Append detailed information to the div
    detailedInfoDiv.append('<h3>' + parkingLot.parkingFacilityName + '</h3>');
    detailedInfoDiv.append('<p>요금정보: ' + parkingLot.feeInformation + '</p>');
    detailedInfoDiv.append('<p>결제방법: ' + parkingLot.paymentMethod + '</p>');
    detailedInfoDiv.append('<p>주차기본시간: ' + parkingLot.basicParkingTime +'분'+ '</p>');
    detailedInfoDiv.append('<p>주차기본요금: ' + parkingLot.basicParkingFee +'원'+ '</p>');
    detailedInfoDiv.append('<p>추가단위시간: ' + parkingLot.additionalUnitTime +'분'+ '</p>');
    detailedInfoDiv.append('<p>추가단위요금: ' + parkingLot.additionalUnitFee +'원'+ '</p>');
    detailedInfoDiv.append('<p>1일주차권요금적용시간: ' + parkingLot.dailyParkingTicketApplicationTime +'시간'+ '</p>');
    detailedInfoDiv.append('<p>1일주차권요금: ' + parkingLot.dailyParkingTicketFee +'원'+ '</p>');
    detailedInfoDiv.append('<p>월정기권요금: ' + parkingLot.monthlySubscriptionFee +'원'+ '</p>');
    detailedInfoDiv.append('<p>운영요일: ' + parkingLot.operatingDays + '</p>');
    detailedInfoDiv.append('<p>분류: ' + parkingLot.parkingFacilityClassification + '</p>');
    detailedInfoDiv.append('<p>유형: ' + parkingLot.parkingFacilityType + '</p>');
    detailedInfoDiv.append('<p>구획수: ' + parkingLot.numberofParkingSpaces + '</p>');
    detailedInfoDiv.append('<p>평일운영시작시각: ' + parkingLot.weekdayOperatingStartTime + '</p>');
    detailedInfoDiv.append('<p>평일운영종료시각: ' + parkingLot.weekdayOperatingEndTime + '</p>');
    detailedInfoDiv.append('<p>토요일운영시작시각: ' + parkingLot.saturdayOperatingStartTime + '</p>');
    detailedInfoDiv.append('<p>토요일운영종료시각: ' + parkingLot.saturdayOperatingEndTime + '</p>');
    detailedInfoDiv.append('<p>공휴일운영시작시각: ' + parkingLot.holidayOperatingStartTime + '</p>');
    detailedInfoDiv.append('<p>공휴일운영종료시각: ' + parkingLot.holidayOperatingEndTime + '</p>');
    detailedInfoDiv.append('<p>특기사항: ' + parkingLot.specialNotes + '</p>');
    detailedInfoDiv.append('<p>관리기관명: ' + parkingLot.managementOrganizationName + '</p>');
    detailedInfoDiv.append('<p>전화번호: ' + parkingLot.telephoneNumber + '</p>');
    detailedInfoDiv.append('<p>장애인전용주차구역보유여부: ' + parkingLot.presenceofDisabledParkingSpaces + '</p>');
    detailedInfoDiv.append('<p>데이터기준일자: ' + parkingLot.dataReferenceDate + '</p>');

    // Show the detailed information section
    detailedInfoDiv.show();
}

function commentWrite() {
    var commentWriter = $('#commentWriter').val();
    var commentContents = $('#commentContents').val();

    if (currentParkingLotId !== null) {
        $.ajax({
            type: 'POST',
            url: '/api/parkinglot/' + currentParkingLotId + '/reviews',
            contentType: 'application/json',
            data: JSON.stringify({
                author: commentWriter,
                reviewText: commentContents
            }),
            success: function (response) {
                // Clear input fields
                $('#commentWriter').val('');
                $('#commentContents').val('');

                // Fetch and display updated reviews
                fetchReviews(currentParkingLotId);
            },
            error: function () {
                alert('Error adding review');
                console.log(this.error);
            }
        });
    } else {
        alert('리뷰를 작성하기 위해 주차장을 선택해주세요');
        console.log(this.error);
    }
}

function fetchReviews(parkingLotId) {
    $.ajax({
        type: 'GET',
        url: '/api/parkinglot/' + parkingLotId + '/reviews',
        success: function (reviews) {
            // Clear existing reviews
            $('#Reviews tbody').empty();

            // Append the new reviews
            for (var i = 0; i < reviews.length; i++) {
                $('#Reviews tbody').append(
                    '<tr>' +
                    '<td>' + (i + 1) + '</td>' +
                    '<td>' + reviews[i].author + '</td>' +
                    '<td>' + reviews[i].reviewText + '</td>' +
                    '<td>' + reviews[i].timestamp + '</td>' +
                    '</tr>'
                );
            }
        },
        error: function () {
            alert('Error fetching reviews');
        }
    });
}



$(document).ready(function () {
    $('#serveyBtn').click(function () {
        $('#satisfactionModal').modal('show');
    });
});

// 모달 닫힐 때 입력 내용 초기화
$('#satisfactionModal').on('hidden.bs.modal', function () {
    $('#satisfactionSurveyText').val('');
});

// 만족도 조사 제출 함수
function submitSatisfactionSurvey() {
    var surveyText = $('#satisfactionSurveyText').val();
    // 여기에 만족도 조사를 서버로 전송하는 코드를 추가할 수 있습니다.
    // 예시: $.post('/api/satisfaction', { text: surveyText }, function(response) { console.log(response); });
    // 모달 닫기
    $('#satisfactionModal').modal('hide');
}