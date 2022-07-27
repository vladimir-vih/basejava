package ru.javaops.basejava.webapp.web;

import ru.javaops.basejava.webapp.exception.IncorrectDateFormat;
import ru.javaops.basejava.webapp.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
                    try {
                        List<Experience> finalList = getExperienceList(request, type.name());
                        /*List<Experience> newExperience = null;
                        if (request.getParameterMap().containsKey("new_" + type.name())
                                && request.getParameter("new_" + type.name()).equals("true")) {
                            newExperience = getExperienceList(request, "new_" + type.name());
                        }
                        if (newExperience != null) finalList.addAll(newExperience);*/
                        r.addSection(type, new ExperienceSection(finalList));
                    } catch (IncorrectDateFormat e) {
                        request.setAttribute("uuid", uuid);
                        request.setAttribute("company_name", e.companyName);
                        request.getRequestDispatcher("WEB-INF/jsp/incorrect_date_format.jsp")
                                .forward(request, response);
                        return;
                    }
                    break;
            }
        }
        HtmlHelper.updateResume(r);
        response.sendRedirect("resume");
    }

    private List<Experience> getExperienceList(HttpServletRequest request, String prefix) throws IncorrectDateFormat {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Experience> experienceList = new ArrayList<>();
        if (!request.getParameterMap().containsKey(prefix + "StartDate")) {
            return experienceList;
        }
        String[] startDatesArr = request.getParameterValues(prefix + "StartDate");
        final int sectionSize = startDatesArr.length;
        String[] deletedArr = request.getParameterValues(prefix + "deleted");
        String[] endDatesArr = request.getParameterValues(prefix + "EndDate");
        String[] companyNameArr = request.getParameterValues(prefix + "CompanyName");
        String[] companyUrlArr = request.getParameterValues(prefix + "CompanyUrl");
        String[] shortInfoArr = request.getParameterValues(prefix + "ShortInfo");
        String[] detailedInfoArr = request.getParameterValues(prefix + "DetailedInfo");
        for (int i = 0; i < sectionSize; i++) {
            if (deletedArr[i].equals("delete")) {
                continue;
            }
            if ((startDatesArr[i] == null || startDatesArr[i].trim().length() == 0)
                    && (endDatesArr[i] == null || endDatesArr[i].trim().length() == 0)
                    && (companyNameArr[i] == null || companyNameArr[i].trim().length() == 0)
                    && (companyUrlArr[i] == null || companyUrlArr[i].trim().length() == 0)
                    && (shortInfoArr[i] == null || shortInfoArr[i].trim().length() == 0)) continue;
            final String companyName = companyNameArr[i];
            Link companyUrl = null;
            String companyUrlString = companyUrlArr[i];
            if (companyUrlString != null && companyUrlString.trim().length() > 0) {
                companyUrl = new Link(companyName, companyUrlString);
            }
            final Company company = new Company(companyName, companyUrl);
            LocalDate startDate;
            LocalDate endDate;
            try {
                startDate = LocalDate.parse(startDatesArr[i], formatter);
                endDate = endDatesArr[i].equals("NOW") ?
                        LocalDate.MAX : LocalDate.parse(endDatesArr[i], formatter);
            } catch (DateTimeParseException e) {
                throw new IncorrectDateFormat(e, companyName);
            }

            final String detailedInfo = detailedInfoArr == null ? null : detailedInfoArr[i];
            experienceList.add(new Experience(company, startDate, endDate, shortInfoArr[i], detailedInfo));
        }
        return experienceList;
    }
}
