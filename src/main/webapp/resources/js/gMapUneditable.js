var map;
var mapOptions;
var loc;




$(function(){
    loc = new google.maps.LatLng(parseFloat(document.getElementById("lat").innerHTML),parseFloat(document.getElementById("lng").innerHTML));
    initMap();
    placeMarker(loc);
    addListeners();
});

function initMap(){

    mapOptions = {
        zoom: 12,
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
        map.setCenter(loc);
    });
}

function placeMarker(loc) {

    var marker = new google.maps.Marker({
        position: loc,
        map: map
    });

    map.setCenter(loc);
}







