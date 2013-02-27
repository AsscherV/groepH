$(document).ready(function () {
    // TRIPS / MYTRIPS MENU
    $(".tabContentsTrips").hide(); // Hide all tab content divs by default
    if($("#loggedIn").val() =="true" ){
        $("#tabContainerTrips ul li a").removeClass("active");
        $("#myTrips").addClass("active"); // set clicked link to highlight state
        $(".tabContentsTrips").eq(1).show(); // Show the second div of tab content by default
    }else{
        $("#tabContainerTrips ul li a").removeClass("active"); // Remove pre-highlighted link
        $("#trips").addClass("active"); // set clicked link to highlight state
        $(".tabContentsTrips").eq(0).show(); // Show the first div of tab content by default
    }
    // TRIPS / MYTRIPS CLICK EVENT
    $("#tabContainerTrips ul li a").click(function () { //Fire the click event
        var activeTab = $(this).attr("href"); // Catch the click link
        $("#tabContainerTrips ul li a").removeClass("active"); // Remove pre-highlighted link
        $(this).addClass("active"); // set clicked link to highlight state
        $(".tabContentsTrips").hide(); // hide currently visible tab content div
        $(activeTab).fadeIn(); // show the target tab content div by matching clicked link.
    });

    //DATETIMEPICKER
  /* $(".datepicker").datepicker({
        dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true,showOtherMonths: true,selectOtherMonths: true,
        yearRange: '-100y:c+nn', maxDate: '-1d'
    });
    //TRIP STARTDATE DATETIMEPICKER
    $(".tripStartDatepicker").datepicker({
        dateFormat: 'dd/mm/yy',changeMonth: true,changeYear: true,showOtherMonths: true,selectOtherMonths: true,
        onClose: function (selectedDate) {
            $(".tripEndDatepicker").datepicker("option", "minDate", selectedDate);
        }
    });
    //TRIP ENDDATE DATETIMEPICKER
    $(".tripEndDatepicker").datepicker({
        dateFormat: 'dd/mm/yy',changeMonth: true,changeYear: true,showOtherMonths: true,selectOtherMonths: true,
        onClose: function (selectedDate) {
            $(".tripStartDatepicker").datepicker("option", "maxDate", selectedDate);
        }
    });
    */
});