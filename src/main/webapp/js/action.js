$(document).ready(function() {
    var trigger = false;
    var websiteWarning = $('#test').find('.form-control-feedback-url');
    var emailWarning = $('#test').find('.form-control-feedback-email');
    function checkAndSend(){
        trigger = true;
        if (formValidation(trigger, websiteWarning, emailWarning)) {
            sendRequest($('#site-url').val(), $('#email-input').val());
        }
    }
    $('#test').find('button').on('click', function(){
        trigger = true;
        checkAndSend();
    });
    $('#site-url, #email-input').on('keyup',function(){
        if (trigger){
            formValidation(trigger, websiteWarning, emailWarning);
        }
        if (event.keyCode == 13) {
            checkAndSend();
        }
    })
});
function formValidation(trigger, websiteWarning, emailWarning) {
    var website = $('#site-url').val();
    var email = $('#email-input').val();
    var ifEmpty = 'Required field cannot be left blank';
    var invalidEmail = 'Please enter a valid email';

    if(validateEmail(email)) {
        emailWarning.addClass('hidden');
    } else if (!checkIfNotEmpty(email)) {
        emailWarning.text(ifEmpty).removeClass('hidden');
    } else if (checkIfNotEmpty(email) && !validateEmail(email)) {
        emailWarning.text(invalidEmail).removeClass('hidden');
    }
    if(checkIfNotEmpty(website)) {
        websiteWarning.addClass('hidden');
    } else if (!checkIfNotEmpty(website)) {
        websiteWarning.text(ifEmpty).removeClass('hidden');
    }

    if (validateEmail(email) && checkIfNotEmpty(website)) {
        return true;
    }
}
function checkIfNotEmpty(site) {
    if (site.length != 0) return true;
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
        }
    });
}
$(function showProgressBar() {
    $(".progress div").each(function () {
        var display = $(this),
            currentValue = parseInt(display.text()),
            nextValue = $(this).attr("data-values"),
            diff = nextValue - currentValue,
            step = (0 < diff ? 1 : -1);
        if (nextValue == "0") {
            $(display).css("padding", "0");
        } else {
            $(display).css("color", "transparent").animate({
                "width": nextValue + "%"
            }, "slow");
        }

        for (var i = 0; i < Math.abs(diff); ++i) {
            setTimeout(function () {
                currentValue += step;
                display.html(currentValue + "%");
            }, 20 * i);
        }
    });
});
$(function() {
    $.fn.scrollToTop = function() {
        $(this).click(function() {
            $("html, body").animate({scrollTop: 0}, "slow");
            $("#site-url").focus();
        })
    }
});

$(function() {
    $("#Go_Top").scrollToTop();
});