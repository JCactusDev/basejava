<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.github.jcactusdev.model.Resume" %>
<%@ page import="com.github.jcactusdev.model.TextSection" %>
<%@ page import="com.github.jcactusdev.model.ListSection" %>
<%@ page import="com.github.jcactusdev.model.OrganizationSection" %>
<%@ page import="com.github.jcactusdev.util.HtmlUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="css/style.css">
  <title>View</title>
</head>
<body>
<div class="wrapper">
  <jsp:include page="fragments/header.jsp"></jsp:include>
  <jsp:useBean id="resume" type="com.github.jcactusdev.model.Resume" scope="request" />
  <main>
    <h1>View (<a href="resume?uuid=${resume.uuid}&action=edit">EDIT</a>)</h1>
    <div class="resume-container">
      <fieldset>
        <legend>Персональные данные:</legend>
        <label for="uuid">UUID: </label>
        <p id="uuid">${resume.uuid}</p>
        <label for="name">Полное имя: </label>
        <p id="name">${resume.fullName}</p>
      </fieldset>
      <fieldset>
        <legend>Контакты:</legend>
      <c:forEach items="${resume.contacts}" var="contactEntry">
        <jsp:useBean id="contactEntry" type="java.util.Map.Entry<com.github.jcactusdev.model.ContactType, java.lang.String>" />
        <label for="contactEntry-${contactEntry.key.name()}">${contactEntry.key.title}:</label>
        <p id="contactEntry-${contactEntry.key.name()}">${contactEntry.value}</p>
      </c:forEach>
      </fieldset>

      <fieldset>
        <legend>Содержание:</legend>
        <c:forEach items="${resume.sections}" var="sectionEntry">
          <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.github.jcactusdev.model.SectionType, com.github.jcactusdev.model.Section>" />
          <c:set var="type" value="${sectionEntry.key}"></c:set>
          <c:set var="section" value="${sectionEntry.value}"></c:set>
          <jsp:useBean id="section" type="com.github.jcactusdev.model.Section"/>
          <h4><label for="sectionEntry-${type}">${type.title}:</label></h4>
          <c:choose>
            <c:when test="${type=='OBJECTIVE'}">
              <p id="sectionEntry-${type}" >
                <%=((TextSection) section).getContent()%>
              </p>
            </c:when>
            <c:when test="${type=='PERSONAL'}">
              <p id="sectionEntry-${type}">
                <%=((TextSection) section).getContent()%>
              </p>
            </c:when>
            <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
              <ul id="sectionEntry-${type}">
                <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                  <li>${item}</li>
                </c:forEach>
              </ul>
            </c:when>
            <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
              <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>">
                <h5>
                  <c:choose>
                    <c:when test="${empty organization.homePage.url}">
                      ${organization.homePage.name}
                    </c:when>
                    <c:otherwise>
                      <a href="${organization.homePage.url}">${organization.homePage.name}</a>
                    </c:otherwise>
                  </c:choose>
                  <c:forEach var="position" items="${organization.positions}">
                    <jsp:useBean id="position" type="com.github.jcactusdev.model.Organization.Position"/>
                    <div class="position-date"><%=HtmlUtil.formatDates(position)%></div>
                    <div id="position-title">${position.title}</div>
                  </c:forEach>
                </h5>
              </c:forEach>
            </c:when>
          </c:choose>
          <br>
        </c:forEach>
      </fieldset>
      <form method="post" action="resume?uuid=${resume.uuid}&action=delete">
        <input type="submit" value="Удалить">
      </form>
    </div>
  </main>
  <jsp:include page="fragments/footer.jsp"></jsp:include>
</div>
</body>
</html>
