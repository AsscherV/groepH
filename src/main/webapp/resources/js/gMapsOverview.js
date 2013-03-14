var map;
var mapOptions;
var loc;
var bounds;
var markers = new Array();
var infowindows = new Array();

$(function(){
    initMap();
    fetchPositions();
    createInfowindows();
    addListeners();
});

function initMap(){
    mapOptions = {
        center: new google.maps.LatLng(0,0),
        zoom: 3,
        scrollwheel: true,
        navigationControl: true,
        scaleControl: true,
        draggable: true,
        streetViewControl: true,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("map_canvas"),
        mapOptions);
}

function addListeners(){
    $('#resetMap').click( function(){
        map.fitBounds(bounds);
    });

    for(var i = 0; i < markers.length; i++){
        google.maps.event.addListener(markers[i], 'click', function() {
            //if(infowindows[0].getMap()){
            //infowindows[0].close();
            //}else{
            infowindows[i].open(map,markers[i]);
            //}
        });
    }
}

function fetchPositions(){
    bounds = new google.maps.LatLngBounds ();
    //var str = $('input[type=hidden]').val();
    var str = document.getElementById("waypointsForm:wayPointsTable:0:positions").value;
    var str2= document.getElementById("waypointsForm:wayPointsTable:0:titles").value;
    var positionsArray = str.split(" ");
    var titlesArray = str2.split(",");

    for(var i = 0; i < positionsArray.length; i++){
        loc = new google.maps.LatLng(positionsArray[i++],positionsArray[i]);
        bounds.extend(loc);
        placeMarker(loc);
    }

    for(var j = 0; j < titlesArray.length; j++){
        createInfowindows(titlesArray[j]);
    }

    map.fitBounds(bounds);
}

function placeMarker(loc) {
    var marker = new google.maps.Marker({
        position: loc,
        map: map
    });
    markers.push(marker);
    //map.setCenter(loc);
}

function createInfowindows(text){
        var infow = new google.maps.InfoWindow(
        {
            size: new google.maps.Size(50,50),
            content: text
        });
    infowindows.push(infow);
}
