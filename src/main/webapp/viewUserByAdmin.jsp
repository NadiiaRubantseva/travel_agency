<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/><fmt:message key="view.user"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@include file="/fragments/import_CSS_and_JS.jsp"%>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-10 mx-auto p-2 py-md-2">

    <c:set var="user" value="${requestScope.user}"/>
    <br>
    <main>
        <div class="container-fluid offset-1">
            <div class="row">
                <div class="col-sm-4">
                    <h2 class="text-muted"><fmt:message key="view.user"/></h2>
                    <br><br>
                    <div class="image">
                        <c:set var="avatar" value="${requestScope.user.avatar}"/>
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
                    <h5><fmt:message key="email"/>:</h5>
                    <p>${requestScope.user.email}</p>

                    <h5><fmt:message key="name"/>:</h5>
                    <p>${requestScope.user.name}</p>

                    <h5><fmt:message key="surname"/>:</h5>
                    <p>${requestScope.user.surname}</p>

                    <h5><fmt:message key="role"/>:</h5>
                    <p>${requestScope.user.role}</p>
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
                        <button type="submit" class="btn btn-success mt-0 mb-1"><fmt:message key="set.role"/></button>
                    </form>
                    <br>

                    <form method="POST" action="controller">
                        <input type="hidden" name="action" value="set-user-status">
                        <input type="hidden" name="email" value=${requestScope.user.email}>
                        <label>
                            <select name="status" class="form-select mt-2">
                                <option value="ACTIVE" ${requestScope.user.status eq 'ACTIVE' ? 'selected' : ''}>
                                    ACTIVE
                                </option>
                                <option value="BLOCKED" ${requestScope.user.status eq 'BLOCKED' ? 'selected' : ''}>
                                    BLOCKED
                                </option>
                            </select>
                        </label>
                        <button type="submit" class="btn btn-success mt-0 mb-1">Set Status</button>
                    </form>
                    <br>

                    <button class="btn btn-danger mt-0 mb-1" data-toggle="modal" data-target="#delete">
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