<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="search.users"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<%-- main navbar --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>

<%-- additional navbar for different roles --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/menuChoice.jsp"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">

    <%-- search users title --%>
    <h2 class="text-muted"><fmt:message key="search.users"/></h2>

    <%-- search user form --%>
    <form method="GET" action="controller">
        <input type="hidden" name="action" value="search-user-by-email">

        <div class="form-group">

            <%-- success message --%>
            <c:if test="${not empty requestScope.message}">
                <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if>

            <br>

            <%-- user email input field --%>
            <label class="form-label fs-5" for="email"><fmt:message key="search.user.by.email"/></label>
            <input class="form-control" type="email" name="email" id="email"
                   pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$" required>

            <%-- error message --%>
            <c:if test="${not empty requestScope.error}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>

            <br>

        </div>

        <%-- button message --%>
        <div id="button" class="form-group">
            <button type="submit" class="btn btn-success mt-0 mb-1"><fmt:message key="search"/></button>
        </div>

    </form>
</div>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>
