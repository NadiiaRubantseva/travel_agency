<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="search.order"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<%-- main navbar --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>

<%-- additional navbar for different roles --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/menuChoice.jsp"/>

<br>

<div class="col-lg-5 mx-auto p-4 py-md-5">

    <%-- search order title --%>
    <h2 class="text-muted"><fmt:message key="search.order"/></h2>

    <form method="GET" action="controller">
        <input type="hidden" name="action" value="search-order">

        <div class="form-group">

            <%-- success message --%>
            <c:if test="${not empty requestScope.message}">
                <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if>

            <br>

            <%-- order id input field --%>
            <label class="form-label" for="id"><fmt:message key="search.order.by.ID"/></label>
            <input class="form-control" type="text" name="order-id" id="id" pattern="^[1-9][0-9]*$" required>

            <%-- error message --%>
            <c:if test="${not empty requestScope.error}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>

            <br>

        </div>

        <br>

        <%-- submit button --%>
        <button type="submit" class="btn btn-success btn-sm btn-block"><fmt:message key="search"/></button>

    </form>
</div>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>
