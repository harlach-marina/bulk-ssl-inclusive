$(document).ready(function() {
    //triggers for buttons
    var trigger = false;
    var trigger2 = false;
    //hidden error validate notifications
    var websiteWarning = $('#test').find('.form-control-feedback-url');
    var emailWarning = $('#test').find('.form-control-feedback-email');
    var email2Warning = $('#testModal').find('.form-control-feedback-email');
    var ifEmpty = 'Required field cannot be left blank';
    var invalidEmail = 'Please enter a valid email';
    //params to view in modal
    var website;
    var email;
    //validate site and email
    function checkAndSend(){
        if (formValidation(websiteWarning, emailWarning)) {
            website = formValidation(websiteWarning, emailWarning, ifEmpty, invalidEmail).website;
            email = formValidation(websiteWarning, emailWarning, ifEmpty, invalidEmail).email;
            $('#testModal').modal('show');
            if (email) {
                $('#email2-input').val(email).attr({'disabled': true});
                $('#checkbox-input').find('input').attr('checked', true);
                $('#checkbox-input').removeClass('hidden');
            }
        }
    }
    //check required email and send it
    function send() {
        if(checkEmailForReport(email2Warning, ifEmpty, invalidEmail)){
            sendRequest(website, email, $('#email2-input').val());
        }
    }

    //action on close modal window
    $('#testModal').on('hide.bs.modal', function () {
        emptyModalWindow($(this));
        emptyTestForm();
    });
    //bind checkbox and email field
    $('#checkbox-input').find('input').on('change', function () {
        if(!$(this).is(':checked')){
            $('#email2-input').attr('disabled', false);
        } else {
            $('#email2-input').attr('disabled', true);
        }
    });
    //actions on click test button
    $('#testModal').find('.modal-body button').on('click', function(){
        trigger2 = true;
        send();
    });
    $('#test').find('button').on('click', function(){
        trigger = true;
        checkAndSend();
    });
    //actions on key press
    $('#site-url, #email-input').on('keyup',function(){
        //on change input if test button was clicked
        if (trigger){
            formValidation(websiteWarning, emailWarning, ifEmpty, invalidEmail);
        }
        //on press enter
        if (event.keyCode == 13) {
            checkAndSend();
        }
    });
    $('#email2-input').on('keyup',function(){
        //on change input if test button was clicked
        if (trigger2){
            checkEmailForReport(email2Warning, ifEmpty, invalidEmail);
        }
        //on press enter
        if (event.keyCode == 13) {
            send();
        }
    })
});
function emptyModalWindow(window) {
    emptyField(window.find('#email2-input'));
    window.find('#email2-input').attr({'disabled': false});
    window.find('.form-control-feedback-email').addClass('hidden');
    window.find('#checkbox-input').attr('checked', false).addClass('hidden');
}
function emptyTestForm() {
    emptyField($('#site-url'));
    emptyField($('#email-input'));
}
//check email for report input
function checkEmailForReport(emailWarning, ifEmpty, invalidEmail) {
    var email = $('#email2-input').val();
    if(validateEmail(email)) {
        emailWarning.addClass('hidden');
        return true;
    } else if (!checkIfNotEmpty(email)) {
        emailWarning.text(ifEmpty).removeClass('hidden');
    } else if (checkIfNotEmpty(email) && !validateEmail(email)) {
        emailWarning.text(invalidEmail).removeClass('hidden');
    }
}
//validate url and email for check fields
function formValidation(websiteWarning, emailWarning, ifEmpty, invalidEmail) {
    var website = $('#site-url').val();
    var email = $('#email-input').val();
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
    } else return false;
}
//check if field not empty
function checkIfNotEmpty(site) {
    if (site.length !== 0) {
        return true;
    }
}
//validate email field
function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}
//empty field
function emptyField(input) {
    $(input).val('');
}
//ajax request
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
            $('#testModal').modal('hide');
        },
        error: function () {
            $('#errorModal').modal('show');
            $('#testModal').modal('hide');
        }
    });
}
//progress bar fill up
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