$(document).ready(function () {
//DATETIMEPICKER
    $(".datepicker").datepicker({
        dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true,showOtherMonths: true,selectOtherMonths: true,
        yearRange: '-100y:c+nn', maxDate: '-1d'
    });
//TRIP STARTDATE DATETIMEPICKER
    $(".tripStartDatepicker").datetimepicker({
        dateFormat: 'dd/mm/yy',changeMonth: true,changeYear: true,showOtherMonths: true,selectOtherMonths: true,
        onClose: function (selectedDate) {
            $(".tripEndDatepicker").datetimepicker("option", "minDate", selectedDate).val(selectedDate);
        }
    });
//TRIP ENDDATE DATETIMEPICKER
    $(".tripEndDatepicker").datetimepicker({
        dateFormat: 'dd/mm/yy',changeMonth: true,changeYear: true,showOtherMonths: true,selectOtherMonths: true,
        onClose: function (selectedDate) {
            $(".tripStartDatepicker").datetimepicker("option", "maxDate", selectedDate);
        }
    });
});