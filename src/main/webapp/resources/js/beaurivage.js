$(document).ready(function() {

    var rootURL = document.URL;

    $("#createRecord").click(function() {

        var customerName = $("#customerName").val();
        var customerSurname = $("#customerSurname").val();
        var customerMiddlename = $("#customerMiddlename").val();
        var customerPhone = $("#customerPhone").val();

        var dayOfWeek = $("#dayOfWeek").val();
        var timeFr = $("#timeFr").val();
        var timeTo = $("#timeTo").val();

        var customer = new Object();

        customer.first_name = customerName;
        customer.middle_name = customerMiddlename;
        customer.last_name = customerSurname;
        customer.phone = customerPhone;

        var record = new Object();

        record.customer = customer;
        record.day = dayOfWeek;
        record.time_from = timeFr;
        record.time_to = timeTo;


        var obj = JSON.stringify(record);
        alert(rootURL + "makerecord");
        $.ajax({
            dataType: "JSON",
            contentType: 'application/json',
            type: "POST",
            url: rootURL + "/welcome/makerecord",
            data: obj}).then(function(success) {
                alert('Ваша подписка на рассылку активирована! '+ success);
                window.location.href = 'customerzone';
            }
        );
    });
});