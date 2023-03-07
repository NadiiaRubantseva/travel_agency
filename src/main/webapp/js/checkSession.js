function checkSession() {
    var userId = "${sessionScope.loggedUser.id}";

    if (userId) {
        $('#book').modal('show');
        return false;
    } else {
        $('#loginModal').modal('show');
        return false;
    }
}