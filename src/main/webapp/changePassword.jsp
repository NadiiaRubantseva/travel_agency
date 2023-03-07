<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="change.password"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
    <script src="js/showPass.js"></script>
</head>

<body>

<%-- main menu --%>
<jsp:include page="fragments/mainMenu.jsp"/>

<%-- additional menu based on role --%>
<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">

    <%-- change password title --%>
    <h2 class="text-muted"><fmt:message key="change.password"/></h2>

    <%-- form --%>
    <form method="POST" action="controller">
        <input type="hidden" name="action" value="change-password">

        <%-- old password input --%>
        <div class="form-group">

            <%-- success message --%>
            <c:if test="${not empty requestScope.message}">
                <span class="text-bg-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if>

            <br>

            <label class="form-label fs-5" for="old-password"><fmt:message key="old.password"/>*: </label>
            <input class="form-control" type="password" name="old-password" id="old-password" required>

            <c:if test="${not empty requestScope.error}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>

        </div>

        <%-- new password input --%>
        <div class="form-group">

            <label class="form-label fs-5" for="password"><fmt:message key="new.password"/>*: </label>
            <input class="form-control" type="password" name="password" id="password"
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                   title="<fmt:message key="password.requirements"/>" required>
            <br>

        </div>

        <%-- repeat new password input --%>
        <div class="form-group">

            <label class="form-label fs-5" for="confirm-password"><fmt:message key="confirm.password"/>*: </label>
            <input class="form-control" type="password" name="confirm-password" id="confirm-password"
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                   title="<fmt:message key="password.requirements"/>" required>

        </div>

        <%-- show password button --%>
        <div id="pass" class="form-group">

            <label class="form-check-label">
                <input type="checkbox" id="flexCheckDefault"
                       onclick="showPass('password'); showPass('confirm-password');">
                <label class="form-check-label" for="flexCheckDefault">
                    <fmt:message key="show.password"/>
                </label>
            </label>

        </div>

        <%-- change password button --%>
        <div id="button" class="form-group">
            <button type="submit" class="btn btn-success mt-0 mb-1"><fmt:message key="change.password"/></button>
        </div>

    </form>
</div>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>