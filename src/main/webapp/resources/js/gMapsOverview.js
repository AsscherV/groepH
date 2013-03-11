var map;
var mapOptions;
var loc;

$(function(){
    initMap();
    fetchPositions();
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

function fetchPositions(){
    var bounds = new google.maps.LatLngBounds ();
    //var str = $('input[type=hidden]').val();
    var str = document.getElementById("j_idt94:j_idt95:0:testID").value;
    var positionsArray = str.split(" ");

    for(var i = 0; i < positionsArray.length; i++){
        loc = new google.maps.LatLng(positionsArray[i++],positionsArray[i]);
        bounds.extend(loc);
        placeMarker(loc);
    }
    map.fitBounds(bounds);
}

function placeMarker(loc) {
    var marker = new google.maps.Marker({
        position: loc,
        map: map
    });
    //map.setCenter(loc);
}
