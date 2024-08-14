<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.github.jcactusdev.model.*" %>
<%@ page import="com.github.jcactusdev.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Edit</title>
</head>
<body>
<div class="wrapper">
    <jsp:include page="fragments/header.jsp"></jsp:include>
    <main>
        <h1>Edit</h1>
        <div class="resume-container">
            <jsp:useBean id="resume" type="com.github.jcactusdev.model.Resume" scope="request" />
            <form method="post" action="resume?uuid=${resume.uuid}&action=${action}">
                <input type="text" id="uuid" name="uuid" value="${resume.uuid}" readonly hidden="hidden">
                <fieldset class="field-personal">
                    <legend>Персональные данные:</legend>
                    <label for="name">Полное имя: </label>
                    <input type="text" id="name" name="fullName" value="${resume.fullName}">
                </fieldset>
                <fieldset class="field-contacts">
                    <legend>Контакты:</legend>
                    <c:forEach var="type" items="<%=ContactType.values()%>">
                        <label for="contactEntry-${type.name()}">${type.title}</label>
                        <input type="text" id="contactEntry-${type.name()}" name="${type.name()}" value="${resume.getContact(type)}">
                        <br>
                    </c:forEach>
                </fieldset>
                <fieldset class="field-sections">
                    <legend>Содержание:</legend>
                    <c:forEach var="type" items="<%=SectionType.values()%>">
                        <c:set var="section" value="${resume.getSection(type)}" />
                        <jsp:useBean id="section" type="com.github.jcactusdev.model.Section"/>
                        <h4><label for="sectionEntry-${type}">${type.title}:</label></h4>
                        <c:choose>
                            <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                                <textarea class="field" name='${type}'><%=section%></textarea>
                            </c:when>
                            <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                                <textarea class="field" name='${type}'><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                            </c:when>
                            <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                                <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>" varStatus="counter">
                                    <c:choose>
                                        <c:when test="${counter.index == 0}">
                                        </c:when>
                                        <c:otherwise>
                                            *
                                        </c:otherwise>
                                    </c:choose>

                                    <input class="field" type="text" placeholder="Название" name="${type}" size="100" value="${organization.homePage.name}">
                                    <input class="field" type="text" placeholder="Ссылка" name="${type}url" size="100" value="${organization.homePage.url}">

                                    <c:forEach var="position" items="${organization.positions}">
                                        <jsp:useBean id="position" type="com.github.jcactusdev.model.Organization.Position"/>
                                        <fieldset>
                                            <input class="field date" name="${type}${counter.index}startDate" placeholder="Начало, ММ/ГГГГ" size=10 value="<%=DateUtil.format(position.getStartDate())%>">
                                            <br>
                                            <input class="field date date-margin" name="${type}${counter.index}endDate" placeholder="Окончание, ММ/ГГГГ" size=10 value="<%=DateUtil.format(position.getEndDate())%>">
                                            <br>
                                            <input class="field" type="text" placeholder="Заголовок" name='${type}${counter.index}title' size=75 value="${position.title}">
                                            <br>
                                            <textarea class="field" placeholder="Описание" name="${type}${counter.index}description">${position.description}</textarea>
                                        </fieldset>
                                    </c:forEach>

                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </fieldset>
                <hr>
                <button type="submit">Сохранить</button>
            </form>
            <form method="post" action="resume?uuid=${resume.uuid}&action=delete">
                <input type="submit" value="Удалить">
            </form>
        </div>
    </main>
    <jsp:include page="fragments/footer.jsp"></jsp:include>
</div>
</body>
</html>
