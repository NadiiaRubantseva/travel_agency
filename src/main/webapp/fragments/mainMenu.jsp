<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>
<script src="${pageContext.request.contextPath}/js/activeLink.js"></script>

<c:set var="role" value="${sessionScope.role}"/>

<nav class="navbar navbar-expand-lg navbar-light fixed-top">
    <div class="container-fluid">

        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">
            <span class="mb-0 h4" style="color: #f04f01">
            <fmt:message key="travel.agency"/>
            </span>
        </a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse"
                aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <%-- Options based on role --%>
        <div class="collapse navbar-collapse" id="navbarCollapse">

            <ul class="navbar-nav me-auto mx-4 mb-4 mb-md-0">

                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=view-tours">
                        <fmt:message key="tours"/>
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/about.jsp">
                        <fmt:message key="about"/>
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/contacts.jsp">
                        <fmt:message key="contacts"/>
                    </a>
                </li>

                <c:if test="${not empty sessionScope.loggedUser}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/profile.jsp">
                            <fmt:message key="profile"/>
                        </a>
                    </li>
                </c:if>

                <c:if test="${fn:contains(role, 'USER')}">
                    <li class="nav-item">
                        <a class="nav-link" href="controller?action=view-orders-of-user">
                            <fmt:message key="orders"/>
                        </a>
                    </li>
                </c:if>

            </ul>

            <%-- Sign in / Sign up / Sign out --%>
            <ul class="navbar-nav ml-auto mx-4 mb-4 mb-md-0">

                <c:choose>

                    <c:when test="${sessionScope.loggedUser eq null}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/signIn.jsp">
                                <fmt:message key="sign.in"/>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/signUp.jsp">
                                <fmt:message key="sign.up"/>
                            </a>
                        </li>
                    </c:when>

                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=sign-out">
                                <fmt:message key="sign.out"/>
                            </a>
                        </li>
                    </c:otherwise>

                </c:choose>

            </ul>

            <%-- Language selection --%>
            <form method="POST" class="d-flex">
                <label>
                    <select name="locale" onchange='submit();'>
                        <option value="en" ${sessionScope.locale eq 'en' ? 'selected' : ''}>
                            <fmt:message key="en"/>
                        </option>
                        <option value="uk_UA" ${sessionScope.locale eq 'uk_UA' ? 'selected' : ''}>
                            <fmt:message key="ua"/>
                        </option>
                    </select>
                </label>
            </form>

        </div>
    </div>
</nav>

<br><br>

