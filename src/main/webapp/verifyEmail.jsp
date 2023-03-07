<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="verify"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>
<c:set var="error" value="${requestScope.error}"/>

<%-- main navbar --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>

<div class="col-lg-7 mx-auto p-4 py-md-5">

    <%-- email verification title --%>
    <header class="d-flex align-items-center pb-3 mb-4 border-bottom">
        <span class="fs-4 text-muted"><fmt:message key="email.verification"/></span>
    </header>

    <%-- check email message --%>
    <span class="text-success"><fmt:message key="email.verification.invitation"/></span>

    <%-- security code form --%>
    <form action="controller" method="post">
        <input type="hidden" name="action" value="verify-code">

        <%-- security code input --%>
        <label>
            <input type="text" name="security-code" pattern="^[1-9][0-9]{5}$" required="required">
        </label>

        <%-- submit button --%>
        <input type="submit" value=<fmt:message key="verify"/>>

    </form>

    <%-- error message --%>
    <c:if test="${fn:contains(error, 'code')}">
        <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
    </c:if>

</div>

<br><br><br><br><br><br><br><br><br><br><br

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>
