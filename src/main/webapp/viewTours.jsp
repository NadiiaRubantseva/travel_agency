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

<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>

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
                        <img src="${pageContext.request.contextPath}/img/pdf-file.png" height="25">
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
                           onclick="window.location='${pageContext.request.contextPath}/controller?action=view-tours'"
                           value="<fmt:message key="reset"/>"/>

                </div>

            </div>

        </form>

    </div>

    <hr>

    <%-- Display tours  --%>
    <div class="container mt-2 pt-2">
        <c:set var="tours" value="${requestScope.tours}"/>
        <c:set var="rowCount" value="0"/>

        <c:forEach items="${tours}" var="tour">
            <c:set var="image" value="${tour.image}"/>
            <c:set var="hot" value="${tour.hot}"/>

            <%-- New line for each 4 tours  --%>
            <c:if test="${rowCount % 4 == 0}">
                <div class="row mb-2 pb-2">
            </c:if>

            <%-- Tour card  --%>
            <div class="col-md-3">
                <div class="card"
                     style="background-color: #f8f9fa; border-radius: 10px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); transition: all 0.3s ease-in-out;">

                        <%-- Red ribbon on top if tour is hot --%>
                    <c:if test="${fn:contains(hot, 'true')}">
                        <div class="ribbon ribbon-top-left"><fmt:message key="hot.tour"/></div>
                    </c:if>

                    <div class="card-body" style="cursor: pointer; transition: all 0.3s ease-in-out;">

                        <div class="card-img-top">

                            <c:choose>
                                <c:when test="${fn:length(image) > 100 }">
                                    <img class="card-img-top" src="${tour.image}">
                                </c:when>
                                <c:otherwise>
                                    <img class="card-img-top"
                                         src="${pageContext.request.contextPath}/img/default_user_photo.png">
                                </c:otherwise>
                            </c:choose>

                        </div>

                        <hr>

                        <h5 class="card-title text-center">${tour.title}</h5>

                        <hr>

                        <p class="card-text text-end">

                            <img src="${pageContext.request.contextPath}/img/persons.png" height="17px" width="17px"
                                 style="float: left;">
                            <span style="float: right;"><c:out value="${tour.persons}"/></span><br>

                            <img src="${pageContext.request.contextPath}/img/price.png" height="17px" width="17px"
                                 style="float: left;">
                            <span style="float: right;"><c:out value="${tour.price}"/> грн</span><br>

                            <img src="${pageContext.request.contextPath}/img/type.png" height="17px" width="17px"
                                 style="float: left;">
                            <span style="float: right;"><fmt:message key="${tour.type}"/></span><br>

                            <img src="${pageContext.request.contextPath}/img/hotel.png" height="17px" width="17px"
                                 style="float: left;">
                            <span style="float: right;"><fmt:message key="${tour.hotel}"/></span><br>

                        </p>

                    </div>

                    <div class="card-footer text-center">

                        <a href="${pageContext.request.contextPath}/controller?action=search-tour&tour-id=${tour.id}"
                           class="btn btn-outline-success btn-sm">
                            <fmt:message key="see.tour"/>
                        </a>

                    </div>

                </div>
            </div>

            <c:set var="rowCount" value="${rowCount + 1}"/>

            <c:if test="${rowCount % 4 == 0}">
                </div>
            </c:if>

        </c:forEach>

    </div>

    <%-- Link for pagination  --%>
    <c:set var="href"
           value="${pageContext.request.contextPath}/controller?action=view-tours&type=${param.type}&sort=hot&order=DESC&sort=${param.sort}&order=${param.order}&persons=${requestScope.persons}&min_price=${requestScope.min_price}&max_price=${requestScope.max_price}&"
           scope="request"/>

    <%-- Pagination  --%>
    <jsp:include page="${pageContext.request.contextPath}/fragments/pagination.jsp"/>

</div>

<%-- Footer  --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>