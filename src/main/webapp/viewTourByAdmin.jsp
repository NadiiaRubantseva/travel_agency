<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="view.tour"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<%-- main navbar --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>

<%-- additional navbar for different roles --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/menuChoice.jsp"/>

<div class="col-lg-12 mx-auto p-1 py-md-1">

    <%-- message --%>
    <header class="d-flex align-items-center pb-0 mb-3 border-bottom offset-1">
        <c:if test="${not empty requestScope.message}">
            <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
        </c:if><br>
    </header>

    <%-- tour information --%>
    <div class="container-fluid">
        <form method="GET" action="controller">
            <input type="hidden" name="action" value="search-tour">
            <input type="hidden" name="purpose" value="edit">
            <input type="hidden" name="tour-id" value=${requestScope.tour.id}>

            <c:set var="error" value="${requestScope.error}"/>
            <c:set var="image" value="${requestScope.tour.image}"/>

            <div class="row">
                <div class="col-md-4 offset-1">

                    <h2 class="text-muted"><fmt:message key="view.tour"/></h2>
                    <br>

                    <%-- tour image --%>
                    <div class="image">
                        <c:choose>
                            <c:when test="${fn:length(image) > 100}">
                                <img src="${requestScope.tour.image}" class="rounded" style="max-width: 250px; max-height: 250px" alt="<fmt:message key="tour.image"/>">
                            </c:when>
                            <c:otherwise>
                                <img src="img/no-tour-image.svg" class="rounded"
                                     style="max-width: 250px; max-height: 250px" alt="<fmt:message key="tour.image"/>">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <br>

                    <%-- tour description --%>
                    <div class="form-group">
                        <label for="description"><fmt:message key="tour.description"/></label>
                        <textarea class="form-control" id="description" rows="5" name="description"
                                  disabled>${requestScope.tour.description}</textarea>
                    </div>
                </div>

                <%-- tour title --%>
                <div class="col-md-5 offset-1 pt-4">
                    <div class="form-group">
                        <label class="form-label fs-5" for="title"><fmt:message key="title"/>*: </label>
                        <input class="form-control" type="text" name="title" id="title"
                               title="<fmt:message key="title.requirements"/>"
                               value="${requestScope.tour.title}" disabled>
                        <c:if test="${fn:contains(error, 'title')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

                    <%-- tour persons --%>
                    <div class="form-group">
                        <label class="form-label fs-5" for="persons"><fmt:message key="persons"/>*: </label>
                        <input class="form-control" type="number" name="persons" id="persons"
                               value="${requestScope.tour.persons}" disabled>
                        <c:if test="${fn:contains(error, 'persons')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

                    <%-- tour price --%>
                    <div class="form-group">
                        <label class="form-label fs-5" for="price"><fmt:message key="price"/>*: </label>
                        <input class="form-control" type="number" name="price" id="price"
                               value="${requestScope.tour.price}" disabled>
                        <c:if test="${fn:contains(error, 'price')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

                    <%-- tour type --%>
                    <label><fmt:message key="tour.type"/>
                        <select name="type" class="form-select mt-2">
                            <option disabled value="REST" ${requestScope.tour.type eq 'REST' ? 'selected' : ''}>
                                <fmt:message key="REST"/>
                            </option>
                            <option disabled
                                    value="EXCURSION" ${requestScope.tour.type eq 'EXCURSION' ? 'selected' : ''}>
                                <fmt:message key="EXCURSION"/>
                            </option>
                            <option disabled value="SHOPPING" ${requestScope.tour.type eq 'SHOPPING' ? 'selected' : ''}>
                                <fmt:message key="SHOPPING"/>
                            </option>
                        </select>
                    </label>

                    <%-- hotel type --%>
                    <label><fmt:message key="hotel.type"/>
                        <select name="hotel" class="form-select mt-2">
                            <option disabled value="HOTEL" ${requestScope.tour.hotel eq 'HOTEL' ? 'selected' : ''}>
                                <fmt:message key="HOTEL"/>
                            </option>
                            <option disabled value="HOSTEL" ${requestScope.tour.hotel eq 'HOSTEL' ? 'selected' : ''}>
                                <fmt:message key="HOSTEL"/>
                            </option>
                            <option disabled value="MOTEL" ${requestScope.tour.hotel eq 'MOTEL' ? 'selected' : ''}>
                                <fmt:message key="MOTEL"/>
                            </option>
                        </select>
                    </label> <br> <br>

                    <%-- hot tour button --%>
                    <div class="form-group">
                        <c:choose>
                            <c:when test="${requestScope.tour.hot eq 'true'}">
                                <input class="form-check-label disabled" type="checkbox" name="hot" id="hot" checked
                                       disabled>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-label disabled" type="checkbox" name="hot" id="hot" disabled>
                            </c:otherwise>
                        </c:choose>
                        <label class="form-check-label" for="hot">
                            <fmt:message key="hot.tour"/>
                        </label>
                    </div>
                    <br>

                    <c:choose>
                        <c:when test="${sessionScope.role eq 'ADMIN'}">
                            <%-- submit button --%>
                            <button type="submit" class="btn btn-success mt-0 mb-1"><fmt:message key="edit.tour"/></button>
                        </c:when>
                    </c:choose>

                </div>
            </div>
        </form

    </div>
</div>
<br>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>