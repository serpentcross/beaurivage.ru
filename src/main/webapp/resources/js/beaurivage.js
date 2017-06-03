$(document).ready(function() {

    var rootURL = document.URL;

    loadHistory(rootURL);

    function loadHistory(url) {
        // $("#searchedvalues").empty();
        $.ajax({
            dataType: "JSON",
            contentType: 'application/json',
            type: "GET",
            url: url + "/welcome/records"
        }).then(function(response) {
            if ($.isEmptyObject(response)) {
                UIkit.notify({message: 'История отправки пустая!', status: 'info', timeout: 5000, pos: 'top-center'});
            } else {
                renderDetails(response);
            }
        });
    }


    $("#createRecord").click(function() {

        var customer = {};

        customer.firstName = $("#customerName").val();
        customer.middleName = $("#customerMiddlename").val();
        customer.lastName = $("#customerSurname").val();
        customer.phone = $("#customerPhone").val();

        var recordModel = {};

        recordModel.customer = customer;
        recordModel.day = $("#dayOfWeek").val();
        recordModel.time_from = $("#timeFr").val();
        recordModel.time_to = $("#timeTo").val();

        var record = JSON.stringify(recordModel);
        $.ajax({
            dataType: "json",
            contentType: 'application/json',
            type: "post",
            url: rootURL + "/welcome/makerecord",
            data: record
        }).then(function(response) {
                UIkit.notify({message: response.message, status: 'info', timeout: 5000, pos: 'top-center'});
                location.reload();
            }
        );
    });

    function renderDetails(response) {
        var globalCycleCounter = 0;
        var rowCounter = 0;

        var id_record = "";
        var time = "";
        var phone_Cell = "";
        var date_Cell = "";
        var status_Cell = "";
        var message_Cell = "";

        var isNeedResend = false;

        for (var property in response) {

            id_record = response[property]['id'];

            time = response[property]['time_from'] + " - " + response[property]['time_to'];

            singleRow = "<div id='record" + id_record + "' class='uk-panel-time'>" + time + "</div>";

            $("#" + response[property]['day']).append(singleRow);
        }
    }

    $("#maintable").delegate("[id^=record]", "click", function() {
        $("#popupWindow").css("display","block");
    });

});