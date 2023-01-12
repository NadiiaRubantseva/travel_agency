package ua.epam.travelagencyms.controller.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;

public class NowTag extends SimpleTagSupport {

    @Override
    public void doTag() throws IOException {
        final JspWriter writer = getJspContext().getOut();
        writer.print(LocalDate.now());
    }
}