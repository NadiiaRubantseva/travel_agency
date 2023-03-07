<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="contacts"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<%-- main menu --%>
<jsp:include page="fragments/mainMenu.jsp"/>

<%-- additional menu based on role --%>
<jsp:include page="fragments/menuChoice.jsp"/>

<br>

<%-- contact information --%>
<figure class="text-start navbar col-8 offset-2 opacity-75">

    <div>
        <br>

        <%-- contacts title --%>
        <h2 class="form-label text-start text-sm-success"><fmt:message key="contacts"/>:</h2><br>

        <%-- contact information --%>
        <p class="lead text-start">
            <span><fmt:message key="signature"/></span><br>
            <span style="color: #f04f01">nadyafranko@gmail.com</span><br>
            <span>LinkedIn</span><br>
            <span style="color: #f04f01"><a href="https://www.linkedin.com/in/nadiia-rubantseva-16a79b253/">https://www.linkedin.com/in/nadiia-rubantseva-16a79b253/ </a></span><br>
        </p>

        <br>
    </div>

</figure>

<br><br>

<%-- footer image --%>
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