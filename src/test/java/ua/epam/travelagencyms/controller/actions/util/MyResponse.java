package ua.epam.travelagencyms.controller.actions.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyResponse extends HttpServletResponseWrapper {
    private final List<Cookie> cookies = new ArrayList<>();
    private final ServletOutputStream servletOutputStream = new ServletOutputStream() {
        private final OutputStream outputStream = new ByteArrayOutputStream();

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {}

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
        }

        @Override
        public String toString() {
            return outputStream.toString();
        }
    };

    public MyResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return servletOutputStream;
    }

    @Override
    public void addCookie(Cookie cookie){
        cookies.add(cookie);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }
}