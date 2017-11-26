<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>Autentification</title>
</head>

<body>
<div>
    <h1>Hi man!!</h1>
    <p>Temperature in ${city}: ${temperature} degrees</p>
    <p>listening to ${favoriteSinger}: ${sizeListening} ${nameArtist}<br><br><br></p>

</div>
<div>
    <c:choose>
        <c:when test="${fn:length(artists) gt 0}">
            <c:forEach items="${artists}" var="artist">
                <p>listening to ${artist.name}: ${artist.sizeListener}</p>
                <img src="<c:url value="${artist.img}"/>" alt="Картинки нет!">
            </c:forEach>
        </c:when>
    </c:choose>
</div>
<%--<div>--%>
    <%--<form method="post">--%>
        <%--<input type="submit" name="Catalog" value="Catalog">--%>
    <%--</form>--%>
<%--</div>--%>

<%--<div>--%>
    <%--<form method="post">--%>
        <%--<input type="submit" name="basket" value="Корзина">--%>
    <%--</form>--%>
<%--</div>--%>

</body>
</html>
