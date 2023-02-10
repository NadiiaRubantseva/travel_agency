window.onload = function () {
    var currentPage = window.location.href;
    var navLinks = document.querySelectorAll('.navbar-nav .nav-item a');

    for (var i = 0; i < navLinks.length; i++) {
        var link = navLinks[i];
        var href = link.getAttribute("href");
        var action = getUrlParameter("action", href);
        if (currentPage.endsWith(href) || (href.startsWith("controller") && getUrlParameter("action", currentPage) === action)) {
            link.classList.add("active");
            break;
        }
    }
}

function getUrlParameter(name, url) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(url);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}