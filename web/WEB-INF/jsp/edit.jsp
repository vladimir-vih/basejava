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

    /*li {
        list-style-type: none;
        width: 100px;
        overflow-x: auto; !* change to hidden if that's what you want *!
        float: left;
        margin-right: 10px;
    }*/

</style>
<head>
    <link rel="stylesheet" href="css/one_resume_page.css">
    <title>Resume ${resume.fullName}</title>
</head>
<jsp:include page="/WEB-INF/fragments/header.html"/>
<body>
<h1>Resume of ${resume.fullName}</h1>
<a href="resume?uuid=${resume.uuid}&action=delete">Delete resume</a>

<div id="readroot" style="display: none">

    <input type="button" value="Remove Experience"
           onclick="this.parentNode.parentNode.removeChild(this.parentNode);"/><br/><br/>
    <label>
        <input type="text" size="6" name="${type}StartDate" required minlength="10" maxlength="10" value="YYYY-MM-DD">
        Since YYYY-MM-DD:
    </label>
    <br/>
    <label>
        <input type="text" size="6" name="${type}EndDate" required minlength="3" maxlength="10" value="YYYY-MM-DD">
        Till YYYY-MM-DD
        (write "<b>NOW</b>" if it's current position):
    </label>
    <br/><br/>
    <label>
        <input type="text" size="30" name="${type}CompanyName" required value="Company Name">
        Company Name:
    </label>
    <br/><br/>
    <label>
        <input type="text" size="75" name="${type}CompanyUrl" value="http://company.com">
        Company URL:
    </label>
    <br/><br/>
    <label>
        <input type="text" size="30" name="${type}ShortInfo" required value="Your position">
        Position name:
    </label>
    <br/><br/>
    <c:if test="${type.equals(SectionType.EXPERIENCE)}">
        <label>
            <textarea rows="30" name="${type}DetailedInfo">Your detailed Information</textarea>
            Detailed information:
        </label>
        <br/><br/>
    </c:if>
</div>

<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <dl>
        <dt>Full Name<br/><br/>
        </dt>

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
            <dt>${type.getTitle()}</dt>
            <dd>
                <c:choose>
                    <c:when test="${type.equals(SectionType.PERSONAL) || type.equals(SectionType.OBJECTIVE)}">
                        <textarea name="${type.name()}" rows="10">${resume.getSection(type).getBody()}</textarea>
                        <br/><br/>
                    </c:when>
                    <c:when test="${type.equals(SectionType.QUALIFICATIONS) || type.equals(SectionType.ACHIEVEMENT)}">
                        <textarea name="${type.name()}" rows="30"><c:forEach var="skill"
                                                                             items="${resume.getSection(type).getBody()}"
                        >${skill}&#10;</c:forEach></textarea><br/><br/>
                    </c:when>
                    <c:when test="${type.equals(SectionType.EXPERIENCE) || type.equals(SectionType.EDUCATION)}">
                        <ol>
                            <c:forEach var="experience" items="${resume.getSection(type).getBody()}">
                                <jsp:useBean id="experience" type="ru.javaops.basejava.webapp.model.Experience"/>
                                <div class="line_border">
                                    <li>
                                        <label>
                                            Since YYYY-MM-DD:
                                            <input type="text" size="6" name="${type}StartDate" required
                                                   minlength="10" maxlength="10"
                                                   value="${experience.getStartDate()}">
                                        </label>
                                        <br/>
                                        <label>
                                            Till YYYY-MM-DD
                                            (write "<b>NOW</b>" if it's current position):
                                            <input type="text" size="6" name="${type}EndDate" required
                                                   minlength="3" maxlength="10"
                                                   value="${HtmlHelper.getEndDateString(experience)}">
                                        </label>
                                        <br/><br/>
                                        <label>
                                            Company Name:
                                            <input type="text" size="30" name="${type}CompanyName" required
                                                   value="${experience.company.name}">
                                        </label>
                                        <br/><br/>
                                        <label>
                                            Company URL:
                                            <input type="text" size="75" name="${type}CompanyUrl"
                                                   value="${experience.company.url.url}">
                                        </label>
                                        <br/><br/>
                                        <label>
                                            Position name:
                                            <input type="text" size="30" name="${type}ShortInfo" required
                                                   value="${experience.shortInfo}">
                                        </label>
                                        <br/><br/>
                                        <c:if test="${type.equals(SectionType.EXPERIENCE)}">
                                            <label>
                                                Detailed information:
                                                <textarea rows="30" name="${type}DetailedInfo">
                                                        ${experience.detailedInfo}</textarea>
                                            </label>
                                            <br/><br/>
                                        </c:if>
                                        <label>Write word "delete", if you want to delete this element
                                            <input type="text" size="6" name="${type}deleted">
                                        </label><br/>
                                    </li>
                                </div>
                                <br/><br/>
                            </c:forEach>
                            <div class="line_border">
                                <li> New element<br/><br/>
                                    <label style="color: red;">Use ckeckbox,
                                        if you need new element of ${type.title}!
                                        <input type="checkbox" name="new_${type}" value="true"><br/><br/>
                                    </label>

                                    <label>
                                        Since YYYY-MM-DD:
                                        <input type="text" size="6" name="new_${type}StartDate" required
                                               minlength="10" maxlength="10"
                                               value="YYYY-MM-DD">
                                    </label>
                                    <br/>
                                    <label>
                                        Till YYYY-MM-DD
                                        (write "<b>NOW</b>" if it's current position):
                                        <input type="text" size="6" name="new_${type}EndDate" required
                                               minlength="3" maxlength="10"
                                               value="YYYY-MM-DD or NOW">
                                    </label>
                                    <br/><br/>
                                    <label>
                                        Company Name:
                                        <input type="text" size="30" name="new_${type}CompanyName" required
                                               value="Company Name">
                                    </label>
                                    <br/><br/>
                                    <label>
                                        Company URL:
                                        <input type="text" size="75" name="new_${type}CompanyUrl"
                                               value="Company URL">
                                    </label>
                                    <br/><br/>
                                    <label>
                                        Position name:
                                        <input type="text" size="30" name="new_${type}ShortInfo" required
                                               value="Your position description">
                                    </label>
                                    <br/><br/>
                                    <c:if test="${type.equals(SectionType.EXPERIENCE)}">
                                        <label>
                                            Detailed information:
                                            <textarea rows="30" name="new_${type}DetailedInfo">Detailed information
                                                    </textarea>
                                        </label>
                                        <br/><br/>
                                    </c:if>
                                </li>
                            </div>
                        </ol>
                    </c:when>
                </c:choose>
            </dd>
        </c:forEach>
    </dl>
    <button type="submit">Save</button>
    <button onclick="window.history.back();return false;">Cancel</button>
</form>
</body>
<jsp:include page="/WEB-INF/fragments/footer.html"/>
</html>
