<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<div class="modal fade" id="bookOrderModalWindow" tabindex="-1" aria-labelledby="bookOrderModalWindow" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content rounded-4 shadow">
            <div class="modal-header border-bottom-0">
                <h1 class="modal-title fs-5 text-md-center"><fmt:message key="book.tour"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body py-0">
                <p><fmt:message key="book.tour.confirmation"/></p>
            </div>
            <div class="modal-footer flex-column border-top-0">
                <form method="POST" action="controller">
                    <input type="hidden" name="action" value="book-tour">
                    <input type="hidden" name="tour-id" value=${requestScope.tour.id}>
                    <input type="hidden" name="tour-price" value=${requestScope.price}>
                    <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="yes"/></button>
                </form>
            </div>
        </div>
    </div>
</div>