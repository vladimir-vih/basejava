package ru.javaops.basejava.webapp.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String uuid = request.getParameter("uuid");
        if (uuid == null) {
            response.getWriter().write(ServletHelper.getHtmlAllResume());
        } else {
            response.getWriter().write(ServletHelper.getHtmlResume(uuid));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
