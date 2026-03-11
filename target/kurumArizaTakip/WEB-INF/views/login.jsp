<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="login.title"/></title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />">
</head>
<body>
<div class="login-container">
    <h1><spring:message code="app.title"/></h1>
    <form action="<c:url value='/login'/>" method="post">

        <!--admin girişi-->
        <div class="form-group">
            <label for="rol">Giriş Tipi:</label>
            <select name="rol" id="rol" required>
                <option value="USER">Kullanıcı</option>
                <option value="ADMIN">Yönetici</option>
            </select>
        </div>

        <div class="form-group">
            <label for="kullaniciAdi"><spring:message code="login.username"/></label>
            <input type="text" id="kullaniciAdi" name="kullaniciAdi" required>
        </div>
        <div class="form-group">
            <label for="sifre"><spring:message code="login.password"/></label>
            <input type="password" id="sifre" name="sifre" required>
        </div>
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        <button type="submit"><spring:message code="login.button"/></button>
    </form>
    <div class="language-switch">
        <a href="?lang=tr">Türkçe</a> | <a href="?lang=en">English</a>
        <p>
            <a href="kayit">Hesabınız yok mu? Kayıt olmak için buraya tıklayınız.</a>
        </p>

    </div>

</div>
</body>
</html>