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
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="my.css">
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
          integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
          crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
          integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
          crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
          integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
          crossorigin="anonymous"></script>
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
