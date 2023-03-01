<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-10 mx-auto p-2 py-md-2">

    <c:set var="user" value="${requestScope.user}"/>
    <br>
    <main>
        <div class="container-fluid offset-1">
            <c:if test="${not empty requestScope.message}">
                <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if><br>
            <h2 class="text-muted"><fmt:message key="view.user"/></h2>
            <div class="row">
                <div class="col-sm-4">
                    <br><br>
                    <div class="image">
                        <c:set var="image" value="${requestScope.user.avatar}"/>
                        <c:choose>
                            <c:when test="${fn:length(avatar) > 100 }">
                                <img src="${requestScope.user.avatar}" class="rounded" width="155">
                            </c:when>
                            <c:otherwise>
                                <img src="img/default_user_photo.png" class="rounded" width="155">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <br>
                </div>

                <div class="col-sm-4">
                    <br>
                    <h5 class="text-muted"><fmt:message key="email"/>:</h5>
                    <p>${requestScope.user.email}</p>

                    <h5 class="text-muted"><fmt:message key="name"/>:</h5>
                    <p>${requestScope.user.name}</p>

                    <h5 class="text-muted"><fmt:message key="surname"/>:</h5>
                    <p>${requestScope.user.surname}</p>

                    <h5 class="text-muted"><fmt:message key="role"/>:</h5>
                    <c:choose>
                        <c:when test="${requestScope.user.role eq 'ADMIN'}">
                            <p><fmt:message key="ADMIN"/></p>
                        </c:when>
                        <c:when test="${requestScope.user.role eq 'USER'}">
                            <p><fmt:message key="USER"/></p>
                        </c:when>
                    </c:choose>
                </div>

                <div class="col-sm-10">
                    <form method="POST" action="controller">
                        <input type="hidden" name="action" value="set-role">
                        <input type="hidden" name="id" value=${requestScope.user.id}>
                        <label>
                            <select name="role" class="form-select mt-2">
                                <option value="USER" ${requestScope.user.role eq 'USER' ? 'selected' : ''}>
                                    <fmt:message key="USER"/>
                                </option>
                                <option value="ADMIN" ${requestScope.user.role eq 'ADMIN' ? 'selected' : ''}>
                                    <fmt:message key="ADMIN"/>
                                </option>
                            </select>
                        </label>
                        <button type="submit" class="btn btn-success mt-0"><fmt:message key="set.role"/></button>
                    </form>
                    <br>

                    <form method="POST" action="controller">
                        <input type="hidden" name="action" value="set-user-status">
                        <input type="hidden" name="user-id" value=${requestScope.user.id}>
                        <label>
                            <select name="user-status" class="form-select">
                                <option value="Active" ${requestScope.user.isBlocked eq 'Active' ? 'selected' : ''}>
                                    <fmt:message key="active"/>
                                </option>
                                <option value="Blocked" ${requestScope.user.isBlocked eq 'Blocked' ? 'selected' : ''}>
                                    <fmt:message key="blocked"/>
                                </option>
                            </select>
                        </label>
                        <button type="submit" class="btn btn-success">
                            <fmt:message key="set.user.status"/>
                        </button>
                    </form>
                    <br>

                    <button class="btn btn-danger" data-toggle="modal" data-target="#delete">
                        <fmt:message key="delete"/>
                    </button>
                </div>
            </div>
        </div>
    </main>


</div>

<jsp:include page="fragments/footer.jsp"/>

<jsp:include page="fragments/deleteUserModal.jsp"/>


</body>
</html>