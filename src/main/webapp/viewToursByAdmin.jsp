<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="view.tours"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<%-- main navbar --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>

<%-- additional navbar for different roles --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/menuChoice.jsp"/>

<div class="col-lg-10 mx-auto p-4 py-md-4">

    <%-- message-success --%>
    <c:if test="${not empty requestScope.message}">
        <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
    </c:if>

    <div class="container">

        <%--top row --%>
        <div class="row">

            <%-- Tours label --%>
            <div class="col text-start">

                <h2 class="text-muted"><fmt:message key="tours"/></h2>

            </div>

            <%-- PDF icon --%>
            <div class="col text-end">

                <form method="GET" action="${pageContext.request.contextPath}/controller">

                    <input type="hidden" name="action" value="tours-pdf">
                    <input type="hidden" name="tour-id" value="${param.id}">
                    <input type="hidden" name="title" value="${param.title}">
                    <input type="hidden" name="persons" value="${param.persons}">
                    <input type="hidden" name="price" value="${param.price}">
                    <input type="hidden" name="type" value="${param.type}">
                    <input type="hidden" name="hotel" value="${param.hotel}">
                    <input type="hidden" name="min_price" value="${param.min_price}">
                    <input type="hidden" name="max_price" value="${param.max_price}">
                    <input type="hidden" name="sort" value="${param.sort}">
                    <input type="hidden" name="order" value="${param.order}">

                    <button type="submit" class="icon-button">
                        <img src="${pageContext.request.contextPath}/img/pdf-file.png" height="25" alt="<fmt:message key="pdf.image"/>">
                    </button>

                </form>

            </div>
        </div>
    </div>

    <hr>

    <div class="container">

        <%-- Search bar --%>
        <form method="GET" id="searchForm" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="action" value="view-tours">
            <input type="hidden" name="offset" value="0">
            <input type="hidden" name="view" value="admin">

            <div class="row">

                <%-- Tour type --%>
                <div class="col-2">

                    <label class="col-form-label"><fmt:message key="tour.type"/></label>

                    <select name="type" class="form-select form-control">
                        <option><fmt:message key="select.type"/></option>
                        <option value="3" ${param.type eq "3" ? "selected" : ""}><fmt:message key="SHOPPING"/></option>
                        <option value="2" ${param.type eq "2" ? "selected" : ""}><fmt:message key="EXCURSION"/></option>
                        <option value="1" ${param.type eq "1" ? "selected" : ""}><fmt:message key="REST"/></option>
                    </select>

                </div>

                <%-- Hotel type --%>
                <div class="col-2">

                    <label class="col-form-label"><fmt:message key="hotel.type"/></label>

                    <select name="hotel" class="form-select form-control">
                        <option><fmt:message key="select.type"/></option>
                        <option value="3" ${param.hotel eq "3" ? "selected" : ""}><fmt:message key="MOTEL"/></option>
                        <option value="2" ${param.hotel eq "2" ? "selected" : ""}><fmt:message key="HOSTEL"/></option>
                        <option value="1" ${param.hotel eq "1" ? "selected" : ""}><fmt:message key="HOTEL"/></option>
                    </select>

                </div>

                <%-- Persons --%>
                <div class="col-1">

                    <label class="col-form-label" for="persons"><fmt:message key="select.persons"/></label>
                    <input type="number" min="1" name="persons" id="persons" class="form-control"
                           value="${not empty param.persons ? param.persons : ""}">

                </div>

                <%-- Price min --%>
                <div class="col-2">

                    <label class="col-form-label" for="min_price"><fmt:message key="price.min"/></label>
                    <input type="number" min="1" name="min_price" id="min_price" class="form-control"
                           value="${not empty param.min_price ? param.min_price : ""}">

                </div>

                <%-- Price max --%>
                <div class="col-2">

                    <label class="col-form-label" for="max_price"><fmt:message key="price.max"/></label>
                    <input type="number" min="1" name="max_price" id="max_price" class="form-control"
                           value="${not empty param.max_price ? param.max_price : ""}">

                </div>

            </div>

            <div class="row">

                <div class="col-12 mt-2">

                    <%-- Submit search button --%>
                    <button type="submit" class="col-2 btn btn-success btn-sm">
                        <fmt:message key="submit"/>
                    </button>

                    <%-- Reset search button --%>
                    <input type="button" class="col-1 btn btn-outline-secondary btn-sm"
                           onclick="window.location='${pageContext.request.contextPath}/controller?action=view-tours&view=admin'"
                           value="<fmt:message key="reset"/>"/>

                </div>

            </div>

        </form>

    </div>

    <hr>

    <div class="bd-example-snippet bd-code-snippet pt-2">
        <div class="bd-example">
            <table class="table table-striped" aria-label="user-table">
                <thead>
                <tr>

                    <c:set var="base" value="controller?action=view-tours&view=admin&type=${param.type}&hotel=${param.hotel}&persons=${param.persons}&price=${param.price}&"/>
                    <c:set var="byId" value="sort=id&"/>
                    <c:set var="byTitle" value="sort=title&"/>
                    <c:set var="byPersons" value="sort=persons&"/>
                    <c:set var="byPrice" value="sort=price&"/>
                    <c:set var="idOrder" value="order=${empty param.sort ? 'DESC' : param.sort ne 'id' || empty param.order || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                    <c:set var="titleOrder" value="order=${param.sort ne 'title' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                    <c:set var="personsOrder" value="order=${param.sort ne 'persons' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                    <c:set var="priceOrder" value="order=${param.sort ne 'price' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>

                    <c:set var="limits" value="&offset=0&records=${param.records}"/>

                    <th scope="col">
                        <fmt:message key="id"/>
                        <a href="${base.concat(byId).concat(idOrder).concat(limits)}">
                            <i class="bi bi-arrow-down-up link-dark"></i>
                        </a>
                    </th>
                    <th scope="col">
                        <fmt:message key="photo"/>
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
                                <td><img src="${tour.image}" class="rounded" height="25" alt="<fmt:message key="tour.image"/>"></td>
                            </c:when>
                            <c:otherwise>
                                <td><img src="img/no-tour-image.svg" class="rounded" height="25" alt="<fmt:message key="tour.image"/>"></td>
                            </c:otherwise>
                        </c:choose>
                        <td><c:out value="${tour.title}"/>
                            <c:if test="${fn:contains(hot, 'true')}">
                                <img class="mx-1" src="img/fire.png" height="17px" width="17px" alt="<fmt:message key="fire.image"/>">
                            </c:if>
                        </td>
                        <td><c:out value="${tour.persons}"/></td>
                        <td><fmt:formatNumber value="${tour.price}" pattern="###0" /></td>
                        <td><fmt:message key="${tour.type}"/></td>
                        <td><fmt:message key="${tour.hotel}"/></td>
                        <td>
                            <a class="link-dark" href=controller?action=search-tour&tour-id=${tour.id}>
                                <img src="img/info3.png" height="20px" width="20px" alt="<fmt:message key="info.image"/>">
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

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>