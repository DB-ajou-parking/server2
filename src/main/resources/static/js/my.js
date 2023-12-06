var currentParkingLotId = null;


var mapContainer = document.getElementById('map');
var mapOption = {
    center: new kakao.maps.LatLng(37.28444, 127.0444),
    level: 3,
    mapTypeId: kakao.maps.MapTypeId.ROADMAP
};
var map = new kakao.maps.Map(mapContainer, mapOption);

map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
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

var reviews = [];
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
                    '<td><button onclick="likeReview(' + i + ')">좋아요</button></td>' +
                    '</tr>'
                );
            }
        },
        error: function () {
            alert('리뷰를 가져오는 중에 오류가 발생했습니다.');
        }
    });
}



function likeReview(index) {
    // 현재 likesCount를 가져옴
    var likesCountElement = $('#likesCount_' + index);
    var currentLikesCount = parseInt(likesCountElement.text());

    // 현재 상태에 따라 likesCount를 증가 또는 감소시킴
    var newLikesCount = currentLikesCount === 1 ? 0 : 1;

    // UI를 새로운 좋아요 횟수로 업데이트
    updateLikesCountUI(index, newLikesCount);

    // Update the server using an AJAX call
    updateServerWithLikesCount(index, newLikesCount);
}

// UI를 새로운 좋아요 횟수로 업데이트하는 함수
function updateLikesCountUI(index, newLikesCount) {
    // 리뷰마다 좋아요 횟수를 표시하는 UI를 가정
    $('#ShowMoreReviewstable tbody tr:eq(' + index + ') td:eq(5)').text(newLikesCount);
}

// 서버를 새로운 likesCount로 업데이트하는 함수
function updateServerWithLikesCount(index, newLikesCount) {
    // 리뷰 ID 또는 업데이트에 필요한 다른 식별자를 가져옴
    var reviewId = getReviewIdAtIndex(index);

    // 서버를 업데이트하기 위해 AJAX 호출 수행
    $.ajax({
        type: 'POST',
        url: '/api/likes/' + reviewId,  // 서버의 API에 따라 URL을 업데이트
        contentType: 'application/json',
        data: JSON.stringify({
            // API가 newLikesCount 값을 필요로 하는 경우
            likesCount: newLikesCount
        }),
        success: function (response) {
            console.log('서버가 성공적으로 업데이트되었습니다:', response);
        },
        error: function () {
            console.error('서버 업데이트 중 오류가 발생했습니다.');
            // 업데이트가 실패한 경우 UI를 이전 상태로 되돌리는 등의 조치를 취할 수 있음
        }
    });
}

// 인덱스를 기반으로 리뷰 ID를 검색하는 함수
function getReviewIdAtIndex(index) {            //여기서 reviews를 못가져와서 서버에 저장을 못함.
    // 인덱스를 기반으로 리뷰 ID를 검색하는 로직을 구현
    // 예를 들어, 리뷰에 고유한 ID가 있다면 리뷰 배열을 사용할 수 있음
    // var reviewId = reviews[index].id;
    // 데이터 구조에 따라 이 로직을 조절

    // 리뷰가 서버에서 가져오고 각 리뷰에 ID 속성이 있는 경우를 가정
    return reviews[index].id;
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




