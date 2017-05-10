$(document).ready(function() {

    var rootURL = document.URL;

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
        }).then(
            function(response) {
                UIkit.notify({message: response.message, status: 'info', timeout: 5000, pos: 'top-center'});
            }
        );
    });
});