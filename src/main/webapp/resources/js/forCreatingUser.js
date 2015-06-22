/**
 * Created by student on 4/25/2014.
 */


function f() {
    if ($("#groupSelect").val() == '3') {
        $("#checkboxes").show();
        $("#trTitle").show();
    } else {
        $("#checkboxes").hide();
        $("#trTitle").hide();
    }
}

function passVisible() {
    var p = $('#password');
    if(p.prop('type') == 'password') {
        p.prop('type', 'text');
    } else {
        p.prop('type', 'password');
    }
}