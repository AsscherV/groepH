$(document).ready(function () {

    var url = window.location.hash.split('=');
    if (url[1]) {    // IF THERE IS A TOKEN
        var url = url[1].split('&');
        var token = url[0];
        // JQUERY DOESNT WORK BECAUSE OF JSF RENAME ID =  Form:FieldName
        document.getElementById("loginForm:token").value = token;
        document.getElementById("loginForm:fbLoginButton").click();

    }
});