<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://example.com/tags/orderstatus" prefix="os" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> <fmt:message key="view.user"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@include file="/fragments/import_CSS_and_JS.jsp" %>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-5 mx-auto py-md-4">

    <c:if test="${not empty requestScope.message}">
        <span class="mx-3 my-3 text-success"><fmt:message key="${requestScope.message}"/></span>
    </c:if>

    <c:set var="order" value="${requestScope.order}"/>

    <div class="container">
        <div class="card">
            <div class="card-header bg-light text-dark">
                <h3 class="text-center"><fmt:message key="order.information"/></h3>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-sm-6">
                        <p><strong><fmt:message key="order.id"/>:</strong> ${requestScope.order.id}</p>
                        <p><strong><fmt:message key="order.status"/>:</strong></p>
                    </div>
                    <div class="col-sm-6">
                        <p><strong><fmt:message key="date"/>:</strong> ${requestScope.order.date}</p>
                        <os:orderstatus orderStatus="${requestScope.order.orderStatus}" />
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-6">
                        <p><strong><fmt:message key="customer.information"/></strong></p>
                        <p><fmt:message key="user.id"/>: ${requestScope.order.userId} <br>
                            ${requestScope.order.userName} ${requestScope.order.userSurname}<br>
                            ${requestScope.order.userEmail}
                        </p>
                    </div>
                    <div class="col-sm-6">
                        <p><strong><fmt:message key="tour.information"/></strong></p>
                        <p>
                            <fmt:message key="tour.id"/>: ${requestScope.order.tourId}<br>
                            <fmt:message key="tour.title.name"/>: ${requestScope.order.tourTitle}<br>
                        </p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-6">
                        <p><strong><fmt:message key="tour.price.title"/>:</strong> ${requestScope.order.tourPrice}</p>
                    </div>
                    <div class="col-sm-6">
                        <p><strong><fmt:message key="order.discount.title"/>:</strong> ${requestScope.order.discount}%
                        </p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-12 text-right">
                        <p><strong class="text-uppercase"><fmt:message
                                key="order.total"/>:</strong> ${requestScope.order.totalCost}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <form method="POST" action="controller">
        <input type="hidden" name="action" value="update-order-status">
        <input type="hidden" name="id" value=${requestScope.order.id}>
        <input type="hidden" name="user-id" value=${requestScope.order.userId}>


        <c:choose>
            <c:when test="${requestScope.order.orderStatus eq 'REGISTERED'}">
                <label class="mx-3">
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
            </c:when>

            <c:when test="${requestScope.order.orderStatus eq 'PAID'}">
                <label class="mx-3">
                    <select name="status" class="form-select mt-2">
                        <option value="PAID" ${requestScope.order.orderStatus eq 'PAID' ? 'selected' : ''}>
                            <fmt:message key="PAID"/>
                        </option>
                        <option value="CANCELED" ${requestScope.order.orderStatus eq 'CANCELED' ? 'selected' : ''}>
                            <fmt:message key="CANCELED"/>
                        </option>
                    </select>
                </label>
                <button type="submit" class="btn btn-success btn-sm btn-block"><fmt:message key="set.status"/></button>
            </c:when>
        </c:choose>
    </form>
    <br>

</div>

<jsp:include page="fragments/footer.jsp"/>

<jsp:include page="fragments/deleteUserModal.jsp"/>

</body>
</html>