<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="add.tour"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>
<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-12 mx-auto p-1 py-md-1">
    <header class="d-flex align-items-center pb-0 mb-3 border-bottom">
        <c:if test="${not empty requestScope.message}">
            <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
        </c:if>
        <br>
    </header>

    <div class="container-fluid">
        <form method="POST" action="controller" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add-tour">
            <c:set var="error" value="${requestScope.error}"/>

            <div class="row">
                <div class="col-md-4 offset-1">
                    <h2 class="text-muted"><fmt:message key="add.tour"/></h2>
                    <br>
                                        <div class="image">
                                            <input type="file" name="image" accept="image/*" onchange="loadFile(event)">
                                            <br><br>
                                            <img id="output" width="300" class="rounded"/>
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
                                            <br>

                                            <%-- tour description --%>
                                            <div class="form-group">
                                                <label for="description"><fmt:message key="tour.description"/></label>
                                                <textarea class="form-control" id="description" rows="5"
                                                          name="description">${requestScope.tour.description}</textarea>
                                            </div>

                                        </div>
                    <br>
                </div>

                <div class="col-md-5 offset-1">
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

                    <%--                    <div class="form-group">--%>
                    <%--                        <label class="form-label fs-5" for="discount"><fmt:message key="discount"/>*: </label>--%>
                    <%--                        <input class="form-control" type="number" name="discount" id="discount"--%>
                    <%--                               required value="${requestScope.tour.discount}">--%>
                    <%--                        <c:if test="${fn:contains(error, 'discount')}">--%>
                    <%--                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>--%>
                    <%--                        </c:if>--%>
                    <%--                        <br>--%>
                    <%--                    </div>--%>

                    <label><fmt:message key="tour.type"/>
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

                    <label><fmt:message key="hotel.type"/>
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
                                            <input class="form-check-label" type="checkbox" name="hot" id="hot">
                                            <label class="form-check-label" for="hot">
                                                <fmt:message key="hot"/>
                                            </label>
                                        </div>

                    <br>

                    <button type="submit" class="btn btn-success mt-0 mb-1"><fmt:message key="add.tour"/></button>
                </div>
            </div>
        </form>
    </div>
</div>
<br>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>