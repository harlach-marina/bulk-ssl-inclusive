$(document).ready(function() {
    var trigger = false;
    $('#test button').on('click', function(){
        trigger = true;
        if (formValidation()) {
            sendRequest($('#site-url').val(), $('#email-input').val());
        }
    });
    $('#site-url, #email-input').on('keyup',function(){
        if (trigger){
            formValidation();
        }
    })
});
function formValidation() {
    var website = $('#site-url').val();
    var email = $('#email-input').val();
    var websiteWarning = $('#test .form-control-feedback-url');
    var emailWarning = $('#test .form-control-feedback-email');

    if (checkIfNotEmpty(website) && validateEmail(email)) {
        websiteWarning.addClass('hidden');
        emailWarning.addClass('hidden');
        return true;
    } else if (!checkIfNotEmpty(website) && validateEmail(email)) {
            websiteWarning.removeClass('hidden');
            emailWarning.addClass('hidden');
            return false;
    } else if (checkIfNotEmpty(website) && !validateEmail(email)) {
        websiteWarning.addClass('hidden');
        emailWarning.removeClass('hidden');
        return false;
    } else {
        websiteWarning.removeClass('hidden');
        emailWarning.removeClass('hidden');
        return false;
    }

}
function checkIfNotEmpty(site) {
    var re = new RegExp("^(http|https)://", "i");
    if (site.length != 0 && re.test(site)) return true;
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
$(function() {
    $.fn.scrollToTop = function() {
        $(this).hide().removeAttr("href");
        if ($(window).scrollTop() >= "250") $(this).fadeIn("slow")
        var scrollDiv = $(this);
        $(window).scroll(function() {
            if ($(window).scrollTop() <= "250") $(scrollDiv).fadeOut("slow")
            else $(scrollDiv).fadeIn("slow")
        });
        $(this).click(function() {
            $("html, body").animate({scrollTop: 0}, "slow")
        })
    }
});

$(function() {
    $("#Go_Top").scrollToTop();
});