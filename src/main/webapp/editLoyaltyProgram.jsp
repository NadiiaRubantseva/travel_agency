<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title><fmt:message key="travel.agency"/> <fmt:message key="edit.profile"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/my.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
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