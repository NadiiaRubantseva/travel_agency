function checkSession() {
    var userId = document.getElementById("userId").value;

    if (userId) {
        $('#book').modal('show');
        return false;
    } else {
        $('#loginModal').modal('show');
        return false;
    }
}