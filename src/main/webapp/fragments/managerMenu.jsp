<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<nav class="navbar navbar-expand-md navbar-light bg-light">
    <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarCollapse2" aria-controls="navbarCollapse"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse2">
            <ul class="navbar-nav me-auto mx-4 mb-4 mt-2 mb-md-0">
                <li class="nav-item">
                    <div class="nav-item dropdown">
                        <a data-toggle="dropdown" class="nav-item nav-link dropdown-toggle"><img src="img/users.png"
                                                                                                 height="25px"><fmt:message
                                key="users"/></a>
                        <div class="dropdown-menu">
                            <a href="controller?action=view-users" class="dropdown-item">
                                <fmt:message key="view.users"/></a>
                            <a href="searchUser.jsp" class="dropdown-item">
                                <fmt:message key="search.users"/></a>
                        </div>
                    </div>
                </li>

                <li class="nav-item">
                    <div class="nav-item dropdown">
                        <a data-toggle="dropdown" class="nav-item nav-link dropdown-toggle"><img src="img/tour.png"
                                                                                                 height="25px"><fmt:message
                                key="tours"/></a>
                        <div class="dropdown-menu">
                            <a href="controller?action=view-tours&view=admin" class="dropdown-item"><fmt:message
                                    key="view.tours"/></a>
                            <a href="searchTour.jsp" class="dropdown-item"><fmt:message key="search.tour"/></a>
                        </div>
                    </div>
                </li>

                <li class="nav-item">
                    <div class="nav-item dropdown">
                        <a data-toggle="dropdown" class="nav-item nav-link dropdown-toggle"><img src="img/orders.png" height="25px">
                            <fmt:message key="orders"/></a>
                        <div class="dropdown-menu">
                            <a href="controller?action=view-orders-by-admin" class="dropdown-item">
                                <fmt:message key="view.orders"/>
                            </a>
                            <a href="searchOrder.jsp" class="dropdown-item">
                                <fmt:message key="search.order"/>
                            </a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
