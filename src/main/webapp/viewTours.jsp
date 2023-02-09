<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> <fmt:message key="view.tours"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@include file="/fragments/import_CSS_and_JS.jsp" %>
</head>

<body>

<style>
    .card-img-top {
        height: 200px;
        display: flex;
        align-items: center;
        object-fit: contain;
    }

    img {
        max-height: 100%;
        max-width: 100%;
    }

    .ribbon {
        position: absolute;
        top: -5px;
        left: -5px;
        background-color: rgb(255, 53, 53);
        color: white;
        padding: 5px 10px;
        /*transform: rotate(-45deg);*/
    }

    .ribbon-top-left {
        top: 0;
        left: 0;
    }

</style>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>


<div class="col-lg-10 mx-auto p-4 py-md-4">
    <c:if test="${not empty requestScope.message}">
        <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
    </c:if>
    <div class="container">
        <div class="row">
            <div class="col text-start">
                <h2 class="text-muted"><fmt:message key="tours"/></h2>
            </div>

            <div class="col text-end">
                <form method="GET" action="controller">
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
    </div>

    <hr>

    <div class="container">
        <form method="GET" action="controller">
            <input type="hidden" name="action" value="view-tours">
            <input type="hidden" name="offset" value="0">
            <div class="row">
                <div class="col-2">
                    <label class="col-form-label"><fmt:message key="tour.type"/></label>
                    <select name="type" class="form-select form-control">
                        <option><fmt:message key="select.type"/></option>
                        <option value="3" ${param.type eq "3" ? "selected" : ""}><fmt:message key="SHOPPING"/></option>
                        <option value="2" ${param.type eq "2" ? "selected" : ""}><fmt:message key="EXCURSION"/></option>
                        <option value="1" ${param.type eq "1" ? "selected" : ""}><fmt:message key="REST"/></option>
                    </select>
                </div>
                <div class="col-2">
                    <label class="col-form-label"><fmt:message key="hotel.type"/></label>
                    <select name="hotel" class="form-select form-control">
                        <option><fmt:message key="select.type"/></option>
                        <option value="3" ${param.hotel eq "3" ? "selected" : ""}><fmt:message key="MOTEL"/></option>
                        <option value="2" ${param.hotel eq "2" ? "selected" : ""}><fmt:message key="HOSTEL"/></option>
                        <option value="1" ${param.hotel eq "1" ? "selected" : ""}><fmt:message key="HOTEL"/></option>
                    </select>
                </div>
                <div class="col-1">
                    <label class="col-form-label" for="persons"><fmt:message key="select.persons"/></label>
                    <input type="number" min="1" name="persons" id="persons" class="form-control"
                           value="${not empty requestScope.persons ? requestScope.persons : ""}">

                </div>
                <div class="col-2">
                    <label class="col-form-label" for="min_price"><fmt:message key="price.min"/></label>
                    <input type="number" min="1" name="min_price" id="min_price" class="form-control"
                           value="${not empty requestScope.min_price ? requestScope.min_price : ""}">
                </div>
                <div class="col-2">
                    <label class="col-form-label" for="max_price"><fmt:message key="price.max"/></label>
                    <input type="number" min="1" name="max_price" id="max_price" class="form-control"
                           value="${not empty requestScope.max_price ? requestScope.max_price : ""}">
                </div>
            </div>
            <button type="submit" class="col-2 mt-3 btn btn-success">
                <fmt:message key="submit"/>
            </button>
        </form>
    </div>

    <hr>

    <div class="container mt-2 pt-2">
        <c:set var="tours" value="${requestScope.tours}"/>
        <c:set var="rowCount" value="0"/>
        <c:forEach items="${tours}" var="tour">
            <c:if test="${rowCount % 4 == 0}">
                <div class="row mb-2 pb-2">
            </c:if>
            <div class="col-md-3">
                <div class="card"
                     style="background-color: #f8f9fa; border-radius: 10px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); transition: all 0.3s ease-in-out;">
                    <c:set var="avatar" value="${tour.decodedImage}"/>
                    <c:set var="hot" value="${tour.hot}"/>

                    <c:if test="${fn:contains(hot, 'hot')}">
                        <div class="ribbon ribbon-top-left"><fmt:message key="hot.tour"/></div>
                    </c:if>

                    <div class="card-body" style="cursor: pointer; transition: all 0.3s ease-in-out;">
                        <div class="card-img-top">
                            <c:choose>
                                <c:when test="${fn:length(avatar) > 100 }">
                                    <img class="card-img-top" src="${tour.decodedImage}">
                                </c:when>
                                <c:otherwise>
                                    <img class="card-img-top" src="img/default_user_photo.png">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <hr>

                        <h5 class="card-title text-center">
                                ${tour.title}
                        </h5>
                        <hr>

                        <p class="card-text text-end">
                            <img src="img/persons.png" height="17px" width="17px" style="float: left;">
                            <span style="float: right;"><c:out value="${tour.persons}"/></span><br>
                            <img src="img/price.png" height="17px" width="17px" style="float: left;">
                            <span style="float: right;"><c:out value="${tour.price}"/> грн</span><br>
                            <img src="img/type.png" height="17px" width="17px" style="float: left;">
                            <span style="float: right;"><fmt:message key="${tour.type}"/></span><br>
                            <img src="img/hotel.png" height="17px" width="17px" style="float: left;">
                            <span style="float: right;"><fmt:message key="${tour.hotel}"/></span><br>
                        </p>
                    </div>
                    <div class="card-footer">
                        <a href="controller?action=search-tour&id=${tour.id}"
                           class="btn btn-outline-success btn-sm">
                            <fmt:message key="details"/>
                        </a>
                        <a href="controller?action=book-tour&tour-id=${tour.id}&price=${tour.price}"
                           class="btn btn-outline-warning btn-sm">
                            <fmt:message key="order"/>
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

    <c:set var="href"
           value="controller?action=view-tours&type=${param.type}&sort=hot&order=DESC&sort=${param.sort}&order=${param.order}&persons=${requestScope.persons}&min_price=${requestScope.min_price}&max_price=${requestScope.max_price}&"
           scope="request"/>

    <jsp:include page="/fragments/pagination.jsp"/>
</div>

<jsp:include page="fragments/footer.jsp"/>
<jsp:include page="fragments/bookOrderModal.jsp"/>

</body>
</html>