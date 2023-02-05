<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> <fmt:message key="edit.tour"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@include file="/fragments/import_CSS_and_JS.jsp"%>
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
        <div class="row">
            <%--            <h2 class="text-muted offset-md-1 pt-1 pb-0"> Edit tour </h2>--%>


            <div class="col-md-4 offset-1"><br>
                <h2 class="text-muted"> View tour </h2><br>
                <form method="POST" action="controller" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="upload-tour-image">
                    <input type="hidden" name="id" value=${requestScope.tour.id}>
                    <c:set var="error" value="${requestScope.error}"/>
                    <c:set var="decodedImage" value="${requestScope.image}"/>

                    <div class="row">
                        <div class="image col-md-2">
                            <c:choose>
                                <c:when test="${fn:length(decodedImage) > 100}">
                                    <img src="${requestScope.image}" class="rounded" width="200">
                                </c:when>
                                <c:otherwise>
                                    <img src="img/no-tour-image.png" class="rounded" width="200">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <br>
                        <div class="image col-md-2 offset-5"><br>
                            <input type="file" name="image" accept="image/*" onchange="loadFile(event)">
                            <br><br>
                            <img id="output" height="100" width="100" class="rounded"/>
                            <br><br>
                            <script>
                                var loadFile = function (event) {
                                    var output = document.getElementById('output');
                                    output.src = URL.createObjectURL(event.target.files[0]);
                                    output.onload = function () {
                                        URL.revokeObjectURL(output.src) // free memory
                                    }
                                };
                            </script>
                        </div>
                    </div>
                    <br>
                    <button type="submit" class="btn col-3 btn-success">Upload</button>
                </form>
            </div>

            <div class="col-md-4 offset-2">
                <form method="POST" action="controller" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="edit-tour">
                    <input type="hidden" name="id" value=${requestScope.tour.id}>
                    <c:set var="error" value="${requestScope.error}"/>

                    <div class="form-group">
                        <label class="form-label fs-5" for="title"><fmt:message key="title"/>*: </label>
                        <input class="form-control" type="text" name="title" id="title"
                               title="<fmt:message key="title.requirements"/>"
                               pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                               required value="${requestScope.tour.title}">
                        <c:if test="${fn:contains(error, 'title')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

                    <div class="form-group">
                        <label class="form-label fs-5" for="persons"><fmt:message key="persons"/>*: </label>
                        <input class="form-control" type="number" name="persons" id="persons"
                               required value="${requestScope.tour.persons}">
                        <c:if test="${fn:contains(error, 'persons')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

                    <div class="form-group">
                        <label class="form-label fs-5" for="price"><fmt:message key="price"/>*: </label>
                        <input class="form-control" type="number" name="price" id="price"
                               required value="${requestScope.tour.price}">
                        <c:if test="${fn:contains(error, 'price')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

                    <label>Tour Type
                        <select name="type" class="form-select mt-2">
                            <option value="REST" ${requestScope.tour.type eq 'REST' ? 'selected' : ''}>
                                <fmt:message key="REST"/>
                            </option>
                            <option value="EXCURSION" ${requestScope.tour.type eq 'EXCURSION' ? 'selected' : ''}>
                                <fmt:message key="EXCURSION"/>
                            </option>
                            <option value="SHOPPING" ${requestScope.tour.type eq 'SHOPPING' ? 'selected' : ''}>
                                <fmt:message key="SHOPPING"/>
                            </option>
                        </select>
                    </label>

                    <label>Hotel Type
                        <select name="hotel" class="form-select mt-2">
                            <option value="HOTEL" ${requestScope.tour.hotel eq 'HOTEL' ? 'selected' : ''}>
                                <fmt:message key="HOTEL"/>
                            </option>
                            <option value="HOSTEL" ${requestScope.tour.hotel eq 'HOSTEL' ? 'selected' : ''}>
                                <fmt:message key="HOSTEL"/>
                            </option>
                            <option value="MOTEL" ${requestScope.tour.hotel eq 'MOTEL' ? 'selected' : ''}>
                                <fmt:message key="MOTEL"/>
                            </option>
                        </select>
                    </label> <br> <br>

                    <div class="form-group">
                        <c:choose>
                            <c:when test="${requestScope.tour.hot eq 'hot'}">
                                <input class="form-check-label" type="checkbox" name="hot" id="hot" checked>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-label" type="checkbox" name="hot" id="hot">
                            </c:otherwise>
                        </c:choose>
                        <label class="form-check-label" for="hot">
                            Hot
                        </label>
                    </div>
                    <br>

                    <div class="row-cols-5">
                        <button type="submit" class="btn col-5 btn-success"><fmt:message
                                key="edit.tour"/></button>
                    </div>
                </form><br>

                <div class="row-cols-5">
                    <button type="button" class="col-5 btn btn-danger" data-toggle="modal"
                            data-target="#delete-tour">
                        <fmt:message key="delete"/>
                    </button>
                    <div id="delete-tour" class="modal fade" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content rounded-4 shadow">
                                <div class="modal-header border-bottom-0">
                                    <h1 class="modal-title fs-5 text-md-center" id="exampleModalLabel">
                                        <fmt:message
                                                key="delete.tour"/></h1>
                                    <button type="button" class="btn-close" data-dismiss="modal"></button>
                                </div>
                                <div class="modal-body py-0">
                                    <p><fmt:message key="delete.tour.confirmation"/></p>
                                </div>
                                <div class="modal-footer flex-column border-top-0">
                                    <form method="POST" action="controller">
                                        <input type="hidden" name="action" value="delete-tour">
                                        <input type="hidden" name="tour-id" value=${requestScope.tour.id}>
                                        <button type="submit" class="btn btn-danger mt-0 mb-1"><fmt:message
                                                key="yes"/></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>


</body>
</html>