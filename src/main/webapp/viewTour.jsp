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
    <%@include file="/fragments/import_CSS_and_JS.jsp" %>
    <style>
        .hot-ribbon {
            position: absolute;
            top: -15px;
            left: 0;
            background-color: red;
            color: white;
            padding: 5px 10px;
            font-weight: bold;
        }

        .tour-details {
            padding-left: 20px;
        }

        .tour-description {
            margin-top: 20px;
        }

        .tour-image {
            position: relative;
        }

        .red-italic {
            color: #de0202;
            font-style: italic;
        }

    </style>
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
            <input type="hidden" name="action" value="book-tour">
            <input type="hidden" name="tour-id" value="${requestScope.tour.id}">

            <c:set var="error" value="${requestScope.error}"/>
            <c:set var="decodedImage" value="${requestScope.tour.decodedImage}"/>
            <c:set var="hot" value="${requestScope.tour.hot}"/>

            <h2 class="col-md-4 offset-1 text-muted"><fmt:message key="view.tour"/></h2>
            <br>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-4 offset-1 tour-image">
                        <img src="${requestScope.tour.decodedImage}" alt="Tour Image" class="img-fluid" width="300">
                        <p class="hot-ribbon"><fmt:message key="hot.tour"/></p>
                        <br><br>
                        <p class="text-danger text-opacity-75"><fmt:message key="loyalty.program.note"/></p>
                    </div>
                    <div class="col-md-6 tour-details">
                        <p><strong><fmt:message key="tour.type"/>:&nbsp</strong><fmt:message
                                key="${requestScope.tour.type}"/></p>
                        <p><strong><fmt:message key="persons"/>:&nbsp</strong> ${requestScope.tour.persons}</p>
                        <p><strong><fmt:message key="hotel"/>:&nbsp</strong> <fmt:message
                                key="${requestScope.tour.hotel}"/></p>
                        <p class=" tour-description"><strong><fmt:message
                                key="tour.description"/>:&nbsp</strong> ${requestScope.tour.description}</p>
                        <p><strong><fmt:message key="tour.price"/>:&nbsp</strong> ${requestScope.tour.price}
                            <fmt:message key="uah"/></p>
                        <c:choose>
                            <c:when test="${empty requestScope.discount}">
                                <p class="red-italic"><strong><fmt:message key="discount"/>*:&nbsp</strong>Увійдіть в
                                    обліковий запис</p>
                            </c:when>
                            <c:otherwise>
                                <p class="red-italic"><strong><fmt:message
                                        key="discount"/>*:&nbsp</strong>${requestScope.discount}%</p>
                            </c:otherwise>
                        </c:choose>
                        <p><strong>Всього до сплати:&nbsp</strong>
                            ${requestScope.tour.price} грн
                        </p><br>
                        <button class="btn btn-success" onclick="return checkSession()">
                            <fmt:message key="order"/>
                        </button>

                        <!-- Modal -->
                        <jsp:include page="fragments/loginModal.jsp"/>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<br>


<jsp:include page="fragments/footer.jsp"/>
<jsp:include page="fragments/bookModal.jsp"/>

<script type="text/javascript">
    function checkSession() {
        var userId = "${sessionScope.loggedUser.id}";

        if (userId) {
            $('#book').modal('show');
            return false;
        } else {
            $('#loginModal').modal('show');
            return false;
        }
    }
</script>

</body>
</html>