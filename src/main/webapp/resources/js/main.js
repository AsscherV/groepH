
$(document).ready(function(){
	$(".tabContentsTrips").hide(); // Hide all tab conten divs by default
	$(".tabContentsTrip").hide();
	$(".tabContentsTrips:first").show(); // Show the first div of tab content by default
	$(".tabContentsTrip:first").show();

	$("#tabContainerTrips ul li a").click(function(){ //Fire the click event

		var activeTab = $(this).attr("href"); // Catch the click link
		$("#tabContainerTrips ul li a").removeClass("active"); // Remove pre-highlighted link
		$(this).addClass("active"); // set clicked link to highlight state
		$(".tabContentsTrips").hide(); // hide currently visible tab content div
		$(activeTab).fadeIn(); // show the target tab content div by matching clicked link.

                       $(".button").fadeIn(); // show unvisible trip tabs
	});

    //datetimepicker
            $(".datepicker").datepicker({
                dateFormat: 'dd-mm-yy',
                changeMonth: true,
                changeYear: true,
                showOtherMonths: true,
                selectOtherMonths: true,
                yearRange: '-100y:c+nn',
                maxDate: '-1d'
            });
});