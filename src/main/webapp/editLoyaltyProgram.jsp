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

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-12 mx-auto p-4 py-md-5">

    <c:set var="error" value="${requestScope.error}"/>

    <form class="mx-auto" method="POST" action="controller">
        <input type="hidden" name="action" value="edit-loyalty-program">
        <div class="form-group row">
            <div class="text-center text-muted">
                <h3>Loyalty Program Discount</h3>
            </div>
        </div>
        <br>
        <div class="form-group row">
            <div class="col-sm-3 mx-auto">
                <label for="step">Step</label>
                <input type="number" class="form-control" id="step" name="step" required
                       value="${requestScope.loyaltyProgram.step}">
            </div>
        </div>
        <br>
        <div class="form-group row">
            <div class="col-sm-3 mx-auto">
                <label for="discount">+ discount %</label>
                <input type="number" class="form-control" id="discount" name="discount" required
                       value="${requestScope.loyaltyProgram.discount}">
            </div>
        </div>
        <br>
        <div class="form-group row">
            <div class="col-sm-3 mx-auto">
                <label for="max-discount">Maximum discount</label>
                <input type="number" class="form-control" id="max-discount" name="max-discount" required
                       value="${requestScope.loyaltyProgram.maxDiscount}">
            </div>
        </div>
        <br>
        <div class="form-group row">
            <div class="col-sm-3 mx-auto">
                <button type="submit" class="btn btn-success"><fmt:message key="submit"/></button>
            </div>
        </div>
        <br>
    </form>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>