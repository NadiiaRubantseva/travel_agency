<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> View tour</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
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
    <
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-12 mx-auto p-1 py-md-1">
    <header class="d-flex align-items-center pb-0 mb-3 border-bottom offset-1">
        <c:if test="${not empty requestScope.message}">
            <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
        </c:if><br>
    </header>


    <div class="container-fluid">
        <form method="GET" action="controller" enctype="multipart/form-data">
            <input type="hidden" name="action" value="search-tour">
            <input type="hidden" name="purpose" value="edit">
            <input type="hidden" name="id" value=${requestScope.tour.id}>
            <c:set var="error" value="${requestScope.error}"/>


            <c:set var="decodedImage" value="${requestScope.tour.decodedImage}" />

            <div class="row">
                <div class="col-md-4 offset-1">
                    <h2 class="text-muted"> View tour </h2>
                    <br>
                    <div class="image">
                        <c:choose>
                            <c:when test="${fn:length(decodedImage) > 100}">
                                <img src="${requestScope.tour.decodedImage}" class="rounded" width="300">
                            </c:when>
                            <c:otherwise>
                                <img src="img/no-tour-image.png" class="rounded" width="300">
                            </c:otherwise>
                        </c:choose>
                    </div> <br>
                </div>

                <div class="col-md-5 offset-1">
                    <div class="form-group">
                        <label class="form-label fs-5" for="title"><fmt:message key="title"/>*: </label>
                        <input class="form-control" type="text" name="title" id="title"
                               title="<fmt:message key="title.requirements"/>"
                               pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                               value="${requestScope.tour.title}" disabled>
                        <c:if test="${fn:contains(error, 'title')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

                    <div class="form-group">
                        <label class="form-label fs-5" for="persons"><fmt:message key="persons"/>*: </label>
                        <input class="form-control" type="number" name="persons" id="persons"
                               value="${requestScope.tour.persons}" disabled>
                        <c:if test="${fn:contains(error, 'persons')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

                    <div class="form-group">
                        <label class="form-label fs-5" for="price"><fmt:message key="price"/>*: </label>
                        <input class="form-control" type="number" name="price" id="price"
                               value="${requestScope.tour.price}" disabled>
                        <c:if test="${fn:contains(error, 'price')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

                    <%--                    <div class="form-group">--%>
                    <%--                        <label class="form-label fs-5" for="discount"><fmt:message key="discount"/>*: </label>--%>
                    <%--                        <input class="form-control" type="number" name="discount" id="discount"--%>
                    <%--                               required value="${requestScope.tour.discount}">--%>
                    <%--                        <c:if test="${fn:contains(error, 'discount')}">--%>
                    <%--                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>--%>
                    <%--                        </c:if>--%>
                    <%--                        <br>--%>
                    <%--                    </div>--%>

                    <label>Tour Type
                        <select name="type" class="form-select mt-2">
                            <option disabled value="REST" ${requestScope.tour.type eq 'REST' ? 'selected' : ''}>
                                <fmt:message key="REST"/>
                            </option>
                            <option disabled value="EXCURSION" ${requestScope.tour.type eq 'EXCURSION' ? 'selected' : ''}>
                                <fmt:message key="EXCURSION"/>
                            </option>
                            <option disabled value="SHOPPING" ${requestScope.tour.type eq 'SHOPPING' ? 'selected' : ''}>
                                <fmt:message key="SHOPPING"/>
                            </option>
                        </select>
                    </label>

                    <label>Hotel Type
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

                    <div class="form-group">
                        <c:choose>
                            <c:when test="${requestScope.tour.hot eq 'hot'}">
                                <input class="form-check-label disabled" type="checkbox" name="hot" id="hot" checked disabled>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-label disabled" type="checkbox" name="hot" id="hot" disabled>
                            </c:otherwise>
                        </c:choose>
                        <label class="form-check-label" for="hot">
                            Hot
                        </label>
                    </div>
                    <br>
                    <button type="submit" class="btn btn-success mt-0 mb-1"><fmt:message key="edit.tour"/></button>
                </div>
            </div>
        </form>
<%--        <div>--%>
<%--            <button type="button" class="col-5 offset-6 btn btn-danger" data-toggle="modal" data-target="#delete-tour">--%>
<%--                <fmt:message key="delete"/>--%>
<%--            </button>--%>
<%--            <div id="delete-tour" class="modal fade"  role="dialog">--%>
<%--                <div class="modal-dialog">--%>
<%--                    <div class="modal-content rounded-4 shadow">--%>
<%--                        <div class="modal-header border-bottom-0">--%>
<%--                            <h1 class="modal-title fs-5 text-md-center" id="exampleModalLabel"><fmt:message--%>
<%--                                    key="delete.tour"/></h1>--%>
<%--                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>--%>
<%--                        </div>--%>
<%--                        <div class="modal-body py-0">--%>
<%--                            <p><fmt:message key="delete.tour.confirmation"/></p>--%>
<%--                        </div>--%>
<%--                        <div class="modal-footer flex-column border-top-0">--%>
<%--                            <form method="POST" action="controller">--%>
<%--                                <input type="hidden" name="action" value="delete-tour">--%>
<%--                                <input type="hidden" name="tour-id" value=${requestScope.tour.id}>--%>
<%--                                <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="yes"/></button>--%>
<%--                            </form>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
    </div>
</div>
<br>
<jsp:include page="fragments/footer.jsp"/>


</body>
</html>