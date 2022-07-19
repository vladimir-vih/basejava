<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javaops.basejava.webapp.web.HtmlHelper" %>
<%@ page import="ru.javaops.basejava.webapp.model.ContactType" %>
<%@ page import="ru.javaops.basejava.webapp.model.SectionType" %>
<%@ page import="ru.javaops.basejava.webapp.model.Section" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    dt {
        font-size: 20px;
    }

    input {
        background-color: #989699;
    }

    textarea {
        background-color: #989699;
        width: 500px;
        height: 100px;
    }

</style>
<head>
    <link rel="stylesheet" href="css/one_resume_page.css">
    <title>Resume ${resume.fullName}</title>
</head>
<jsp:include page="/WEB-INF/fragments/header.html"/>
<body>
<h1>Resume of ${resume.fullName}</h1>
<a href="resume?uuid=${resume.uuid}&action=delete">Delete resume</a>
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <dl>
        <dt>Full Name<br/><br/></dt>
        <dd>
            Full Name: <input type="text" name="full_name" value="${resume.fullName}"
                              required placeholder="Full Name required">
        </dd>
    </dl>
    <dl>
        <dt>Contacts<br/><br/></dt>
        <c:forEach var="type" items="${ContactType.values()}">
            <dd>
                <img src="${HtmlHelper.getContactImgLink(type)}" width="40px">
                    ${type.name()} <input type="text" name="${type.name()}"
                                          value="${resume.getContact(type)}">
            </dd>
        </c:forEach>
    </dl>
    <dl>
        <c:forEach var="type" items="${SectionType.values()}">
            <dt>${type.name()}</dt>
            <dd>
                <c:choose>
                    <c:when test="${type.equals(SectionType.PERSONAL) || type.equals(SectionType.OBJECTIVE)}">
                        <textarea name="${type.name()}" rows="10">${resume.getSection(type).getBody()}</textarea>
                    </c:when>
                    <c:when test="${type.equals(SectionType.QUALIFICATIONS) || type.equals(SectionType.ACHIEVEMENT)}">
                        <textarea name="${type.name()}" rows="30"><c:forEach var="skill"
                                                                             items="${resume.getSection(type).getBody()}"
                        >${skill}&#10;</c:forEach></textarea>
                    </c:when>
                    <c:when test="${type.equals(SectionType.EXPERIENCE) || type.equals(SectionType.EDUCATION)}">
                        <c:forEach var="experience" items="${resume.getSection(type).getBody()}">
                            <jsp:useBean id="experience" type="ru.javaops.basejava.webapp.model.Experience"/>
                            <li>
                                <p>SINCE: ${experience.getStartDate()} TILL
                                    <c:choose>
                                        <c:when test="${HtmlHelper.isCurrentPosition(experience)}">
                                            NOW
                                        </c:when>
                                        <c:when test="${!HtmlHelper.isCurrentPosition(experience)}">
                                            : ${experience.getEndDate()}
                                        </c:when>
                                    </c:choose>
                                </p>
                                <c:choose>
                                    <c:when test="${HtmlHelper.hasExperienceUrl(experience)}">
                                        <p><a href="${experience.company.url.url}">${experience.company.name}</a>
                                        </p>
                                    </c:when>
                                    <c:when test="${!HtmlHelper.hasExperienceUrl(experience)}">
                                        <p>${experience.company.name}</p>
                                    </c:when>
                                </c:choose>
                                <p>${experience.shortInfo}</p>
                                <p>${experience.detailedInfo}</p>
                            </li>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </dd>
        </c:forEach>
    </dl>
    <button type="submit">Save</button>
    <button onclick="window.history.back();return false;">Cancel</button>
</form>
</body>
<jsp:include page="/WEB-INF/fragments/footer.html"></jsp:include>
</html>
