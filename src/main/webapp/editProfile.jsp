<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="edit.profile"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
    <script src="${pageContext.request.contextPath}/js/previewImage.js"></script>
</head>

<body>

<%-- main menu --%>
<jsp:include page="fragments/mainMenu.jsp"/>

<%-- additional menu based on role --%>
<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-12 mx-auto p-1 py-md-1">

    <%-- message --%>
    <header class="d-flex align-items-center pb-0 mb-3 border-bottom offset-1">
        <c:if test="${not empty requestScope.message}">
            <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
        </c:if><br>
    </header>

    <div class="container-fluid">
        <form method="POST" action="controller" enctype="multipart/form-data">
            <input type="hidden" name="action" value="edit-profile">

            <c:set var="error" value="${requestScope.error}"/>
            <c:set var="error" value="${requestScope.error}"/>
            <c:set var="email" value="${requestScope.user.email eq null ? sessionScope.loggedUser.email : requestScope.user.email}"/>
            <c:set var="nameValue" value="${requestScope.user.name eq null ? sessionScope.loggedUser.name : requestScope.user.name}"/>
            <c:set var="surnameValue" value="${requestScope.user.surname eq null ? sessionScope.loggedUser.surname : requestScope.user.surname}"/>
            <c:set var="avatar" value="${requestScope.user.avatar eq null ? sessionScope.loggedUser.avatar : requestScope.user.avatar}"/>

            <div class="row">

                <div class="col-md-4 offset-2">

                    <%-- Edit profile title --%>
                    <h2 class="text-muted"><fmt:message key="edit.profile"/></h2>
                    <br>

                    <%-- user image --%>
                    <div class="image">

                        <div class="form-group">
                            <img id="preview"
                            <c:choose>
                            <c:when test="${fn:length(avatar) > 100 }">
                                <img src="${avatar}"
                            </c:when>
                            <c:otherwise>
                            <img src="img/default_user_photo.png"
                            </c:otherwise>
                            </c:choose>
                                 alt="Image Preview"
                                 style="max-width: 250px; max-height: 250px">
                        </div>
                        <br>

                        <div class="form-group">
                            <label for="avatar"></label> <input type="file"
                                                                class="form-control-file" id="avatar"
                                                                name="avatar"
                                                                accept="image/*"
                                                                onchange="readURL(this);">
                        </div>

                    </div>

                    <br>

                    <%-- change password link --%>
                    <p class="fs-6 col-md-4">
                        <a href="changePassword.jsp" class="link-dark"><fmt:message key="change.password"/></a>
                    </p>

                </div>

                <div class="col-md-4 pt-4">

                    <%-- user email --%>
                    <div class="form-group">
                        <c:if test="${not empty requestScope.message}">
                            <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
                        </c:if><br>
                        <label class="form-label fs-5" for="email"><fmt:message key="email"/>: </label>
                        <input class="form-control" type="email" name="email" id="email"
                               value="${email}" disabled>
                        <c:if test="${fn:contains(error, 'email')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if><br>
                    </div>

                    <%-- use name --%>
                    <div class="form-group">
                        <label class="form-label fs-5" for="name"><fmt:message key="name"/>*: </label>
                        <input class="form-control" name="name" id="name"
                               pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                               title="<fmt:message key="name.requirements"/>" required value="${nameValue}">
                        <c:if test="${fn:contains(error, '.name')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if><br>
                    </div>

                    <%-- user surname --%>
                    <div class="form-group">
                        <label class="form-label fs-5" for="surname"><fmt:message key="surname"/>*: </label>
                        <input class="form-control" name="surname" id="surname"
                               pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                               title="<fmt:message key="surname.requirements"/>" required
                               value="${surnameValue}">
                        <c:if test="${fn:contains(error, 'surname')}">
                            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                        </c:if><br>
                    </div>

                    <br>

                    <%-- submit button --%>
                    <button type="submit" class="btn btn-success mt-0 mb-1"><fmt:message key="submit"/></button>

                </div>
            </div>
        </form>
    </div>
</div>
<br>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>