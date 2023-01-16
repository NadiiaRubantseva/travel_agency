<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> <fmt:message key="profile"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/my.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<br>
<div class="col-lg-7 mx-auto p-2 py-md-2">

    <h2 class="text-muted"><fmt:message key="profile.info"/></h2>

    <main>
        <div class="container-fluid">

            <div class="row">
                <div class="col-sm-4">
                    <br><br>
                    <div class="image">
                        <c:choose>
                            <c:when test="${not empty sessionScope.loggedUser.avatar}">
                                <img src="${sessionScope.loggedUser.avatar}" class="rounded" width="155">
                            </c:when>
                            <c:otherwise>
                                <img src="img/default_user_photo.png" class="rounded" width="155">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <br>
                </div>

                <div class="col-sm-4">
                    <p>
                    <h5><fmt:message key="email"/>:</h5></p>
                    <p>${sessionScope.loggedUser.email}</p>

                    <h5><fmt:message key="name"/>:</h5>
                    <p>${sessionScope.loggedUser.name}</p>

                    <h5><fmt:message key="surname"/>:</h5>
                    <p>${sessionScope.loggedUser.surname}</p>
                </div>
            </div>
        </div>
    </main>

    <div id="button" class="form-group">
        <a href="editProfile.jsp" class="btn btn-success mt-0 mb-1"><fmt:message key="edit.profile"/></a>
    </div>

</div>
<br><br>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>