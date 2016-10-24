$(document).ready(function() {
    var trigger = false;
    var trigger2 = false;
    var websiteWarning = $('#test').find('.form-control-feedback-url');
    var emailWarning = $('#test').find('.form-control-feedback-email');
    var email2Warning = $('#testModal').find('.form-control-feedback-email');
    var website;
    var email;
    function checkAndSend(){
        if (formValidation(websiteWarning, emailWarning)) {
            website = formValidation(websiteWarning, emailWarning).website;
            email = formValidation(websiteWarning, emailWarning).email;
            $('#testModal').modal('show');
            if (email) {
                $('#email2-input').val(email).attr({'disabled': true, 'checked': true});
                $('#checkbox-input').find('input').attr('checked', true);
                $('#checkbox-input').removeClass('hidden');
            }

        }
    }
    function send() {
        if(checkEmailForReport(email2Warning, $('#email2-input').val())){
            sendRequest(website, email, $('#email2-input').val());
        }
    }
    $('#testModal').on('hide.bs.modal', function () {
        emptyField('#site-url');
        emptyField('#email-input');
        emptyField('#email2-input');
        $('#checkbox-input').attr('checked', true).addClass('hidden');
    });
    $('#checkbox-input').find('input').on('change', function () {
        if(!$(this).is(':checked')){
            $('#email2-input').attr('disabled', false);
        } else {
            $('#email2-input').attr('disabled', true);
        }
    });
    $('#testModal').find('button').on('click', function(){
        trigger2 = true;
        send();
    });
    $('#test').find('button').on('click', function(){
        trigger = true;
        checkAndSend();
    });
    $('#site-url, #email-input').on('keyup',function(){
        if (trigger){
            formValidation(websiteWarning, emailWarning);
        }
        if (event.keyCode == 13) {
            checkAndSend();
        }
    })
});
function checkEmailForReport(emailWarning, email) {
    var ifEmpty = 'Required field cannot be left blank';
    var invalidEmail = 'Please enter a valid email';

    if(validateEmail(email)) {
        emailWarning.addClass('hidden');
        return true;
    } else if (!checkIfNotEmpty(email)) {
        emailWarning.text(ifEmpty).removeClass('hidden');
    } else if (checkIfNotEmpty(email) && !validateEmail(email)) {
        emailWarning.text(invalidEmail).removeClass('hidden');
    }
}
function formValidation(websiteWarning, emailWarning) {
    var website = $('#site-url').val();
    var email = $('#email-input').val();
    var ifEmpty = 'Required field cannot be left blank';
    var invalidEmail = 'Please enter a valid email';

    // if(validateEmail(email)) {
    //     emailWarning.addClass('hidden');
    // } else if (!checkIfNotEmpty(email)) {
    //     emailWarning.text(ifEmpty).removeClass('hidden');
    // } else if (checkIfNotEmpty(email) && !validateEmail(email)) {
    //     emailWarning.text(invalidEmail).removeClass('hidden');
    // }
    if (validateEmail(email)) {
        emailWarning.addClass('hidden');
    } else if (checkIfNotEmpty(email) && !validateEmail(email)) {
        emailWarning.text(invalidEmail).removeClass('hidden');
    }
    if(checkIfNotEmpty(website)) {
        websiteWarning.addClass('hidden');
    } else if (!checkIfNotEmpty(website)) {
        websiteWarning.text(ifEmpty).removeClass('hidden');
    }

    if ((validateEmail(email) || !checkIfNotEmpty(email)) && checkIfNotEmpty(website)) {
        return {
            email: email,
            website: website
        };
    }
}
function checkIfNotEmpty(site) {
    if (site.length !== 0) {
        return true;
    }
}
function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}
function emptyField(input) {
    $(input).val('');
    if ($(input).attr('disabled')) {
        $(input).attr('disabled', false);
    }
}
function sendRequest(site, emailForCheck, emailForReport) {
    var url = '/rest/report';
    var dataObj;
    if (emailForCheck != '') {
        dataObj = {
            emailForReport: encodeURIComponent(emailForReport),
            emailForCheck: encodeURIComponent(emailForCheck),
            url: encodeURIComponent(site)
        }
    } else {
        dataObj = {
            emailForReport: encodeURIComponent(emailForReport),
            url: encodeURIComponent(site)
        }
    }
    $.ajax({
        url: url,
        method: "GET",
        data: dataObj,
        success: function(){
            $('#thxModal').modal('show');
            emptyField('#site-url');
            emptyField('#email-input');
        },
        error: function () {
            $('#errorModal').modal('show');
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
        if (nextValue === "0") {
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
// var abc = "80%";
//
// $(window).scroll(function() {
//     var scrollTop = $(window).scrollTop();
//     var element = $("#pbar-reputation").find('.progress-bar');
//     if ( scrollTop > (element.offset().top - element.offset().top/2)) {
//         $(function (element) {
//             console.log(element.attr('data-values'));
//             $(".progress-bar").animate({
//                 width: abc
//             }, 2500);
//         })
//
//     }
// });

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