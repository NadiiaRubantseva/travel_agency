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
</head>

<body>

<%-- main navbar --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/mainMenu.jsp"/>

<%-- additional navbar for different roles --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/menuChoice.jsp"/>

<div class="col-lg-12 mx-auto p-4 py-md-5">

    <c:set var="error" value="${requestScope.error}"/>

    <%-- loyalty program form --%>
    <form class="mx-auto" method="GET" action="controller">
        <input type="hidden" name="action" value="search-loyalty-program">
        <input type="hidden" name="purpose" value="edit">

        <div class="form-group row">

            <%-- success message --%>
            <c:if test="${not empty requestScope.message}">
                <span class="text-center text-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if>

            <br>

            <%-- loyalty program title --%>
            <div class="text-center text-muted">
                <h3><fmt:message key="loyalty.program"/></h3>
            </div>

        </div>

        <br>

        <%-- loyalty program step --%>
        <div class="form-group row">
            <div class="col-sm-3 mx-auto">
                <label for="step"><fmt:message key="step"/></label>
                <input type="number" class="form-control" id="step" name="step" required
                       value="${requestScope.loyaltyProgram.step}" disabled>
            </div>
        </div>

        <br>

        <%-- loyalty program discount --%>
        <div class="form-group row">
            <div class="col-sm-3 mx-auto">
                <label for="discount">+ <fmt:message key="discount"/> %</label>
                <input type="number" class="form-control" id="discount" name="discount" required
                       value="${requestScope.loyaltyProgram.discount}" disabled>
            </div>
        </div>

        <br>

        <%-- loyalty program max discount --%>
        <div class="form-group row">
            <div class="col-sm-3 mx-auto">
                <label for="max-discount"><fmt:message key="max.discount"/></label>
                <input type="number" class="form-control" id="max-discount" name="max-discount" required
                       value="${requestScope.loyaltyProgram.maxDiscount}" disabled>
            </div>
        </div>

        <br>

        <%-- submit button --%>
        <div class="form-group row">
            <div class="col-sm-3 mx-auto">
                <button type="submit" class="btn btn-danger"><fmt:message key="edit"/></button>
            </div>
        </div>

        <br>

    </form>
</div>

<%-- footer --%>
<jsp:include page="${pageContext.request.contextPath}/fragments/footer.jsp"/>

</body>
</html>