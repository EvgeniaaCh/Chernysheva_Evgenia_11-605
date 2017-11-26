<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Catalog</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/basis.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/mainPage.css"/>">
</head>
<body>
<jsp:include page="base.jsp"/>
<div class="window">
    <div class="container">
        <div class="products">
            <div class="info">
                <h3 class="new-models">${type}</h3>
                <ul class=items_block" id=items">
                    <c:choose>
                        <c:when test="${fn:length(catalog_goods) gt 0}">
                            <c:forEach items="${catalog_goods}" var="catalog_goods">
                                <li class="item_img">
                                    <div class="column">
                                        <form
                                         action="/info" method="get">
                                            <a href="/info?catalogId=${catalog_goods.catalogId}"><img
                                                    src="<c:url value="/images/${catalog_goods.img}"/>"
                                                    class="img-responsive" alt=""></a>
                                        </form>
                                        <div class="good-name">
                                            <h4 name="name_good">${catalog_goods.name}</h4>
                                            <p name="price">${catalog_goods.price} рублей</p>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <h3>Catalog empty</h3>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="clear-float"></div>
<div class="banner-bottom"></div>


<div>

    <%--<table border="0">--%>
    <%--<c:choose>--%>
    <%--<c:when test="${fn:length(catalog_goods) gt 0}">--%>
    <%--<c:forEach items="${catalog_goods}" var="catalog_goods">--%>
    <%--<div class="catalog_goods">--%>
    <%--&lt;%&ndash;<form action="?catalog_good_id=${catalog_goods.getCatalogId()}" method="get"  >&ndash;%&gt;--%>
    <%--<form action="/basket/add?id=${catalog_goods.getCatalogId()}" method="get">--%>
    <%--<tr>--%>
    <%--<td name="name_good">${catalog_goods.getName()}</td>--%>
    <%--<td name="price">${catalog_goods.getPrice()} рублей</td>--%>
    <%--<td>--%>
    <%--<p><select name='quantity' required>--%>
    <%--<option disabled>Select quantity</option>--%>
    <%--<option>1</option>--%>
    <%--<option>2</option>--%>
    <%--<option>3</option>--%>
    <%--<option>4</option>--%>
    <%--<option>5</option>--%>
    <%--<option>6</option>--%>
    <%--</select></p>--%>
    <%--</td>--%>
    <%--<td><input type="submit" name="add" value="Добавить"></td>--%>
    <%--</tr>--%>
    <%--</form>--%>
    <%--</div>--%>
    <%--</c:forEach>--%>
    <%--</c:when>--%>
    <%--<c:otherwise>--%>
    <%--<h3>Catalog empty</h3>--%>
    <%--</c:otherwise>--%>
    <%--</c:choose>--%>
    <%--</table>--%>
    </form>
</div>
<%--<div>--%>
<%--<form method="post">--%>
<%--<input type="submit" name="basket" value="Корзина">--%>
<%--</form>--%>
<%--</div>--%>
</body>
</html>





