<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
  <title>Travel Agency <fmt:message key="verify"/></title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@include file="/fragments/import_CSS_and_JS.jsp"%>
</head>

<body>
<c:set var="error" value="${requestScope.error}"/>

<jsp:include page="fragments/mainMenu.jsp"/>

<div class="col-lg-7 mx-auto p-4 py-md-5">

  <header class="d-flex align-items-center pb-3 mb-4 border-bottom">
    <span class="fs-4"><fmt:message key="profile.info"/></span>
  </header>

  <span class="text-success">We already send a verification  code to your email.</span>

  <form action="controller" method="post">
    <input type="hidden" name="action" value="verify-code">
    <input type="text" name="authcode" >
    <input type="submit" value=<fmt:message key="verify"/>>
  </form>

  <c:if test="${fn:contains(error, 'code')}">
    <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
  </c:if>

</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
