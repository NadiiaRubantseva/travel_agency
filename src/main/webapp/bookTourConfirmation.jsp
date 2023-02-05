<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Travel Agency MS Successfully booked tour</title>
    <%@include file="/fragments/import_CSS_and_JS.jsp"%>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>
<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">
    <p class="fs-4 col-md-8 text-bg-success">Successfully booked</p><br>

    <p class="fs-4 col-md-4"><a href="controller?action=view-orders-of-user" class="link-dark">See orders</a></p>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>