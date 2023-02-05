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
    <%@include file="/fragments/import_CSS_and_JS.jsp"%>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<%--<jsp:include page="fragments/menuChoice.jsp"/>--%>

<br>
<div class="col-lg-7 mx-auto p-2 py-md-2">

    <h2 class="text-muted"><fmt:message key="profile.info"/></h2>
    <span class="text-opacity-75 text-danger">You are blocked. Please contact customer support.</span>

    <main>
        <div class="container-fluid">

            <div class="row">
                <div class="col-sm-4">
                    <br><br>
                    <div class="image">
                                <img src="img/default_user_photo.png" class="rounded" height="155">
                    </div>
                    <br>
                </div>

                <div class="col-sm-4"><br>
                    <h5><fmt:message key="email"/>:</h5>
                    <p>${requestScope.blockedUser.email}</p>

                    <h5><fmt:message key="name"/>:</h5>
                    <p>${requestScope.blockedUser.name}</p>

                    <h5><fmt:message key="surname"/>:</h5>
                    <p>${requestScope.blockedUser.surname}</p>
                </div>
            </div>
        </div>
    </main>
</div>
<br><br><br><br>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>