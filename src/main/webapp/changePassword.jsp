<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> <fmt:message key="change.password"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="my.css">
    <script src="js/showPass.js"></script>
    <script src="js/bootstrap.min.js"></script>
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

<div class="col-lg-5 mx-auto p-4 py-md-5">
    <h2 class="text-muted"><fmt:message key="change.password"/></h2>

    <form method="POST" action="controller">
        <input type="hidden" name="action" value="change-password">

        <div class="form-group">
            <c:if test="${not empty requestScope.message}">
                <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if><br>
            <label class="form-label fs-5" for="old-password"><fmt:message key="old.password"/>*: </label>
            <input class="form-control" type="password" name="old-password" id="old-password" required>
            <c:if test="${not empty requestScope.error}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="password"><fmt:message key="new.password"/>*: </label>
            <input class="form-control" type="password" name="password" id="password"
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                   title="<fmt:message key="password.requirements"/>" required><br>
        </div>
        <div class="form-group">
            <label class="form-label fs-5" for="confirm-password"><fmt:message key="confirm.password"/>*: </label>
            <input class="form-control" type="password" name="confirm-password" id="confirm-password"
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                   title="<fmt:message key="password.requirements"/>" required>
        </div>
        <div id="pass" class="form-group">
            <label class="form-check-label">
                <input type="checkbox" id="flexCheckDefault"
                       onclick="showPass('password'); showPass('confirm-password');">
                <label class="form-check-label" for="flexCheckDefault">
                    <fmt:message key="show.password"/>
                </label>
            </label>
        </div>
        <div id="button" class="form-group">
            <button type="submit" class="btn btn-success mt-0 mb-1"><fmt:message key="change.password"/></button>
        </div>
    </form>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>