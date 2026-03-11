<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${ariza.id == null}">
                <spring:message code="fault.new.title"/>
            </c:when>
            <c:otherwise>
                <spring:message code="fault.detail.title"/>
            </c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
</head>
<body>
<div class="container">
    <header>
        <h1><spring:message code="app.title"/></h1>
        <div class="user-info">
            <span>${oturumdakiKullanici.adSoyad}</span>
            <a href="<c:url value='/logout'/>"><spring:message code="menu.logout"/></a>
            <div class="language-switch">
                <a href="?lang=tr">Türkçe</a> | <a href="?lang=en">English</a>
            </div>
        </div>
    </header>

    <nav>
        <ul>
            <c:choose>
                <c:when test="${oturumdakiKullanici.rol eq 'ADMIN'}">
                    <li><a href="<c:url value='/admin/anasayfa'/>">Admin Ana Sayfa</a></li>
                    <li><a href="<c:url value='/ariza/liste'/>">Tüm Arızalar</a></li>
                    <li><a href="<c:url value='/ariza/yeni'/>">Yeni Arıza Ekle</a></li>
                    <!-- Diğer admin özel butonları -->
                </c:when>
                <c:otherwise>
                    <li><a href="<c:url value='/anasayfa'/>">Ana Sayfa</a></li>
                    <li><a href="<c:url value='/ariza/liste'/>">Arızalarım</a></li>
                    <li><a href="<c:url value='/ariza/yeni'/>">Yeni Arıza Bildir</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>


    <main>
        <h2>
            <c:choose>
                <c:when test="${ariza.id == null}">
                    <spring:message code="fault.new.title"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="fault.detail.title"/>
                </c:otherwise>
            </c:choose>
        </h2>

        <c:set var="formAction" value="${ariza.id == null ? '/ariza/kaydet' : '/ariza/guncelle'}"/>
        <form action="<c:url value='${formAction}'/>" method="post">


        <input type="hidden" name="id" value="${ariza.id}">

            <div class="form-group">
                <label for="baslik"><spring:message code="fault.title"/></label>
                <input type="text" id="baslik" name="baslik" value="${ariza.baslik}" required>
            </div>

            <div class="form-group">
                <label for="aciklama"><spring:message code="fault.description"/></label>
                <textarea id="aciklama" name="aciklama" rows="5" required>${ariza.aciklama}</textarea>
            </div>

            <c:if test="${isAdmin}">
                <div class="form-group">
                    <label for="durum"><spring:message code="fault.status"/></label>
                    <select id="durum" name="durum">
                        <option value="YENI" ${ariza.durum eq 'YENI' ? 'selected' : ''}><spring:message code="fault.status.new"/></option>
                        <option value="ISLEMDE" ${ariza.durum eq 'ISLEMDE' ? 'selected' : ''}><spring:message code="fault.status.inprogress"/></option>
                        <option value="COZULDU" ${ariza.durum eq 'COZULDU' ? 'selected' : ''}><spring:message code="fault.status.resolved"/></option>
                        <option value="REDDEDILDI" ${ariza.durum eq 'REDDEDILDI' ? 'selected' : ''}><spring:message code="fault.status.rejected"/></option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="cevap"><spring:message code="fault.response"/></label>
                    <textarea id="cevap" name="cevap" rows="3">${ariza.cevap}</textarea>
                </div>
            </c:if>

            <button type="submit">
                <c:choose>
                    <c:when test="${ariza.id == null}">
                        <spring:message code="fault.save"/>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="fault.update"/>
                    </c:otherwise>
                </c:choose>
            </button>
        </form>
    </main>
</div>
</body>
</html>