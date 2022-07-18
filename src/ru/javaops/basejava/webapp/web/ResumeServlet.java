package ru.javaops.basejava.webapp.web;

import ru.javaops.basejava.webapp.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", HtmlHelper.getAllResumes());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "delete":
                HtmlHelper.deleteResume(request.getParameter("uuid"));
                request.setAttribute("resumes", HtmlHelper.getAllResumes());
                request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
                break;
            case "view":
                request.setAttribute("resume", HtmlHelper.getResume(request.getParameter("uuid")));
                request.getRequestDispatcher("WEB-INF/jsp/view.jsp").forward(request, response);
            case "edit":
                Resume r;

                if (request.getParameterMap().containsKey("new_flag")) {
                    r = new Resume(HtmlHelper.getNewUUID());
                    HtmlHelper.saveResume(r);
                } else r = HtmlHelper.getResume(request.getParameter("uuid"));
                request.setAttribute("resume", r);
                request.getRequestDispatcher("WEB-INF/jsp/edit.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("full_name");
        Resume r = new Resume(uuid, fullName);
        for (ContactType type : ContactType.values()) {
            String contactValue = request.getParameter(type.name());
            if (contactValue != null && contactValue.trim().length() > 0) {
                r.addContact(type, contactValue);
            } else r.removeContact(type);
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE: {
                    String sectionbody = request.getParameter(type.name());
                    if (sectionbody != null && sectionbody.trim().length() > 0) {
                        r.addSection(type, new CharacteristicSection(sectionbody.trim()));
                    } else r.removeSection(type);
                    break;
                }
                case QUALIFICATIONS:
                case ACHIEVEMENT: {
                    String skillsString = request.getParameter(type.name());
                    if (skillsString != null && skillsString.trim().length() > 0) {
                        String[] skillsArr = skillsString.split("\r\n");
                        List<String> skillList = new ArrayList<>();
                        for (String s : skillsArr) {
                            if (s.trim().length() > 0) skillList.add(s);
                        }
                        r.addSection(type, new SkillsSection(skillList));
                    } else r.removeSection(type);
                    break;
                }
                case EDUCATION:
                case EXPERIENCE:
                    Section<Experience> oldSection = (Section<Experience>) HtmlHelper.getResume(uuid).getSection(type);
                    if (oldSection != null) r.addSection(type, oldSection);
                    break;
            }
        }
        HtmlHelper.updateResume(r);
        response.sendRedirect("resume");
    }
}
