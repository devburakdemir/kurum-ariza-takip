<%--
  Created by IntelliJ IDEA.
  User: 34bur
  Date: 19.05.2025
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="app.title" text="Kurum Arıza Takip"/></title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
</head>
<body>
<div class="container">
    <header>
        <h1><spring:message code="app.title" text="Arıza Takip Sistemi"/></h1>
        <div class="user-info">
            <span><spring:message code="welcome"/> ${oturumdakiKullanici.adSoyad}!</span>
            <span><spring:message code="role.label"/> ${oturumdakiKullanici.rol}</span>
            <a href="<c:url value='/logout'/>"><spring:message code="menu.logout"/></a>
            <div class="language-switch">
                <a href="?lang=tr">Türkçe</a> | <a href="?lang=en">English</a>
            </div>
        </div>
    </header>
    <nav>
        <ul>
            <li><a href="<c:url value='/ariza/liste'/>">Arızalarım</a></li>
            <li><a href="<c:url value='/ariza/yeni'/>">Yeni Arıza Bildir</a></li>
        </ul>
    </nav>
</div>
</body>
</html>





