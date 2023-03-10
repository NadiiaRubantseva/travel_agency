<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="profile"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<%-- main navbar --%>
<jsp:include page="fragments/mainMenu.jsp"/>

<br>
<div class="col-lg-7 mx-auto p-2 py-md-2">

    <%-- profile title --%>
    <h2 class="text-muted"><fmt:message key="profile.info"/></h2>

    <%-- blocked user message --%>
    <span class="text-opacity-75 text-danger"><fmt:message key="blocked.message"/></span>

    <%-- user information --%>
    <main>
        <div class="container-fluid">

            <div class="row">
                <div class="col-sm-4">

                    <br><br>

                    <%-- user default picture --%>
                    <div class="image">
                        <img src="img/default_user_photo.png" class="rounded" height="155">
                    </div>

                    <br>

                </div>

                <%-- user contact information --%>
                <div class="col-sm-4">

                    <br>

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

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>