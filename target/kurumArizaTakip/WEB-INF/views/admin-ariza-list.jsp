<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="fault.list.title" text="Tüm Arızalar"/></title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
</head>
<body>
<div class="container">
    <header>
        <h1><spring:message code="app.title" text="Arıza Takip Sistemi"/></h1>
        <div class="user-info">
            <span>${oturumdakiKullanici.adSoyad} (Admin)</span>
            <a href="<c:url value='/logout'/>">Çıkış Yap</a>
            <div class="language-switch">
                <a href="?lang=tr">Türkçe</a> | <a href="?lang=en">English</a>
            </div>
        </div>
    </header>

    <nav>
        <ul>
            <li><a href="<c:url value='/admin/anasayfa'/>">Admin Anasayfa</a></li>
            <li><a href="<c:url value='/ariza/liste'/>">Tüm Arızalar</a></li>
        </ul>
    </nav>

    <main>
        <!-- 🔍 Arama formu -->
        <form method="get" action="<c:url value='/ariza/liste'/>" style="margin-bottom: 20px;">
            <input type="text" name="arama" placeholder="Arıza başlığına göre ara..." value="${param.arama}" />
            <button type="submit">Ara</button>
        </form>

        <h2><spring:message code="fault.list.title" text="Tüm Arızalar"/></h2>

        <c:if test="${not empty success}">
            <div class="success-message">${success}</div>
        </c:if>

        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Başlık</th>
                <th>Durum</th>
                <th>Tarih</th>
                <th>İşlem</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${arizalar}" var="ariza">
                <tr>
                    <td>${ariza.id}</td>
                    <td>${ariza.baslik}</td>
                    <td>${ariza.durum}</td>
                    <td><fmt:formatDate value="${ariza.olusturmaTarihi}" pattern="dd.MM.yyyy HH:mm"/></td>
                    <td>
                        <a href="<c:url value='/ariza/guncelle/${ariza.id}'/>">
                            <button type="button">Güncelle</button>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </main>
</div>
</body>
</html>
