<%--
  Created by IntelliJ IDEA.
  User: 34bur
  Date: 20.05.2025
  Time: 01:40
  To change this template use File | Settings | File Templates.
--%>
<!-- admin-anasayfa.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Paneli</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />">
</head>
<body>
<div class="container">
    <header>
        <h1>Yönetici Paneli</h1>
        <div class="user-info">
            <span>${oturumdakiKullanici.adSoyad} (${oturumdakiKullanici.rol})</span>
            <a href="<c:url value='/logout'/>">Çıkış Yap</a>
        </div>
    </header>
    <nav>
        <ul>
            <li><a href="<c:url value='/ariza/liste'/>">Tüm Arızalar</a></li>
        </ul>
    </nav>
</div>
</body>
</html>

