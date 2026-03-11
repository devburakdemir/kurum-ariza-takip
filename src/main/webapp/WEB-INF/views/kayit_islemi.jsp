<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="register.title" text="Kayıt Ol"/></title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />">
</head>
<body>
<div class="login-container">
    <h1>Kayıt Ol</h1>
    <form action="<c:url value='/kayit'/>" method="post">
        <div class="form-group">
            <label for="kullaniciAdi">Kullanıcı Adı:</label>
            <input type="text" id="kullaniciAdi" name="kullaniciAdi" value="${kullanici.kullaniciAdi}" required>
        </div>
        <div class="form-group">
            <label for="adSoyad">Ad Soyad:</label>
            <input type="text" id="adSoyad" name="adSoyad" value="${kullanici.adSoyad}" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${kullanici.email}" required>
        </div>
        <div class="form-group">
            <label for="sifre">Şifre:</label>
            <input type="password" id="sifre" name="sifre" required>
        </div>
        <div class="form-group">
            <label for="sifreTekrar">Şifre (Tekrar):</label>
            <input type="password" id="sifreTekrar" name="sifreTekrar" required>
        </div>


        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <button type="submit">Kayıt Ol</button>
    </form>
    <div class="form-group">
        <a href="login">Zaten bir hesabınız var mı? Giriş yap</a>
    </div>
</div>
</body>
</html>
