var map;
var mapOptions;
var loc;

$(function(){
    initMap();
    fetchPositions();
});

function initMap(){
    mapOptions = {
        center: new google.maps.LatLng(50.892639,4.367065),
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

function fetchPositions(){
    var str = document.getElementById("j_idt94:j_idt95:1:testID").value;
    var positionsArray = str.split(" ");

    for(var i = 0; i < positionsArray.length; i++){
        loc = new google.maps.LatLng(positionsArray[i++],positionsArray[i]);
        placeMarker(loc);
    }
}

function placeMarker(loc) {
    var marker = new google.maps.Marker({
        position: loc,
        map: map
    });
    //map.setCenter(loc);
}
