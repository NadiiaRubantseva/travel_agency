<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="sign.in"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
    <script src="js/showPass.js"></script>
</head>

<body>

<%-- main navbar --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>
<br>

<%-- sign in form --%>
<div class="signup-form">

    <h2><fmt:message key="sign.in"/></h2>

    <%-- sign in form --%>
    <form method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="action" value="sign-in">
        <c:set var="error" value="${requestScope.error}"/>

        <div id="fg" class="form-group">

            <%-- message --%>
            <c:if test="${not empty requestScope.message}">
                <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if><br>

            <%-- email field --%>
            <input type="email"
                   class="form-control"
                   name="email"
                   id="email"
                   placeholder="<fmt:message key="email"/>"
                   pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$" required
                   value="${requestScope.email}">

            <c:if test="${fn:contains(error, 'email')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>

        </div>

        <%-- password fields --%>
        <div class="form-group">
            <input type="password"
                   class="form-control"
                   name="password"
                   id="password"
                   placeholder="<fmt:message key="password"/>"
                   required>

            <c:if test="${fn:contains(error, 'pass')}">
                <span class="text-danger text-center"><fmt:message key="${requestScope.error}"/></span>
            </c:if>

            <div id="pass" class="form-group">
                <label class="form-check-label">
                    <input type="checkbox" id="flexCheckDefault"
                           onclick="showPass('password'); showPass('confirm-password');">
                    <label class="form-check-label" for="flexCheckDefault">
                        <fmt:message key="show.password"/>
                    </label>
                </label>
            </div>

            <%-- submit button --%>
            <div class="form-group text-center">
                <button type="submit" class="btn btn-success btn-block col-3"><fmt:message key="sign.in"/></button>
            </div>

        </div>
    </form>

    <br>

    <%-- reset password link --%>
    <div class="text-center text-muted"><fmt:message key="forgot.password"/>
        <a href="${pageContext.request.contextPath}/resetPassword.jsp">
            <fmt:message key="reset.password"/>
        </a>
    </div>

    <%-- sign up link --%>
    <div class="text-center text-muted"><fmt:message key="no.account"/>
        <a href="${pageContext.request.contextPath}/signUp.jsp">
            <fmt:message key="sign.up"/>
        </a>
    </div>

</div>

<br>

<%-- footer field --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>