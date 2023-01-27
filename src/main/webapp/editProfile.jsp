<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> <fmt:message key="edit.profile"/></title>
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

<div class="col-lg-12 mx-auto p-4 py-md-5">

        <c:set var="error" value="${requestScope.error}"/>
        <c:set var="titleValue" value="${requestScope.user.email eq null ?
                                sessionScope.loggedUser.email : requestScope.user.email}"/>
        <c:set var="nameValue" value="${requestScope.user.name eq null ?
                                sessionScope.loggedUser.name : requestScope.user.name}"/>
        <c:set var="surnameValue" value="${requestScope.user.surname eq null ?
                                sessionScope.loggedUser.surname : requestScope.user.surname}"/>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-4 offset-2">
                    <h2 class="text-muted"><fmt:message key="edit.profile"/></h2>
                    <br><br>
                    <div class="image">
                        <c:set var="avatar" value="${sessionScope.loggedUser.avatar}" />
                        <c:choose>
                            <c:when test="${fn:length(avatar) > 100 }">
                                <img src="${sessionScope.loggedUser.avatar}" class="rounded" width="155">
                            </c:when>
                            <c:otherwise>
                                <img src="img/default_user_photo.png" class="rounded" width="155">
                            </c:otherwise>
                        </c:choose>
                    </div> <br>
                    <form method="post" action="controller" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="update-avatar" >
                        <input type="hidden" name="id" value="${sessionScope.loggedUser.id}">
                        <div class="input-group mb-3">
                            <input type="file" name="avatar" class="form-control">
                            <button type="submit" class="btn btn-outline-secondary"><fmt:message key="upload"/></button>
                        </div>
                    </form>
                    <br>
                    <p class="fs-6 col-md-8">
                        <a href="changePassword.jsp" class="link-dark"><fmt:message key="change.password"/></a>
                    </p>
                </div>

                <div class="col-md-5">
                    <form method="POST" action="controller">
                        <input type="hidden" name="action" value="edit-profile">
                    <div class="form-group">
                        <c:if test="${not empty requestScope.message}">
                            <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
                        </c:if><br>
                        <label class="form-label fs-5" for="email"><fmt:message key="email"/>: </label>
                        <input class="form-control" type="email" name="email" id="email"
                               pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$" required
                               value="${titleValue}" disabled>
                        <c:if test="${fn:contains(error, 'email')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if><br>
                    </div>

                    <div class="form-group">
                        <label class="form-label fs-5" for="name"><fmt:message key="name"/>*: </label>
                        <input class="form-control" name="name" id="name"
                               pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                               title="<fmt:message key="name.requirements"/>" required value="${nameValue}">
                        <c:if test="${fn:contains(error, '.name')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if><br>
                    </div>

                    <div class="form-group">
                        <label class="form-label fs-5" for="surname"><fmt:message key="surname"/>*: </label>
                        <input class="form-control" name="surname" id="surname"
                               pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                               title="<fmt:message key="surname.requirements"/>" required value="${surnameValue}">
                        <c:if test="${fn:contains(error, 'surname')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if><br>
                    </div>
                    <div id="button" class="form-group">
                        <button type="submit" class="btn btn-success mt-0 mb-1"><fmt:message key="submit"/></button>
                    </div>
                    </form>
                </div>
            </div>
        </div>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>