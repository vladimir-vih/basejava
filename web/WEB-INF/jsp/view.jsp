<%@ page import="ru.javaops.basejava.webapp.web.HtmlHelper" %>
<%@ page import="ru.javaops.basejava.webapp.model.ContactType" %>
<%@ page import="ru.javaops.basejava.webapp.model.SectionType" %>
<%@ page import="ru.javaops.basejava.webapp.model.Section" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/one_resume_page.css">
    <title>Resume ${resume.fullName}</title>
</head>
<jsp:include page="/WEB-INF/fragments/header.html"/>
<body>

<h1>Resume of ${resume.fullName}&nbsp;
    <a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/trash_icon.png" alt="Delete" width="50px"></a>
    <a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit_icon.png" alt="Edit" width="50px"></a>
</h1>
<c:if test="${resume.contacts.size() > 0}">
    <section>
        <h2>Resume Contacts</h2>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javaops.basejava.webapp.model.ContactType,
                          java.lang.String>"/>
            <p style="font-size: 20px;"><img src="${HtmlHelper.getContactImgLink(contactEntry.key)}"
                                             width="50px" alt="Contact image"> ${contactEntry.getKey().getTitle()}:
                <c:choose>
                    <c:when test="${HtmlHelper.hasContactLink(contactEntry)}">
                        <a href="${HtmlHelper.getContactLink(contactEntry)}">${contactEntry.value}</a>
                    </c:when>
                    <c:when test="${!HtmlHelper.hasContactLink(contactEntry)}">
                        ${contactEntry.value}
                    </c:when>
                </c:choose>
            </p>
        </c:forEach>
    </section>
</c:if>
<dif>
    <c:if test="${resume.sections.size() > 0}">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<ru.javaops.basejava.webapp.model.SectionType,
        ru.javaops.basejava.webapp.model.Section>"/>
            <h2>${sectionEntry.getKey().getTitle()}</h2>
            <c:choose>
                <c:when test="${sectionEntry.getKey().getDisplayType().equals(\"string\")}">
                    <p>${sectionEntry.getValue().getBody()}</p>
                </c:when>
                <c:when test="${sectionEntry.getKey().getDisplayType().equals(\"list\")}">
                    <ul>
                        <c:forEach var="string" items="${sectionEntry.getValue().getBody()}">
                            <li>${string}<br/><br/></li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:when test="${sectionEntry.getKey().getDisplayType().equals(\"experience\")}">
                    <ol>
                        <c:forEach var="experience" items="${sectionEntry.getValue().getBody()}">
                            <jsp:useBean id="experience" type="ru.javaops.basejava.webapp.model.Experience"/>
                            <li>
                                <p>SINCE: ${experience.getStartDate()} TILL: ${HtmlHelper.getEndDateString(experience)}
                                </p>
                                <c:choose>
                                    <c:when test="${HtmlHelper.hasExperienceUrl(experience)}">
                                        <p><a href="${experience.company.url.url}">${experience.company.name}</a></p>
                                    </c:when>
                                    <c:when test="${!HtmlHelper.hasExperienceUrl(experience)}">
                                        <p>${experience.company.name}</p>
                                    </c:when>
                                </c:choose>
                                <p>${experience.shortInfo}</p>
                                <p>${experience.detailedInfo}</p>
                            </li>
                        </c:forEach>
                    </ol>
                </c:when>
            </c:choose>
        </c:forEach>
    </c:if>
</dif>
</body>
<jsp:include page="/WEB-INF/fragments/footer.html"/>
</html>
