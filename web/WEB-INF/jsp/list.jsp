<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javaops.basejava.webapp.model.ContactType" %>
<%@ page import="ru.javaops.basejava.webapp.web.HtmlHelper" %>
<html>
<style>
    button {
        display: block;
        margin: 0 auto;
        background-color: #989699;
    }
</style>
<head>
    <link rel="stylesheet" href="css/resume_list.css">
    <title>Resume List</title>
</head>
<jsp:include page="/WEB-INF/fragments/header.html"/>
<body>
<form action="resume" method="get">
    <input type="hidden" name="action" value="edit">
    <input type="hidden" name="new_flag" value="true">
    <button type="submit">CREATE NEW RESUME</button>
</form>
<h1>All Resume list</h1>
<section>
    <table>
        <tr>
            <th>Full Name</th>
            <th>MAIL</th>
            <th>Remove</th>
            <th>Edit</th>
        </tr>
        <c:forEach var="resume" items="${resumes}">
            <tr>
                <jsp:useBean id="resume" type="ru.javaops.basejava.webapp.model.Resume"/>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <c:set var="mail" value="${resume.getContact(ContactType.MAIL)}"/>
                <c:choose>
                    <c:when test="${mail == null}">
                        <td>no mail</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="mailto:${mail}">${mail}</a></td>
                    </c:otherwise>
                </c:choose>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/trash_icon.png"
                                                                            alt="Delete" width="50px"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit_icon.png"
                                                                          alt="Edit" width="50px"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
<jsp:include page="/WEB-INF/fragments/footer.html"/>
</html>
