<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="reset.password"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<%-- main navbar --%>
<jsp:include page="fragments/mainMenu.jsp"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">

    <%-- reset password title --%>
    <h2 class="text-muted"><tags:header value="reset.password"/></h2>

    <%-- reset password form --%>
    <form method="POST" action="controller">
        <input type="hidden" name="action" value="password-reset">

        <div>

            <%-- *custom tag --%>
            <%-- message success --%>
            <tags:notEmptyMessage value="${requestScope.message}"/><br>

            <%-- email input field --%>
            <label class="form-label fs-5" for="email"><fmt:message key="email"/>: </label>
            <input class="form-control" type="email" name="email" id="email" required
                   value="${not empty requestScope.email ? requestScope.email : ""}"
                   pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$">

            <%-- *custom tag --%>
            <%-- message error --%>
            <tags:notEmptyError value="${requestScope.error}"/>
            <br>

        </div>

        <%-- submit button --%>
        <button type="submit" class="btn btn-success btn-sm btn-block"><fmt:message key="reset.password"/></button>

    </form>

</div>

<br><br><br><br><br>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>