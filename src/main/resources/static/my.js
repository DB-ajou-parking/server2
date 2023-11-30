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

        var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
            message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다



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

    $.ajax({
        type: 'GET',
        url: '/api/parkinglot/search?location=' + roadName,
        success: function (data) {
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
}

function displaySearchResult(data) {
    var resultDiv = $('#searchResult');
    resultDiv.empty();

    if (data.length > 0) {
        for (var i = 0; i < data.length; i++) {
            //resultDiv.append('<p>ParkingLot ID: ' + data[i].id + '</p>');
            resultDiv.append('<p>주차장명: <a href="#" class="parking-link" data-lat="' + data[i].latitude + '" data-lng="' + data[i].longitude + '" data-parking-lot-id="' + data[i].id + '">' + data[i].parkingFacilityName + '</a></p>');
            resultDiv.append('<p>도로명: ' + data[i].locationRoadNameAddress + '</p>');
            resultDiv.append('<p>지번: ' + data[i].locationLandParcelAddress + '</p>');
            //resultDiv.append('<p>분류: ' + data[i].parkingFacilityClassification + '</p>');
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
            }
        });
    } else {
        alert('Please select a parking lot before writing a review.');
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