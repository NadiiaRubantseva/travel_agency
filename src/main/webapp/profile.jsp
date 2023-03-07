<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="profile"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="${pageContext.request.contextPath}/fragments/import_CSS_and_JS.jsp"/>
</head>

<body>

<%-- main navbar --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>

<%-- additional navbar for different roles --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/menuChoice.jsp"/>
<br>

<br>

<div class="col-lg-7 mx-auto p-2 py-md-2">

    <br>

    <%-- profile info title --%>
    <h2 class="text-muted"><fmt:message key="profile.info"/></h2>

    <main>
        <div class="container-fluid">

            <div class="row">
                <div class="col-sm-4">
                    <br><br>

                    <%-- avatar --%>
                    <div class="image">
                        <c:set var="avatar" value="${sessionScope.loggedUser.avatar}"/>
                        <c:choose>
                            <c:when test="${fn:length(avatar) > 100 }">
                                <img src="${sessionScope.loggedUser.avatar}" class="rounded" width="155"
                                     alt="<fmt:message key="avatar"/>">
                            </c:when>
                            <c:otherwise>
                                <img src="img/default_user_photo.png" class="rounded" height="155"
                                     alt="<fmt:message key="avatar"/>">
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <br>

                    <%-- edit profile button --%>
                    <div id="button" class="form-group">
                        <a href="editProfile.jsp" class="btn btn-success mt-0 mb-1"><fmt:message key="change"/></a>
                    </div>

                </div>

                <%-- user information --%>
                <div class="col-sm-4">

                    <%-- email --%>
                    <h5 class="text-muted"><fmt:message key="email"/>:</h5>
                    <p>${sessionScope.loggedUser.email}</p>

                    <%-- name --%>
                    <h5 class="text-muted"><fmt:message key="name"/>:</h5>
                    <p>${sessionScope.loggedUser.name}</p>

                    <%-- surname --%>
                    <h5 class="text-muted"><fmt:message key="surname"/>:</h5>
                    <p>${sessionScope.loggedUser.surname}</p>

                    <%-- user role --%>
                    <c:if test="${sessionScope.loggedUser.role != 'USER'}">
                        <h5 class="text-muted"><fmt:message key="role"/>:</h5>
                        <p><fmt:message key="${sessionScope.loggedUser.role}"/></p>
                    </c:if>

                </div>
            </div>
        </div>
    </main>
</div>

<br><br>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>