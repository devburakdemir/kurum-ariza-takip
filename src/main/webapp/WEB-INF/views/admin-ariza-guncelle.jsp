<%--
  Created by IntelliJ IDEA.
  User: 34bur
  Date: 20.05.2025
  Time: 03:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Arıza Güncelle</title>
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
        <h2>Arıza Güncelle</h2>

        <form method="post" action="<c:url value='/ariza/guncelle'/>">
            <input type="hidden" name="id" value="${ariza.id}" />

            <div class="form-group">
                <label for="baslik">Başlık:</label>
                <input type="text" id="baslik" name="baslik" value="${ariza.baslik}" readonly />
            </div>

            <div class="form-group">
                <label for="durum">Durum:</label>
                <select id="durum" name="durum" required>
                    <option value="YENI" ${ariza.durum eq 'YENI' ? 'selected' : ''}>Yeni</option>
                    <option value="ISLEMDE" ${ariza.durum eq 'ISLEMDE' ? 'selected' : ''}>İşlemde</option>
                    <option value="COZULDU" ${ariza.durum eq 'COZULDU' ? 'selected' : ''}>Çözüldü</option>
                    <option value="REDDEDILDI" ${ariza.durum eq 'REDDEDILDI' ? 'selected' : ''}>Reddedildi</option>
                </select>
            </div>

            <div class="form-group">
                <label for="cevap">Yönetici Cevabı:</label>
                <textarea id="cevap" name="cevap" rows="5" placeholder="Cevabınızı girin...">${ariza.cevap}</textarea>
            </div>

            <button type="submit">Kaydet</button>
            <a href="<c:url value='/ariza/liste'/>">İptal Et / Geri Dön</a>
        </form>
    </main>
</div>
</body>
</html>
