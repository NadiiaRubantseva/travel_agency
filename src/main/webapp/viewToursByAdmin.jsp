<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/>. <fmt:message key="view.tours"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@include file="/fragments/import_CSS_and_JS.jsp" %>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-10 mx-auto p-4 py-md-4">
    <c:if test="${not empty requestScope.message}">
        <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
    </c:if><br>

    <h2 class="text-muted pb-3"><fmt:message key="tours"/></h2>

    <div class="container-fluid">
        <div class="row">
            <form class="col-10" method="GET" action="controller">
                <input type="hidden" name="action" value="view-tours">
                <input type="hidden" name="offset" value="0">

                <label><fmt:message key="tour.type"/><select name="type" class="form-select mt-2">
                    <option><fmt:message key="select.type"/></option>
                    <option value="3" ${param.type eq "3" ? "selected" : ""}><fmt:message key="SHOPPING"/></option>
                    <option value="2" ${param.type eq "2" ? "selected" : ""}><fmt:message key="EXCURSION"/></option>
                    <option value="1" ${param.type eq "1" ? "selected" : ""}><fmt:message key="REST"/></option>
                </select>
                </label>&nbsp;&nbsp;

                <label><fmt:message key="hotel.type"/>
                    <select name="hotel" class="form-select mt-2">
                        <option><fmt:message key="select.type"/></option>
                        <option value="3" ${param.hotel eq "3" ? "selected" : ""}><fmt:message key="MOTEL"/></option>
                        <option value="2" ${param.hotel eq "2" ? "selected" : ""}><fmt:message key="HOSTEL"/></option>
                        <option value="1" ${param.hotel eq "1" ? "selected" : ""}><fmt:message key="HOTEL"/></option>
                    </select>
                </label>
                &nbsp;&nbsp;
                <label for="persons"><fmt:message key="select.persons"/></label>
                <input class="col-1" type="number" min="1" name="persons" id="persons"
                       value="${not empty requestScope.persons ? requestScope.persons : ""}">&nbsp;&nbsp;
                <label for="min_price"><fmt:message key="select.price.min"/></label>
                <input class="col-2" type="number" min="1" name="min_price" id="min_price"
                       value="${not empty requestScope.min_price ? requestScope.min_price : ""}">
                <label for="max_price"><fmt:message key="select.price.max"/></label>
                <input class="col-2" type="number" min="1" name="max_price" id="max_price"
                       value="${not empty requestScope.max_price ? requestScope.max_price : ""}">
                <br><br>

                <label for="records"><fmt:message key="number.records"/></label>
                <input class="col-1" type="number" min="1" name="records" id="records"
                       value="${not empty requestScope.records ? requestScope.records : "5"}">
                <button type="submit" class="btn btn-success btn-sm mt-0 mb-1"><fmt:message
                        key="submit"/></button>
            </form>

            <form class="col-1 offset-1 justify-content-end" method="GET" action="controller">
                <input type="hidden" name="action" value="tours-pdf">
                <input type="hidden" name="id" value="${param.id}">
                <input type="hidden" name="title" value="${param.title}">
                <input type="hidden" name="persons" value="${param.persons}">
                <input type="hidden" name="price" value="${param.price}">
                <input type="hidden" name="type" value="${param.type}">
                <input type="hidden" name="hotel" value="${param.hotel}">
                <input type="hidden" name="min_price" value="${param.minPrice}">
                <input type="hidden" name="max_price" value="${param.maxPrice}">
                <input type="hidden" name="sort" value="${param.sort}">
                <input type="hidden" name="order" value="${param.order}">
                <button type="submit" class="icon-button"><img src="img/pdf-file.png" height="25"></button>
            </form>
        </div>
    </div>

    <div class="bd-example-snippet bd-code-snippet pt-2">
        <div class="bd-example">
            <table class="table table-striped" aria-label="user-table">
                <thead>
                <tr>

                    <c:set var="base"
                           value="controller?action=view-tours&view=admin&type=${param.type}&hotel=${param.hotel}&persons=${param.persons}&price=${param.price}&"/>

                    <c:set var="byId" value="sort=id&"/>
                    <c:set var="byTitle" value="sort=title&"/>
                    <c:set var="byPersons" value="sort=persons&"/>
                    <c:set var="byPrice" value="sort=price&"/>

                    <c:set var="idOrder"
                           value="order=${empty param.sort ? 'DESC' : param.sort ne 'id' || empty param.order || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                    <c:set var="titleOrder"
                           value="order=${param.sort ne 'title' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                    <c:set var="personsOrder"
                           value="order=${param.sort ne 'persons' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                    <c:set var="priceOrder"
                           value="order=${param.sort ne 'price' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>

                    <c:set var="limits" value="&offset=0&records=${param.records}"/>

                    <th scope="col">
                        <fmt:message key="id"/>
                        <a href="${base.concat(byId).concat(idOrder).concat(limits)}">
                            <i class="bi bi-arrow-down-up link-dark"></i>
                        </a>
                    </th>
                    <th scope="col">
                        <fmt:message key="photo"/>
                        <%--                        <a href="${base.concat(byId).concat(idOrder).concat(limits)}">--%>
                        <%--                            <i class="bi bi-arrow-down-up link-dark"></i>--%>
                        <%--                        </a>--%>
                    </th>
                    <th scope="col" class="col-3">
                        <fmt:message key="title"/>
                        <a href="${base.concat(byTitle).concat(titleOrder).concat(limits)}">
                            <i class="bi bi-arrow-down-up link-dark"></i>
                        </a>
                    </th>
                    <th scope="col">
                        <fmt:message key="persons"/>
                        <a href="${base.concat(byPersons).concat(personsOrder).concat(limits)}">
                            <i class="bi bi-arrow-down-up link-dark"></i>
                        </a>
                    </th>
                    <th scope="col">
                        <fmt:message key="price"/>
                        <a href="${base.concat(byPrice).concat(priceOrder).concat(limits)}">
                            <i class="bi bi-arrow-down-up link-dark"></i>
                        </a>
                    </th>
                    <th scope="col"><fmt:message key="tour.type"/></th>
                    <th scope="col"><fmt:message key="hotel.type"/></th>
                    <%--                    <th scope="col"><fmt:message key="hot"/></th>--%>
                    <th scope="col"><fmt:message key="details"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="tour" items="${requestScope.tours}">
                    <c:set var="image" value="${tour.image}"/>
                    <c:set var="hot" value="${tour.hot}"/>
                    <tr>
                        <td><c:out value="${tour.id}"/></td>
                        <c:choose>
                            <c:when test="${fn:length(image) > 100 }">
                                <td><img src="${tour.image}" class="rounded" height="25"></td>
                            </c:when>
                            <c:otherwise>
                                <td><img src="img/no-tour-image.png" class="rounded" height="25"></td>
                            </c:otherwise>
                        </c:choose>
                        <td><c:out value="${tour.title}"/>
                            <c:if test="${fn:contains(hot, 'hot')}">
                                <img class="mx-1" src="img/fire.png" height="17px" width="17px">
                            </c:if>
                        </td>
                        <td><c:out value="${tour.persons}"/></td>
                        <td><c:out value="${tour.price}"/></td>
                        <td><fmt:message key="${tour.type}"/></td>
                        <td><fmt:message key="${tour.hotel}"/></td>
                            <%--                        <td>--%>
                            <%--                            <c:if test="${fn:contains(hot, 'hot')}">--%>
                            <%--                                <img src="img/fire.png" height="17px" width="17px">--%>
                            <%--                            </c:if>--%>
                            <%--                        </td>--%>
                        <td>
                            <a class="link-dark" href=controller?action=search-tour&tour-id=${tour.id}>
                                <img src="img/info3.png" height="20px" width="20px">
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <c:set var="href"
           value="controller?action=view-tours&view=admin&type=${param.type}&sort=${param.sort}&order=${param.order}&persons=${requestScope.persons}&min_price=${requestScope.min_price}&max_price=${requestScope.max_price}&"
           scope="request"/>

    <jsp:include page="/fragments/pagination.jsp"/>
</div>

<jsp:include page="fragments/footer.jsp"/>
<jsp:include page="fragments/bookOrderModal.jsp"/>

</body>
</html>