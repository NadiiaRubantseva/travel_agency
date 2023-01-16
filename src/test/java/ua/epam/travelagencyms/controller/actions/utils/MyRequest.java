package ua.epam.travelagencyms.controller.actions.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class MyRequest extends HttpServletRequestWrapper {
    private final Map<String, Object> attributes = new HashMap<>();
    private final HttpSession session = new MySession();
    private String encoding;

    public MyRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public HttpSession getSession() {
        return session;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return session;
    }

    @Override
    public String getCharacterEncoding() {
        return encoding;
    }

    @Override
    public void setCharacterEncoding(String enc) {
        encoding = enc;
    }
}
