var map;
var mapOptions;
var markersArray = [];

$(function(){
    initMap();
    addListeners();
    setFilledInPosition();
});

function initMap(){
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

function addListeners(){
    google.maps.event.addListener(map, "click", function(event)
    {
        // place a marker
        placeMarker(event.latLng);

        // display the lat/lng in your form's lat/lng fields
        var lat  = event.latLng.lat();
        var lng =  event.latLng.lng();

        // NEED A FIX !!!! FOR SOME REASON ONLY THE FIRST LAT & LNG GETS THE VALUE !!!
        document.getElementById("waypointForm:newlat").value = lat;
        document.getElementById("waypointForm:newlng").value = lng  ;
        document.getElementById("waypointForm:lat").value = lat ;
        document.getElementById("waypointForm:lng").value = lng ;

    });
}

function placeMarker(location) {
    // first remove all markers if there are any
    deleteOverlays();

    var marker = new google.maps.Marker({
        position: location,
        map: map
    });

    // add marker in markers array
    markersArray.push(marker);

    //map.setCenter(location);
}

// Deletes all markers in the array by removing references to them
function deleteOverlays() {
    if (markersArray) {
        for (i in markersArray) {
            markersArray[i].setMap(null);
        }
        markersArray.length = 0;
    }
}

function setFilledInPosition(){
    if(document.getElementById("waypointForm:newlat").value != '0.0'){
        var marker = new google.maps.Marker({
            position: new google.maps.LatLng(parseFloat(document.getElementById("waypointForm:newlat").value),parseFloat(document.getElementById("waypointForm:newlng").value)),
            map: map
        });

        markersArray.push(marker);
    }

}




