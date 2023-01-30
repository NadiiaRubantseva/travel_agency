<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> <fmt:message key="main"/></title>
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

<jsp:include page="fragments/menuChoice.jsp"/><br>
<figure class="text-start navbar col-8 offset-2 opacity-75">
    <div><br>
        <h2 style="color: #f04f01" class="form-label text-center"><fmt:message key="lead.title"/></h2><br>
        <p class="lead">
            <strong style="color: #f04f01"><fmt:message key="travel.agency"/>&nbsp</strong><fmt:message key="lead.2"/><strong style="color: #f04f01"><fmt:message key="lead.3"/></strong><fmt:message key="lead.4"/>
            <strong style="color: #f04f01"><fmt:message key="lead.5"/></strong><fmt:message key="lead.6"/><strong style="color: #f04f01"><fmt:message key="lead.7"/></strong>
            <fmt:message key="lead.8"/>
        </p>
        <p class="lead">
            <strong style="color: #f04f01"><fmt:message key="lead.9"/></strong> <fmt:message key="lead.10"/>
        </p>
    </div>
</figure>
<br>

<div class="text-center">
    <footer class="img-fluid">
        <img src="img/aboutUs.png" height=150>
    </footer>
</div><br>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>