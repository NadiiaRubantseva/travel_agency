<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="profile"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-7 mx-auto p-4 py-md-5">

    <header class="d-flex align-items-center pb-3 mb-4 border-bottom">
        <span class="fs-4"><fmt:message key="profile.info"/></span>
    </header>

    <main>
        <p class="fs-6"><fmt:message key="email"/>:</p>
        <p class="fs-5">${sessionScope.loggedUser.email}</p>

        <p class="fs-6"><fmt:message key="name"/>:</p>
        <p class="fs-5">${sessionScope.loggedUser.name}</p>

        <p class="fs-6"><fmt:message key="surname"/>:</p>
        <p class="fs-5">${sessionScope.loggedUser.surname}</p><br>
    </main>

    <a href="editProfile.jsp" class="btn btn-dark mt-0 mb-1"><fmt:message key="edit.profile"/></a>

</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>