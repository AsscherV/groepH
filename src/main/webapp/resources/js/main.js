$(document).ready(function () {
    // TRIPS / MYTRIPS MENU
    $(".tabContentsTrips").hide(); // Hide all tab content divs by default
    if($("#loggedIn").val() =="true" ){
        $("#tabContainerTrips ul li a").removeClass("ui-state-active");
        $("#myTrips").addClass("active"); // set clicked link to highlight state
        $(".tabContentsTrips").eq(1).show(); // Show the second div of tab content by default
    }else{
        $("#tabContainerTrips ul li a").removeClass("ui-state-active"); // Remove pre-highlighted link
        $("#trips").addClass("active"); // set clicked link to highlight state
        $(".tabContentsTrips").eq(0).show(); // Show the first div of tab content by default
    }
    // TRIPS / MYTRIPS CLICK EVENT
    $("#tabContainerTrips ul li a").click(function () { //Fire the click event
        var activeTab = $(this).attr("href"); // Catch the click link
        $("#tabContainerTrips ul li a").removeClass("ui-state-active"); // Remove pre-highlighted link
        $(this).addClass("ui-state-active"); // set clicked link to highlight state
        $(".tabContentsTrips").hide(); // hide currently visible tab content div
        $(activeTab).show(); // show the target tab content div by matching clicked link.
    });
    jsf.ajax.addOnEvent(function(data) {
        // TRIPS / MYTRIPS MENU
        $(".tabContentsTrips").hide(); // Hide all tab content divs by default
        if($("#loggedIn").val()){
            $("#tabContainerTrips ul li a").removeClass("ui-state-active");
            $("#myTrips").addClass("ui-state-active"); // set clicked link to highlight state
            $(".tabContentsTrips").eq(1).show(); // Show the second div of tab content by default
        }else{
            $("#tabContainerTrips ul li a").removeClass("ui-state-active"); // Remove pre-highlighted link
            $("#trips").addClass("ui-state-active"); // set clicked link to highlight state
            $(".tabContentsTrips").eq(0).show(); // Show the first div of tab content by default
        }
        // TRIPS / MYTRIPS CLICK EVENT
        $("#tabContainerTrips ul li a").click(function () { //Fire the click event
            var activeTab = $(this).attr("href"); // Catch the click link
            $("#tabContainerTrips ul li a").removeClass("ui-state-active"); // Remove pre-highlighted link
            $(this).addClass("ui-state-active"); // set clicked link to highlight state
            $(".tabContentsTrips").hide(); // hide currently visible tab content div
            $(activeTab).fadeIn(); // show the target tab content div by matching clicked link.
        });
        var txt =  $("#tripMenu input.span2");
        txt.value = txt;
        txt.focus();


    }) ;


});