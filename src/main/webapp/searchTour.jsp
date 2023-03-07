<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="search.tour"/></title>
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

    <%-- search tour title --%>
    <h2 class="text-muted"><fmt:message key="search.tours"/></h2>

    <%-- additional navbar for different roles --%>
    <form method="GET" action="controller">
        <input type="hidden" name="action" value="search-tour">

        <div class="form-group">

            <%-- success message --%>
            <c:if test="${not empty requestScope.message}">
                <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if><br>

            <%-- tour id input field --%>
            <label class="form-label fs-5" for="tour-id"><fmt:message key="search.tour.by.id"/></label>
            <input class="form-control" type="text" name="tour-id" id="tour-id" pattern="^[1-9][0-9]*$" required>

            <%-- error message --%>
            <c:if test="${not empty requestScope.error}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>

            <br>

        </div>

        <br>

        <%-- submit button --%>
        <button type="submit" class="btn btn-success mt-0 mb-1"><fmt:message key="search"/></button>

    </form>
</div>

<br><br><br>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>
