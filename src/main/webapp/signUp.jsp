<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> <fmt:message key="sign.up"/></title>
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
<jsp:include page="fragments/mainMenu.jsp"/>
<br>
<body class="signup-form">
<form method="POST" action="controller">
    <input type="hidden" name="action" value="sign-up">
    <c:set var="error" value="${requestScope.error}"/>
    <h2><fmt:message key="register"/></h2>
    <p class="hint-text">
        <fmt:message key="account.invitation"/>
    </p>

    <div class="form-group">
        <div class="row">
            <div class="row">
                <div class="col">
                    <input type="text"
                           class="form-control"
                           name="name"
                           placeholder="<fmt:message key="first.name"/>"
                           title="<fmt:message key="name.requirements"/>"
                           pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                           required="required" value="${requestScope.user.name}">
                    <c:if test="${fn:contains(error, 'name')}">
                        <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                    </c:if>
                </div>
                <br>

                <div class="col">
                    <input type="text"
                           class="form-control"
                           name="surname"
                           placeholder="<fmt:message key="first.name"/>"
                           title="<fmt:message key="surname.requirements"/>"
                           pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}" required
                           value="${requestScope.user.surname}">
                    <c:if test="${fn:contains(error, 'surname')}">
                        <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                    </c:if>
                </div>
                <br>

            </div>

            <div id="fg" class="form-group">
                <input type="email"
                       class="form-control"
                       name="email"
                       placeholder="<fmt:message key="email"/>"
                       pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$" required
                       value="${requestScope.user.email}">
                <c:if test="${fn:contains(error, 'email')}">
                    <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                </c:if>
            </div>
            <br>

            <div class="form-group">
                <input type="password"
                       class="form-control"
                       name="password"
                       id="password"
                       placeholder="<fmt:message key="password"/>"
                       title="<fmt:message key="password.requirements"/>"
                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$" required>
                <c:if test="${fn:contains(error, 'pass')}">
                    <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                </c:if><br>
            </div>

            <div class="form-group">
                <input type="password"
                       class="form-control"
                       name="confirm-password"
                       id="confirm-password"
                       placeholder="<fmt:message key="confirm.password"/>"
                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                       title="<fmt:message key="password.requirements"/>" required>
            </div>
            <br>

            <div id="pass" class="form-group">
                <label class="form-check-label">
                    <input type="checkbox" id="flexCheckDefault"
                           onclick="showPass('password'); showPass('confirm-password');">
                    <label class="form-check-label" for="flexCheckDefault">
                        <fmt:message key="show.password"/>
                    </label>
                </label>
            </div>
            <br>

            <div id="button" class="form-group">
                <button type="submit" class="btn btn-success btn-sm btn-block"><fmt:message key="sign.up"/></button>
            </div>
            <br>
        </div>
    </div>
</form>

<div class="text-center"><fmt:message key="have.account"/> <a href="signIn.jsp"><fmt:message key="sign.in"/></a>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>