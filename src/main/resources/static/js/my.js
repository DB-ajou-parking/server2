var currentParkingLotId = null;
var currentUserId = null;

var mapContainer = document.getElementById('map');
var mapOption = {
    center: new kakao.maps.LatLng(37.28444, 127.0444),
    level: 3,
    mapTypeId: kakao.maps.MapTypeId.ROADMAP
};
var map = new kakao.maps.Map(mapContainer, mapOption);
//map.removeOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);

var markers = []; // Array to store markers





//로드뷰를 표시할 div
var roadviewContainer = document.getElementById('roadview');

// 로드뷰 위치
var rvPosition = new kakao.maps.LatLng(37.56613, 126.97853);

//로드뷰 객체를 생성한다
var roadview = new kakao.maps.Roadview(roadviewContainer, {
    pan: 0, // 로드뷰 처음 실행시에 바라봐야 할 수평 각
    tilt: 0, // 로드뷰 처음 실행시에 바라봐야 할 수직 각
    zoom: 0// 로드뷰 줌 초기값
});
var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
var position = new kakao.maps.LatLng(37.28379, 127.0449);
roadviewClient.getNearestPanoId(position, 50, function(panoId) {
    roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
});


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

    var locPosition = new kakao.maps.LatLng(37.28444, 127.0444),
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
                alert('검색 데이터를 가져오는 중에 오류가 발생했습니다.');
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
            console.log('Latitude:', lat, 'Longitude:', lng);


            map.setCenter(new kakao.maps.LatLng(lat, lng));
            fetchReviews(currentParkingLotId);

            // Fetch and display detailed parking lot information
            fetchDetailedParkingLotInfo(currentParkingLotId);



            // Toggle the reviews section with animation
            $('#Reviews').css('right', '0');
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
            alert('자세한 주차장 정보를 가져오는 중에 오류가 발생했습니다.');
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
    //var commentWriter = $('#commentWriter').val();
    var commentContents = $('#commentContents').val();
    //var loggedInUserId;

    if (currentParkingLotId !== null) {
        $.ajax({
            type: 'POST',
            url: '/api/parkinglot/' + currentParkingLotId + '/reviews',
            contentType: 'application/json',
            data: JSON.stringify({
                //user_id: loggedInUserId,
                reviewText: commentContents
            }),
            success: function (response) {
                // Clear input fields
                //loggedInUserId = response.user_id;
                //$('#commentWriter').val('');
                $('#commentContents').val('');

                // Fetch and display updated reviews
                fetchReviews(currentParkingLotId);
            },
            error: function () {
                alert('리뷰를 추가하는 중에 오류가 발생했습니다.');
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
                    '<td>' + reviews[i].reviewText + '</td>' +
                    '</tr>'
                );
            }

            $('#ShowMoreReviewstable tbody').empty();

            // Append the new reviews to the modal's content
            for (var i = 0; i < reviews.length; i++) {
                $('#ShowMoreReviewstable tbody').append(
                    '<tr>' +
                    '<td>' + (i + 1) + '</td>' +
                    '<td>' + reviews[i].user.tier + '</td>' +
                    '<td>' + reviews[i].user.tierExp + '</td>' +
                    '<td>' + reviews[i].user.username + '</td>' +
                    '<td>' + reviews[i].reviewText + '</td>' +
                    '<td id="likesCount_' + i + '">' + reviews[i].likesCount + '</td>' +
                    '<td><button onclick="likeReview(' + reviews[i].id + ',' + i + ')">좋아요</button></td>' +
                    '</tr>'
                );
            }
        },
        error: function () {
            alert('리뷰를 가져오는 중에 오류가 발생했습니다.');
        }
    });
}

// 좋아요 버튼 클릭 시 호출되는 함수
function likeReview(reviewId, index) {
    // 클라이언트 측에서만 likesCount 증가
    var currentLikesCount = parseInt($('#likesCount_' + index).text(), 10);
    $('#likesCount_' + index).text(currentLikesCount + 1);

    // 클라이언트 측에서만 exp 증가
    var currentExp = parseInt($('#ShowMoreReviewstable tbody tr:eq(' + index + ') td:eq(2)').text(), 10);
    $('#ShowMoreReviewstable tbody tr:eq(' + index + ') td:eq(2)').text(currentExp + 1);

    // 이후 서버로 해당 정보를 업데이트하는 api 호출을 추가해야 합니다.
    // 서버에서는 클라이언트에서 전송한 정보를 검증하고 실제로 DB에 반영해야 합니다.
}


$(document).ready(function () {
    $('#serveyBtn').click(function () {
        $('#satisfactionModal').modal('show');
    });


});

/* closeSatisfactionModal() <- 이 함수만 작동이 안돼서 html에 따로 빼주었음. 해결해주시면 감사합니다.

function closeSatisfactionModal() {
    console.log('Closing modal');
    $('#satisfactionModal').modal('hide');
}
*/


// 모달 닫힐 때 입력 내용 초기화
$('#satisfactionModal').on('hidden.bs.modal', function () {
    $("#satisfactionSurveyText").val('');
    $("#cleanlinessSatisfaction").val('');
    $("#facilitySatisfaction").val('');
    $("#congestionSatisfaction").val('');
    $("#feeSatisfaction").val('');
    $("#safetySatisfaction").val('');
    $("#signageSatisfaction").val('');
    $("#serviceSatisfaction").val('');
});

// Add this function to your existing JavaScript code
function submitSatisfactionSurvey() {
    var cleanlinessSatisfaction = parseInt($('#cleanlinessSatisfaction').val());
    var facilitySatisfaction = parseInt($('#facilitySatisfaction').val());
    var congestionSatisfaction = parseInt($('#congestionSatisfaction').val());
    var feeSatisfaction = parseInt($('#feeSatisfaction').val());
    var safetySatisfaction = parseInt($('#safetySatisfaction').val());
    var signageSatisfaction = parseInt($('#signageSatisfaction').val());
    var serviceSatisfaction = parseInt($('#serviceSatisfaction').val());

    if (currentParkingLotId !== null) {
        $.ajax({
            type: 'POST',
            url: '/api/parkinglot/' + currentParkingLotId + '/satisfaction',
            contentType: 'application/json',
            data: JSON.stringify({
                cleanlinessSatisfaction: cleanlinessSatisfaction,
                facilitySatisfaction: facilitySatisfaction,
                congestionSatisfaction: congestionSatisfaction,
                feeSatisfaction: feeSatisfaction,
                safetySatisfaction: safetySatisfaction,
                signageSatisfaction: signageSatisfaction,
                serviceSatisfaction: serviceSatisfaction
            }),
            success: function (response) {
                console.log(response);
                $('#satisfactionModal').modal('hide');

            },
            error: function (error) {
                console.error(error);
                alert('만족도 설문조사를 제출하는 중에 오류가 발생했습니다.');
            }
        });
    } else {
        alert('설문조사를 제출하기 전에 주차장을 선택하세요.');
    }
}




var barChart, radarChart;



$(document).ready(function () {
    $('#visualizationBtn').click(function () {
        $('#visualizationModal').modal('show');
        console.log('Submitting satisfaction survey for Parking Lot ID:', currentParkingLotId);

        if (currentParkingLotId !== null) {
            // Fetch satisfaction survey results for the current parking lot ID
            $.ajax({
                type: 'GET',
                url: '/api/parkinglot/' + currentParkingLotId + '/satisfaction',
                success: function (surveyResults) {
                    // Extract satisfaction values for each category
                    var cleanlinessData = surveyResults.map(result => result.cleanlinessSatisfaction);
                    var facilityData = surveyResults.map(result => result.facilitySatisfaction);
                    var congestionData = surveyResults.map(result => result.congestionSatisfaction);
                    var feeData = surveyResults.map(result => result.feeSatisfaction);
                    var safetyData = surveyResults.map(result => result.safetySatisfaction);
                    var signageData = surveyResults.map(result => result.signageSatisfaction);
                    var serviceData = surveyResults.map(result => result.serviceSatisfaction);


                    // Destroy existing charts if they exist
                    if (barChart) {
                        barChart.destroy();
                    }
                    if (radarChart) {
                        radarChart.destroy();
                    }

                    // Create a chart
                    radarChart = createRadarChart(cleanlinessData, facilityData, congestionData, feeData, safetyData, signageData, serviceData);
                    barChart = createBarChart(cleanlinessData, facilityData, congestionData, feeData, safetyData, signageData, serviceData);


                    },
                error: function () {
                    alert('만족도 설문조사 결과를 가져오는 중에 오류가 발생했습니다.');
                }
            });
        } else {
            alert('시각화분석을 보려는 주차장을 선택하세요.');
        }
    });
});

// Add a new function to create the chart
function createBarChart(cleanlinessData, facilityData, congestionData, feeData, safetyData, signageData, serviceData) {
    return new Chart(document.getElementById("bar-chart"), {
        type: 'bar',
        data: {
            labels: ["청결도", "시설", "혼잡도", "요금", "안전성", "안내표시", "직원서비스"],
            datasets: [
                {
                    label: "Average Satisfaction",
                    backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850", "#8e5ea2", "#3cba9f"],
                    data: [
                        calculateAverage(cleanlinessData),
                        calculateAverage(facilityData),
                        calculateAverage(congestionData),
                        calculateAverage(feeData),
                        calculateAverage(safetyData),
                        calculateAverage(signageData),
                        calculateAverage(serviceData),
                    ]
                }
            ]
        },
        options: {
            legend: { display: false },
            title: {
                display: true,
                text: '만족도 평균 크기 비교'
            }
        }
    });
}

function createRadarChart(cleanlinessData, facilityData, congestionData, feeData, safetyData, signageData, serviceData) {
    return new Chart(document.getElementById("radar-chart"), {
        type: 'radar',
        data: {
            labels:["청결도", "시설", "혼잡도", "요금", "안전성", "안내표시", "직원서비스"],
            datasets: [
                {
                    label: "평균",
                    fill: true,
                    backgroundColor: "rgba(255,99,132,0.2)",
                    borderColor: "rgba(255,99,132,1)",
                    pointBorderColor: "#fff",
                    pointBackgroundColor: "rgba(255,99,132,1)",
                    pointBorderColor: "#fff",
                    data:[
                        calculateAverage(cleanlinessData),
                        calculateAverage(facilityData),
                        calculateAverage(congestionData),
                        calculateAverage(feeData),
                        calculateAverage(safetyData),
                        calculateAverage(signageData),
                        calculateAverage(serviceData),
                    ]
                }
            ]
        },
        options: {
            title: {
                display: true,
                text: '만족도 평균 분포'
            }
        }
    });
}

// Add a new function to calculate the average of an array of numbers
function calculateAverage(numbers) {
    var sum = numbers.reduce((acc, val) => acc + val, 0);
    return sum / numbers.length;
}






// Assuming roadBtn and mapBtn are the IDs of your buttons
var roadBtn = document.getElementById('roadBtn');
var mapBtn = document.getElementById('mapBtn');
var asroadview = document.getElementById('roadview');
var asmapview = document.getElementById('map');

// Initially, show the map and hide the road view
asroadview.style.display = 'none';
asmapview.style.display = 'block';

// Add click event listener for the roadBtn
roadBtn.addEventListener('click', function () {
    // Show the road view and hide the map
    asroadview.style.display = 'block';
    asmapview.style.display = 'none';
});

// Add click event listener for the mapBtn
mapBtn.addEventListener('click', function () {
    // Show the map and hide the road view
    asroadview.style.display = 'none';
    asmapview.style.display = 'block';
});







var container = document.getElementById('container'),
    mapWrapper = document.getElementById('mapWrapper'),
    btnRoadview = document.getElementById('btnRoadview'),
    btnMap = document.getElementById('btnMap'),
    rvContainer = document.getElementById('roadview'),
    mapContainer = document.getElementById('map');

var placePosition = new kakao.maps.LatLng(37.28379, 127.0449);

var mapOption = {
    center: placePosition,
    level: 3
};
var map = new kakao.maps.Map(mapContainer, mapOption);
var roadview = new kakao.maps.Roadview(rvContainer);


roadview.setViewpoint({
    pan: 0,
    tilt: 0,
    zoom: 0
});




function toggleMap(active) {
    if (active) {
        container.className = "view_map";
    } else {
        container.className = "view_roadview";
    }
}

// Event listeners to toggle between map and road view
btnRoadview.addEventListener('click', function () {
    toggleMap(false);
});

btnMap.addEventListener('click', function () {
    toggleMap(true);
});



function ShowMoreReviews() {
    // Open the details modal
    $('#ShowMoreReviewsModal').modal('show');
}











function showFavoritesModal() {
    $('#favoritesModal').modal('show');

}





// 사용자의 즐겨찾기 주차장을 표시하는 함수
function showFavorites() {
    // 현재 로그인한 사용자의 ID를 얻어오는 로직이 필요할 수 있습니다.
    // 예: const userId = getCurrentUserId();

    // AJAX를 통해 서버에서 사용자의 즐겨찾기 목록을 가져옵니다.
    $.ajax({
        url: '/api/favorite', // 즐겨찾기 목록을 가져오는 엔드포인트
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            // 서버로부터 받아온 즐겨찾기 목록을 화면에 표시하는 로직을 작성합니다.
            // 여기서는 간단하게 콘솔에 출력하는 것으로 대체합니다.
            console.log('User Favorites:', data);

            // 실제로는 즐겨찾기 목록을 사용하여 화면에 표시하는 로직을 작성해야 합니다.
            // 예: displayFavoritesOnPage(data);
            displayFavoritesOnModal(data);
        },
        error: function (error) {
            console.error('Failed to fetch user favorites:', error);
        }
    });
}

/*
// 즐겨찾기 데이터를 모달 내부에 표시하는 함수
function displayFavoritesOnModal(data) {
    // 모달 내부의 body 엘리먼트를 선택합니다.
    var modalBody = $('#favoritesModal .modal-body');

    // 기존에 표시된 내용을 비웁니다.
    modalBody.empty();

    // 받아온 데이터를 이용하여 목록을 생성하고 모달에 추가합니다.
    if (data.length > 0) {
        var favoritesList = $('<ul class="list-group"></ul>');

        // 각 즐겨찾기 아이템을 목록에 추가합니다.
        data.forEach(function (favorite) {
            var listItem = $('<li class="list-group-item"></li>').text(favorite.parkinglot.id);
            favoritesList.append(listItem);
        });

        // 목록을 모달 내부에 추가합니다.
        modalBody.append(favoritesList);
    } else {
        // 만약 즐겨찾기가 없을 경우에 대한 처리를 추가할 수 있습니다.
        modalBody.text('즐겨찾기가 없습니다.');
    }
}
*/


// 즐겨찾기 데이터를 모달 내부에 표시하는 함수
function displayFavoritesOnModal(data) {
    // 모달 내부의 테이블 body 엘리먼트를 선택합니다.
    var tableBody = $('#favoritesTableBody');

    // 기존에 표시된 내용을 비웁니다.
    tableBody.empty();

    // 받아온 데이터를 이용하여 행을 생성하고 테이블에 추가합니다.
    if (data.length > 0) {
        data.forEach(function (favorite) {
            var row = $('<tr></tr>');
            //row.append('<td>' + favorite.parkinglot.id + '</td>');
            row.append('<td>' + favorite.parkinglot.parkingFacilityName + '</td>');
            row.append('<td>' + favorite.parkinglot.locationRoadNameAddress + '</td>');
            tableBody.append(row);
        });
    } else {
        // 만약 즐겨찾기가 없을 경우에 대한 처리를 추가할 수 있습니다.
        tableBody.html('<tr><td colspan="3">즐겨찾기가 없습니다.</td></tr>');
    }
}












// 즐겨찾기에 주차장을 추가하는 함수
function addToFavorites() {
    // 주차장을 즐겨찾기에 추가하기 위한 AJAX POST 요청 수행
    if (currentParkingLotId !== null) {
        $.ajax({
            type: 'POST',
            url: '/api/favorite/' + currentParkingLotId,  //
            data: { parkingLotId: currentParkingLotId },
            success: function (response) {
                alert('주차장이 즐겨찾기에 추가되었습니다!');
            },
            error: function () {
                alert('주차장을 즐겨찾기에 추가하지 못했습니다.');
            }
        });
    } else {
        alert('설문조사를 제출하기 전에 주차장을 선택하세요.');
    }
}




/*
// 버튼에 클릭 이벤트 리스너를 추가합니다
$(document).ready(function () {
    $('#addToFavoritesBtn').click(function () {
        addToFavorites(currentParkingLotId);

    });

    $('#showFavoritesBtn').click(function () {
        showFavorites();
    });
});
*/