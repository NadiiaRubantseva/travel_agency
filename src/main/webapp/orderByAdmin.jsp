<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> <fmt:message key="view.user"/></title>
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

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">
        <h2 class="text-muted"><fmt:message key="view.order"/></h2>

    <c:set var="order" value="${requestScope.order}"/>

    <main class="border-success shadow-lg p-5 m-5">
        <h5><fmt:message key="id"/>:${requestScope.order.id}</h5>
        <h5><fmt:message key="order.status"/>: ${requestScope.order.orderStatus}</h5>
        <h5><fmt:message key="user.id"/>: ${requestScope.order.userId}</h5>
        <h5><fmt:message key="user.name"/>: ${requestScope.order.userName}</h5>
        <h5><fmt:message key="tour.id"/>: ${requestScope.order.tourId}</h5>
        <h5><fmt:message key="tour.title"/>: ${requestScope.order.tourTitle}</h5>
        <h5><fmt:message key="tour.price"/>: ${requestScope.order.tourPrice}</h5>
        <h5><fmt:message key="order.discount"/>: ${requestScope.order.discount}</h5>
        <h5><fmt:message key="order.total"/>: ${requestScope.order.totalCost}</h5>
    </main>

    <form method="POST" action="controller">
        <input type="hidden" name="action" value="update-order-status">
        <input type="hidden" name="id" value=${requestScope.order.id}>
        <label>
            <select name="status" class="form-select mt-2">
                <option value="REGISTERED" ${requestScope.order.orderStatus eq 'REGISTERED' ? 'selected' : ''}>
                    <fmt:message key="REGISTERED"/>
                </option>
                <option value="PAID" ${requestScope.order.orderStatus eq 'PAID' ? 'selected' : ''}>
                    <fmt:message key="PAID"/>
                </option>
                <option value="CANCELED" ${requestScope.order.orderStatus eq 'CANCELED' ? 'selected' : ''}>
                    <fmt:message key="CANCELED"/>
                </option>
            </select>
        </label>
        <button type="submit" class="btn btn-success btn-sm btn-block"><fmt:message key="set.status"/></button>
    </form><br>

    <c:choose>
        <c:when test="${requestScope.order.orderStatus != 'PAID'}">
            <form method="POST" action="controller">
                <input type="hidden" name="action" value="update-order-discount">
                <input type="hidden" name="id" value=${requestScope.order.id}>
                <input type="hidden" name="tour-price" value=${requestScope.order.tourPrice}>
                <div class="form-group">
                    <label class="form-label fs-4" for="discount"><fmt:message key="discount"/>: </label>
                    <input class="form-control" type="number" name="discount" id="discount"
                           required value="${requestScope.order.discount}">
                    <br>
                </div>
                <button type="submit" class="btn btn-success btn-sm btn-block"><fmt:message key="set.discount"/></button>
            </form>
        </c:when>
    </c:choose>


<%--    <form method="POST" action="controller">--%>
<%--        <input type="hidden" name="action" value="update-order-discount">--%>
<%--        <input type="hidden" name="id" value=${requestScope.order.id}>--%>
<%--        <input type="hidden" name="tour-price" value=${requestScope.order.tourPrice}>--%>
<%--        <div class="form-group">--%>
<%--            <label class="form-label fs-4" for="discount"><fmt:message key="discount"/>: </label>--%>
<%--            <input class="form-control" type="number" name="discount" id="discount"--%>
<%--                   required value="${requestScope.order.discount}">--%>
<%--            <br>--%>
<%--        </div>--%>
<%--        <button type="submit" class="btn btn-success btn-sm btn-block"><fmt:message key="set.discount"/></button>--%>
<%--    </form>--%>

<%--    <button class="btn btn-dark mt-4 mb-4" data-bs-toggle="modal" data-bs-target="#exampleModalDefault">--%>
<%--        <fmt:message key="delete"/>--%>
<%--    </button>--%>
</div>

<jsp:include page="fragments/footer.jsp"/>

<jsp:include page="deleteUserModal.jsp"/>

</body>
</html>