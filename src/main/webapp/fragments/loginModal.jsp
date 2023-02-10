<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<div class="modal fade" id="loginModal" tabindex="-1" role="dialog"
     aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content rounded-4 shadow">
            <div class="modal-header border-bottom-0">
                <h5 class="modal-title fs-5 text-md-center" id="loginModalLabel"><fmt:message
                        key="authorization.required"/></h5>
                <button type="button" class="btn-close" data-dismiss="modal"
                        aria-label="Close"></button>
            </div>
            <div class="modal-body py-0">
                <fmt:message key="login.invitation"/>
            </div>
            <div class="modal-footer flex-column border-top-0">
                <a href="signIn.jsp" target="_blank">
                    <button type="button" class="btn btn-success mt-4 mb-4"><fmt:message
                            key="sign.in"/></button>
                </a>
            </div>
        </div>
    </div>
</div>
