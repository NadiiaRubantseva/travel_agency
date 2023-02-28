<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/>. <fmt:message key="main"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- import of CSS & JS resources --%>
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<%-- main navbar --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>

<%-- additional navbar for different roles --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/menuChoice.jsp"/><br>

<%-- welcome image and tag line --%>
<figure class="text-center">
    <img src="${pageContext.request.contextPath}/img/index.jpg" class="figure-img img-fluid" alt="<fmt:message key="travel.agency"/>">
    <figcaption class="figure-caption"><fmt:message key="pic.description"/></figcaption>
</figure>

<br>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>