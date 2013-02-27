$(document).ready(function () {

    var url = window.location.hash.split('=');
    var url = url[1].split('&');
    var token = url[0];
    if (token != "") {
        document.getElementById("loginForm:token").value =token;
        alert(document.getElementById("loginForm:token").value);
        document.getElementById("loginForm:fbLoginButton").click();
    }

});