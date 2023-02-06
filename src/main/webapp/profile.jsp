<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> <fmt:message key="profile"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@include file="/fragments/import_CSS_and_JS.jsp"%>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<br>
<div class="col-lg-7 mx-auto p-2 py-md-2"><br>

    <h2 class="text-muted"><fmt:message key="profile.info"/></h2>

    <main>
        <div class="container-fluid">

            <div class="row">
                <div class="col-sm-4">
                    <br><br>
                    <div class="image">
                        <c:set var="avatar" value="${sessionScope.loggedUser.avatar}" />
                        <c:choose>
                            <c:when test="${fn:length(avatar) > 100 }">
                                <img src="${sessionScope.loggedUser.avatar}" class="rounded" width="155">
                            </c:when>
                            <c:otherwise>
                                <img src="img/default_user_photo.png" class="rounded" height="155">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <br>
                </div>

                <div class="col-sm-4">
                    <p>
                    <h5 class="text-muted"><fmt:message key="email"/>:</h5></p>
                    <p>${sessionScope.loggedUser.email}</p>

                    <h5 class="text-muted"><fmt:message key="name"/>:</h5>
                    <p>${sessionScope.loggedUser.name}</p>

                    <h5 class="text-muted"><fmt:message key="surname"/>:</h5>
                    <p>${sessionScope.loggedUser.surname}</p>
                </div>
            </div>
        </div>
    </main>

    <div id="button" class="form-group">
        <a href="editProfile.jsp" class="btn btn-success mt-0 mb-1"><fmt:message key="change"/></a>
    </div>

</div>
<br><br>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>