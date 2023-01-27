<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Travel Agency <fmt:message key="view.orders"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-icons.css">
    <link rel="stylesheet" href="css/my.css">
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

<div class="col-lg-10 mx-auto p-4 py-md-5">
    <h2 class="text-muted"><fmt:message key="orders"/></h2>

    <div class="row">
        <form class="col-11" method="GET" action="controller">
            <input type="hidden" name="action" value="view-orders-of-user">
            <input type="hidden" name="offset" value="0">
            <div>
                <label><fmt:message key="order.status"/><select name="status" class="form-select mt-2">
                    <option><fmt:message key="select.status"/></option>
                    <option value="3" ${param.status eq "3" ? "selected" : ""}><fmt:message key="CANCELED"/></option>
                    <option value="2" ${param.status eq "2" ? "selected" : ""}><fmt:message key="PAID"/></option>
                    <option value="1" ${param.status eq "1" ? "selected" : ""}><fmt:message key="REGISTERED"/></option>
                </select>
                </label>
            </div>
            &nbsp&nbsp&nbsp&nbsp&nbsp
            <br>
            <label for="records"><fmt:message key="number.records"/></label>
            <input class="col-2" type="number" min="1" name="records" id="records"
                   value="${not empty requestScope.records ? requestScope.records : "5"}">&nbsp&nbsp&nbsp
            <button type="submit" class="btn btn-success btn-sm mt-0 mb-1"><fmt:message key="submit"/></button>
        </form>

        <form class="col-1 mt-3" method="GET" action="controller">
            <input type="hidden" name="status" value="${param.status}">
            <input type="hidden" name="sort" value="${param.sort}">
            <input type="hidden" name="order" value="${param.order}">
            <button type="submit" class="icon-button"><img src="img/pdf-file.png" height="25"></button>
        </form>
    </div>

    <div class="bd-example-snippet bd-code-snippet">
        <div class="bd-example">
            <table class="table table-striped" aria-label="order-table">
                <thead>
                <tr>

                    <c:set var="base" value="controller?action=view-orders-of-user&status=${param.status}&"/>
                    <c:set var="byId" value="sort=id&"/>
                    <c:set var="limits" value="&offset=0&records=${param.records}"/>

                    <th scope="col" class="col-md-2">
                        <fmt:message key="status"/>
                    </th>
                    <th scope="col" class="col-md-2">
                        <fmt:message key="order.date"/>
                    </th>
                    <th scope="col" class="col-md-3">
                        <fmt:message key="tour.title"/>
                    </th>
                    <th scope="col" class="col-md-1">
                        <fmt:message key="tour.price"/>
                    </th>
                    <th scope="col" class="col-md-1">
                        <fmt:message key="order.discount"/>
                    </th>
                    <th scope="col" class="col-md-1">
                        <fmt:message key="order.total"/>
                    </th>
                    <th scope="col" class="col-md-1">
                        <fmt:message key="action"/>
                    </th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="order" items="${requestScope.orders}">
                    <tr>
                        <c:choose>
                            <c:when test="${order.orderStatus eq 'REGISTERED'}">
                                <td><div style=" text-align: center; background-color: #fdf66a; border-radius: 5px;"><c:out value="${order.orderStatus}"/></div></td>
                            </c:when>
                            <c:when test="${order.orderStatus eq 'PAID'}">
                                <td><div style=" text-align: center; background-color: lightgreen; border-radius: 5px;"><c:out value="${order.orderStatus}"/></div></td>
                            </c:when>
                            <c:when test="${order.orderStatus eq 'CANCELED'}">
                                <td><div style=" text-align: center; background-color: lightgrey; border-radius: 5px;"><c:out value="${order.orderStatus}"/></div></td>
                            </c:when>
                        </c:choose>
                        <td><c:out value="${order.date}"/></td>
                        <td><c:out value="${order.tourTitle}"/></td>
                        <td><c:out value="${order.tourPrice}"/> грн</td>
                        <td><c:out value="${order.discount}"/>%</td>
                        <td><c:out value="${order.totalCost}"/> грн</td>
                        <td>
                            <c:choose>
                                <c:when test="${order.orderStatus == 'CANCELED'}">
                                    <button type="button" class="disabled btn btn-sm btn-danger">
                                        <fmt:message key="CANCELED"/>
                                    </button>
                                </c:when>
                                <c:otherwise>

<%--                                    <button type="button" class="btn btn-sm btn-danger" data-toggle="modal"--%>
<%--                                            data-target="#cancel-order">--%>
<%--                                        <fmt:message key="cancel"/>--%>
<%--                                    </button>--%>

                                    <form method="POST" action="controller">
                                        <input type="hidden" name="action" value="cancel-order">
                                        <input type="hidden" name="order-id" value=${order.id}>
                                        <button type="submit" class="btn btn-sm btn-danger">
                                            <fmt:message key="cancel"/>
                                        </button>
                                    </form>
                                </c:otherwise>
                            </c:choose>

<%--                            <div id="cancel-order" class="modal fade" role="dialog">--%>
<%--                                <div class="modal-dialog">--%>
<%--                                    <div class="modal-content rounded-4 shadow">--%>
<%--                                        <div class="modal-header border-bottom-0">--%>
<%--                                            <h1 class="modal-title fs-5 text-md-center" id="exampleModalLabel">--%>
<%--                                                <fmt:message key="cancel.order"/></h1>--%>
<%--                                            <button type="button" class="btn-close"--%>
<%--                                                    data-bs-dismiss="modal"></button>--%>
<%--                                        </div>--%>
<%--                                        <div class="modal-body py-0">--%>
<%--                                            <p><fmt:message key="cancel.order.confirmation"/></p>--%>
<%--                                        </div>--%>
<%--                                        <div class="modal-footer flex-column border-top-0">--%>
<%--                                            <form method="POST" action="controller">--%>
<%--                                                <input type="hidden" name="action" value="cancel-order">--%>
<%--                                                <input type="hidden" name="order-id" value=${order.id}>--%>
<%--                                                <button type="submit" class="btn btn-dark mt-4 mb-4">--%>
<%--                                                    <fmt:message key="yes"/>--%>
<%--                                                </button>--%>
<%--                                            </form>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <c:set var="href"
           value="controller?action=view-orders-of-user&status=${param.status}&sort=${param.sort}&order=${param.order}&"
           scope="request"/>
    <jsp:include page="/fragments/pagination.jsp"/>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>