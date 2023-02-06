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
    <%@include file="/fragments/import_CSS_and_JS.jsp" %>
    <script src="js/showPass.js"></script>
</head>
<jsp:include page="fragments/mainMenu.jsp"/>

<body class="signup-form">
<form method="POST" action="controller">
    <input type="hidden" name="action" value="sign-up">
    <c:set var="error" value="${requestScope.error}"/>
    <h2><fmt:message key="register"/></h2>
    <p class="hint-text">
        <fmt:message key="account.invitation"/>
    </p>

    <div class="form-group">
        <input type="text"
               class="form-control"
               name="name"
               placeholder="<fmt:message key="first.name"/>"
               title="<fmt:message key="name.requirements"/>"
               pattern="^\p{L}[\p{L}'’]{0,29}$"
               required="required" value="${requestScope.user.name}">
        <c:if test="${fn:contains(error, 'name')}">
            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
        </c:if>
    </div>
    <br>

    <div class="form-group">
        <input type="text"
               class="form-control"
               name="surname"
               placeholder="<fmt:message key="surname"/>"
               title="<fmt:message key="surname.requirements"/>"
               pattern="^\p{L}[\p{L}'’]{0,29}$"
               value="${requestScope.user.surname}">
        <c:if test="${fn:contains(error, 'surname')}">
            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
        </c:if>
    </div>
    <br>

    <div class="form-group">
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
        </c:if>
    </div>
    <br>

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

    <div class="form-group">
        <label class="form-check-label">
            <input type="checkbox" id="flexCheckDefault"
                   onclick="showPass('password'); showPass('confirm-password');">
            <label class="form-check-label" for="flexCheckDefault">
                <fmt:message key="show.password"/>
            </label>
        </label>
    </div>
    <br>

    <div class="form-group text-center">
        <button type="submit" class="btn btn-success btn-block"><fmt:message key="sign.up"/></button>
    </div>
    <br>
</form>

<div class="text-center text-muted"><fmt:message key="have.account"/> <a href="signIn.jsp"><fmt:message key="sign.in"/></a>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>