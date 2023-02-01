package ua.epam.travelagencyms.controller.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;


/**
 * NowTag  class.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class NowTag extends SimpleTagSupport {

    /**
     *  Writes to JspWriter formatted current date.
     */
    @Override
    public void doTag() throws IOException {
        final JspWriter writer = getJspContext().getOut();
        writer.print(LocalDate.now());
    }
}