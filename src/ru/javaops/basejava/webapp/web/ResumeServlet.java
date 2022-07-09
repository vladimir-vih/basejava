package ru.javaops.basejava.webapp.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        final String name = request.getParameter("name");
//        if (name == null) {
//            response.getWriter().write("Hello all");
//        } else {
//            response.getWriter().write("Hello " + name);
//        }

        response.getWriter().write(ServletHelper.getHtmlAllResume());

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
