<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="fault.list.title"/></title>
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
        <c:if test="${not empty success}">
            <div class="success-message">${success}</div>
        </c:if>

        <h2><spring:message code="fault.list.title"/></h2>
        <table>
            <thead>
            <tr>
                <th><spring:message code="fault.id"/></th>
                <th><spring:message code="fault.title"/></th>
                <th><spring:message code="fault.status"/></th>
                <th><spring:message code="fault.date"/></th>
                <th><spring:message code="fault.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${arizalar}" var="ariza">
                <tr>
                    <td>${ariza.id}</td>
                    <td>${ariza.baslik}</td>
                    <td>
                        <c:choose>
                            <c:when test="${ariza.durum eq 'YENI'}">
                                <spring:message code="fault.status.new"/>
                            </c:when>
                            <c:when test="${ariza.durum eq 'ISLEMDE'}">
                                <spring:message code="fault.status.inprogress"/>
                            </c:when>
                            <c:when test="${ariza.durum eq 'COZULDU'}">
                                <spring:message code="fault.status.resolved"/>
                            </c:when>
                            <c:when test="${ariza.durum eq 'REDDEDILDI'}">
                                <spring:message code="fault.status.rejected"/>
                            </c:when>
                            <c:otherwise>
                                ${ariza.durum}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><fmt:formatDate value="${ariza.olusturmaTarihi}" pattern="dd.MM.yyyy HH:mm"/></td>
                    <td>
                        <a href="<c:url value='/ariza/duzenle/${ariza.id}'/>"><spring:message code="fault.edit"/></a>
                        <a href="<c:url value='/ariza/sil/${ariza.id}'/>" onclick="return confirm('<spring:message code="fault.delete.confirm"/>')">
                            <spring:message code="fault.delete"/>
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
