<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="main"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<%-- main navbar --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>

<%-- additional navbar for different roles --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/menuChoice.jsp"/><br>

<%-- About us description --%>
<figure class="text-start navbar col-8 offset-2 opacity-75">

    <div><br>

        <h2 class="form-label text-center text-success"><fmt:message key="lead.title"/></h2><br>

        <p class="lead">
            <strong style="color: #f04f01"><fmt:message key="travel.agency"/>&nbsp</strong><fmt:message key="lead.2"/><strong style="color: #f04f01"><fmt:message key="lead.3"/></strong><fmt:message key="lead.4"/>
            <strong style="color: #f04f01"><fmt:message key="lead.5"/></strong><fmt:message key="lead.6"/><strong style="color: #f04f01"><fmt:message key="lead.7"/></strong>
            <fmt:message key="lead.8"/>
        </p>

        <p class="lead">
            <strong style="color: #f04f01"><fmt:message key="lead.9"/></strong> <fmt:message key="lead.10"/>
        </p>

    </div>

</figure>

<br>

<%-- About us footer picture --%>
<div class="text-center">

    <footer class="img-fluid">

        <img src="img/aboutUs.png" height=150>

    </footer>

</div>

<br>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>