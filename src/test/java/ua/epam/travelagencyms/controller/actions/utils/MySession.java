package ua.epam.travelagencyms.controller.actions.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MySession implements HttpSession {
    private final Map<String, Object> attributes = new HashMap<>();

    public MySession(){}

    @Override
    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
    }

    @Override
    public void putValue(String s, Object o) {

    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public Object getValue(String s) {
        return null;
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public void removeValue(String s) {

    }

    @Override
    public void invalidate() {}
    @Override
    public boolean isNew() {
        return false;
    }
    @Override
    public long getCreationTime() {
        return 0;
    }
    @Override
    public String getId() {
        return null;
    }
    @Override
    public long getLastAccessedTime() {
        return 0;
    }
    @Override
    public ServletContext getServletContext() {
        return null;
    }
    @Override
    public void setMaxInactiveInterval(int i) {}
    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }
}