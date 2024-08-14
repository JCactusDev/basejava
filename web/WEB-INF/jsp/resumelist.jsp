<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.github.jcactusdev.model.Resume" %>
<%@ page import="com.github.jcactusdev.model.ContactType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>List</title>
</head>
<body>
<div class="wrapper">
    <jsp:include page="fragments/header.jsp"></jsp:include>
    <main>
        <h1>List:</h1>
        <a href="resume?action=add">Создать резюме</a>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>Имя</th>
                <th>Email</th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="resumes" type="java.util.List" scope="request" />
            <c:forEach items="${resumes}" var="resume">
                <jsp:useBean id="resume" type="com.github.jcactusdev.model.Resume"/>
                <tr>
                    <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                    <td>${resume.getContact(ContactType.MAIL)}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </main>
    <jsp:include page="fragments/footer.jsp"></jsp:include>
</div>
</body>
</html>
