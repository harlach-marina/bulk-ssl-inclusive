$(document).ready(function() {
    var website = '';
    $('#test button').on('click', function(){
        website = $('#site-url').val();
        email = $('#email-input').val();
        if (checkIfNotEmpty(website) && validateEmail(email)) {
            $('#test .form-control-feedback-url').addClass('hidden');
            $('#test .form-control-feedback-email').addClass('hidden');
            sendRequest(website, email);
        } else if (!checkIfNotEmpty(website) && validateEmail(email)) {
            $('#test .form-control-feedback-url').removeClass('hidden');
            $('#test .form-control-feedback-email').addClass('hidden');
        } else if (checkIfNotEmpty(website) && !validateEmail(email)) {
            $('#test .form-control-feedback-email').removeClass('hidden');
            $('#test .form-control-feedback-url').addClass('hidden');
        } else {
            $('#test .form-control-feedback-email').removeClass('hidden');
            $('#test .form-control-feedback-url').removeClass('hidden');
        }
    });
});
function checkIfNotEmpty(site) {
    if (site.length != 0) return true;
    else return false;
}
function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}
function emptyField(input) {
    $(input).val('');
}
function sendRequest(site, email) {
    var url = '/rest/report';
    $.ajax({
        url: url,
        method: "GET",
        data: {
            email: encodeURIComponent(email),
            url: encodeURIComponent(site)
        },
        success: function(data){
            console.log("success!");
            $('#thxModal').modal('show');
            emptyField('#site-url');
            emptyField('#email-input');
        },
        error: function () {
            console.log("error!");
        },
    });
}