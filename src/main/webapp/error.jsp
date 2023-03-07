<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="oops"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>
<body>

<%-- empty menu --%>
<jsp:include page="fragments/emptyMenu.jsp"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">

    <%-- error message --%>
    <p class="fs-4 col-md-8 text-error"><fmt:message key="global.error"/></p>

    <br><br><br>

    <%-- to Main page link --%>
    <p class="fs-6 col-md-8"><a href="index.jsp" class="link-dark"><fmt:message key="to.main"/></a></p>

</div>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>