<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<div class="container">
    <footer class="py-2 my-3">
        <p class="text-center text-muted">2023 © <fmt:message key="travel.agency"/></p>
    </footer>
</div>