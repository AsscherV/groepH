var map;
var mapOptions;

$(function(){
    showMap();
});

function showMap(){
    mapOptions = {
        center: new google.maps.LatLng(50.892639,4.367065),
        zoom: 4,
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




