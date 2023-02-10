package ua.epam.travelagencyms.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class OrderStatusTag extends TagSupport {
    private String orderStatus;

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            String statusClass = "text-warning";
            if (orderStatus.equals("PAID")) {
                statusClass = "text-success";
            } else if (orderStatus.equals("CANCELED")) {
                statusClass = "text-danger";
            }
            out.println("<p><strong class='" + statusClass + "'>" + orderStatus + "</strong></p>");
        } catch (Exception e) {
            throw new JspException("Error: " + e.getMessage());
        }
        return SKIP_BODY;
    }
}
