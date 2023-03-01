<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="edit.tour"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
    <script>
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#preview').attr('src', e.target.result);
                }
                reader.readAsDataURL(input.files[0]);
            }
        }

        $(document).ready(function () {
            $("#image").change(function () {
                var file = this.files[0];
                var imagefile = file.type;
                var match = ["image/jpeg", "image/png", "image/jpg"];
                if (!((imagefile == match[0]) || (imagefile == match[1]) || (imagefile == match[2]))) {
                    $('#preview').attr('src', '');
                    alert("Please select a valid image file (JPEG/JPG/PNG).");
                    return false;
                }
            });
        });
    </script>
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
        <form method="POST" action="controller" enctype="multipart/form-data">
            <input type="hidden" name="action" value="edit-tour">
            <input type="hidden" name="tour-id" value=${requestScope.tour.id}>


            <c:set var="error" value="${requestScope.error}"/>
            <c:set var="image" value="${requestScope.tour.image}"/>

            <div class="row">
                <div class="col-md-4 offset-1">
                    <h2 class="text-muted"><fmt:message key="view.tour"/></h2>
                    <br>
                    <div class="image">
                        <div class="form-group">
                            <img id="preview"
                            <c:choose>
                            <c:when test="${fn:length(image) > 100 }">
                                <img src="${image}"
                            </c:when>
                            <c:otherwise>
                            <img src="img/no-tour-image.png"
                            </c:otherwise>
                            </c:choose>
                                 alt="Image Preview"
                                 style="max-width: 250px; max-height: 250px">
                        </div>
                        <br>
                        <div class="form-group">
                            <label for="image"></label> <input type="file"
                                                               class="form-control-file" id="image"
                                                               name="image"
                                                               accept="image/*"
                                                               onchange="readURL(this);">
                        </div>
                    </div>

                    <br>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" id="description" rows="3"
                                  name="description">${requestScope.tour.description}</textarea>
                    </div>
                </div>

                <div class="col-md-5 offset-1">
                    <div class="form-group">
                        <label class="form-label fs-5" for="title"><fmt:message key="title"/>*: </label>
                        <input class="form-control" type="text" name="title" id="title"
                               title="<fmt:message key="title.requirements"/>"
                               pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                               value="${requestScope.tour.title}">
                        <c:if test="${fn:contains(error, 'title')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

                    <div class="form-group">
                        <label class="form-label fs-5" for="persons"><fmt:message key="persons"/>*: </label>
                        <input class="form-control" type="number" name="persons" id="persons"
                               value="${requestScope.tour.persons}">
                        <c:if test="${fn:contains(error, 'persons')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

                    <div class="form-group">
                        <label class="form-label fs-5" for="price"><fmt:message key="price"/>*: </label>
                        <input class="form-control" type="number" name="price" id="price"
                               value="${requestScope.tour.price}">
                        <c:if test="${fn:contains(error, 'price')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if>
                        <br>
                    </div>

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
                        <c:choose>
                            <c:when test="${requestScope.tour.hot eq 'hot'}">
                                <input class="form-check-label" type="checkbox" name="hot" id="hot" checked>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-label" type="checkbox" name="hot" id="hot">
                            </c:otherwise>
                        </c:choose>
                        <label class="form-check-label" for="hot">
                            <fmt:message key="hot"/>
                        </label>
                    </div>
                    <br>
                    <button type="submit" class="btn btn-success mt-0 mb-1"><fmt:message key="edit.tour"/></button>
                </div>
            </div>
        </form>
        <div class="col-2 offset-6 px-3">
            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#delete-tour">
                <fmt:message key="delete"/>
            </button>
            <div id="delete-tour" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content rounded-4 shadow">
                        <div class="modal-header border-bottom-0">
                            <h1 class="modal-title fs-5 text-md-center" id="exampleModalLabel"><fmt:message
                                    key="delete.tour"/></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body py-0">
                            <p><fmt:message key="delete.tour.confirmation"/></p>
                        </div>
                        <div class="modal-footer flex-column border-top-0">
                            <form method="POST" action="controller">
                                <input type="hidden" name="action" value="delete-tour">
                                <input type="hidden" name="tour-id" value=${requestScope.tour.id}>
                                <button type="submit" class="btn btn-danger mt-4 mb-4"><fmt:message key="yes"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<br>
<jsp:include page="fragments/footer.jsp"/>


</body>
</html>