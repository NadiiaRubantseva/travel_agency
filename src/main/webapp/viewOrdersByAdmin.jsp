<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="view.orders"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<%-- main navbar --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>

<%-- additional navbar for different roles --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/menuChoice.jsp"/>

<div class="col-lg-11 mx-auto p-4 py-md-5">

    <%-- orders title --%>
    <h2 class="text-muted"><fmt:message key="orders"/></h2>

    <%-- search orders form --%>
    <div class="row">
        <form class="col-11" method="GET" action="controller">
            <input type="hidden" name="action" value="view-orders-by-admin">
            <input type="hidden" name="offset" value="0">
            <div class="row row-cols-auto">

                <%-- order status input --%>
                <label><fmt:message key="order.status"/>
                    <select name="status" class="form-select">
                        <option><fmt:message key="select.status"/></option>
                        <option value="3" ${param.status eq "3" ? "selected" : ""}><fmt:message
                                key="CANCELED"/></option>
                        <option value="2" ${param.status eq "2" ? "selected" : ""}><fmt:message key="PAID"/></option>
                        <option value="1" ${param.status eq "1" ? "selected" : ""}><fmt:message
                                key="REGISTERED"/></option>
                    </select>
                </label>

                <%-- start date input --%>
                <div class="form-group">
                    <label for="start_date"><fmt:message key="start.date"/></label>
                    <input type="date" class="form-control" id="start_date" name="start_date"
                           value="${param.start_date}">
                </div>

                <%-- end date input --%>
                <div class="form-group">
                    <label for="end_date"><fmt:message key="end.date"/></label>
                    <input type="date" class="form-control" id="end_date" name="end_date" value="${param.end_date}">
                </div>

            </div>

            <br>

            <%-- records number input --%>
            <label for="records"><fmt:message key="number.records"/></label>
            <input class="col-2" type="number" min="1" name="records" id="records"
                   value="${not empty requestScope.records ? requestScope.records : "5"}">&nbsp&nbsp&nbsp

            <%-- submit button --%>
            <button type="submit" class="btn btn-success btn-sm mt-0 mb-1"><fmt:message key="submit"/></button>

        </form>

        <%-- pdf form --%>
        <form class="col-1 mt-3" method="GET" action="controller">
            <input type="hidden" name="action" value="orders-pdf">
            <input type="hidden" name="id" value="${param.id}">
            <input type="hidden" name="date" value="${param.date}">
            <input type="hidden" name="orderStatus" value="${param.orderStatus}">
            <input type="hidden" name="status" value="${param.status}">
            <input type="hidden" name="userId" value="${param.userId}">
            <input type="hidden" name="userName" value="${param.userName}">
            <input type="hidden" name="userSurname" value="${param.userSurname}">
            <input type="hidden" name="tourId" value="${param.tourId}">
            <input type="hidden" name="tourTitle" value="${param.tourTitle}">
            <input type="hidden" name="tourPrice" value="${param.tourPrice}">
            <input type="hidden" name="discount" value="${param.discount}">
            <input type="hidden" name="totalCost" value="${param.totalCost}">
            <input type="hidden" name="sort" value="${param.sort}">
            <input type="hidden" name="order" value="${param.order}">
            <button type="submit" class="icon-button"><img src="img/pdf-file.png" height="25"></button>
        </form>
    </div>

    <br>

    <%--orders table --%>
    <div class="bd-example-snippet bd-code-snippet">
        <div class="bd-example">

            <table class="table table-striped" aria-label="order-table">

                <thead>
                <tr>

                    <c:set var="base" value="controller?action=view-orders-by-admin&status=${param.status}&"/>
                    <c:set var="byId" value="sort=id&"/>
                    <c:set var="limits" value="&offset=0&records=${param.records}"/>

                    <th scope="col"><fmt:message key="id"/></th>

                    <th scope="col"><fmt:message key="date"/></th>

                    <th scope="col"><fmt:message key="status"/></th>

                    <th scope="col"><fmt:message key="fullname"/></th>

                    <th scope="col"><fmt:message key="tour.title"/></th>

                    <th scope="col"><fmt:message key="tour.price"/></th>

                    <th scope="col"><fmt:message key="order.discount"/></th>

                    <th scope="col"><fmt:message key="order.total"/></th>

                    <th scope="col"><fmt:message key="action"/></th>

                </tr>
                </thead>

                <tbody>

                <c:forEach var="order" items="${requestScope.orders}">

                    <tr>

                        <td><c:out value="${order.id}"/></td>

                        <td><c:out value="${order.date}"/></td>

                        <c:choose>
                            <c:when test="${order.orderStatus eq 'REGISTERED'}">
                                <td><div style=" text-align: center; background-color: #fdf66a; border-radius: 5px;"><fmt:message key="${order.orderStatus}"/></div></td>
                            </c:when>
                            <c:when test="${order.orderStatus eq 'PAID'}">
                                <td><div style=" text-align: center; background-color: lightgreen; border-radius: 5px;"><fmt:message key="${order.orderStatus}"/></div></td>
                            </c:when>
                            <c:when test="${order.orderStatus eq 'CANCELED'}">
                                <td><div style=" text-align: center; background-color: lightgrey; border-radius: 5px;"><fmt:message key="${order.orderStatus}"/></div></td>
                            </c:when>
                        </c:choose>

                        <td><c:out value="${order.userName}"/> <c:out value="${order.userSurname}"/></td>

                        <td><c:out value="${order.tourTitle}"/></td>

                        <td><fmt:formatNumber value="${order.tourPrice}" pattern="###0"/><fmt:message key="uah"/></td>

                        <td><c:out value="${order.discount}"/>%</td>

                        <td><fmt:formatNumber value="${order.tourPrice}" pattern="###0"/><fmt:message key="uah"/></td>

                        <%-- edit order button --%>
                        <td>
                            <a class="link-dark" href=controller?action=search-order&order-id=${order.id}>
                                <img src="img/edit.png" width="20">
                            </a>
                        </td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>
        </div>
    </div>

    <%-- link for pagination --%>
    <c:set var="href"
           value="controller?action=view-orders-by-admin&status=${param.status}&sort=${param.sort}&order=${param.order}&"
           scope="request"/>

    <%-- pagination --%>
    <jsp:include page="/fragments/pagination.jsp"/>

</div>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>