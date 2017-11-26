<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Base</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/basis.css"/>">
</head>
<body>
<div class="header">
    <div class="container">
        <div class="header_top">
            <div class="logo">
                <a href="/main"><img src="<c:url value="/images/shop.png"/>" alt=""/></a>
            </div>
            <div class="top_right">
                <ul>
                    <c:if test="${session ne 1}">
                        <li><a href="/registration">Регистрация </a></li>
                    </c:if>
                    |
                    <li><a href="/basket">Корзина</a></li>
                    |
                    <li class="login">
                        <c:if test="${session ne 1}">
                            <div id= "loginContainer"><a href="/input" class="loginButton"><span>Войти</span></a>
                            </div>
                        </c:if>
                        <c:if test="${session eq 1}">
                            <div id="loginContainer"><a href="/exit" class="loginButton"><span>Выйти</span></a>
                            </div>
                        </c:if>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="head-bann">
    <div class="container">
        <div class="head-nav">
            <span class="menu"> </span>
            <ul class="megamenu skyblue">
                <li><a class="color1" href="/main">Главная</a></li>
                <li><a class="color2" href="/catalog?type=dresses">Платья</a>
                <li><a class="color3" href="/catalog?type=sweaters">Кофты</a>
                <li><a class="color4" href="/catalog?type=jeans">Джинсы</a>
                <li><a class="color5" href="/catalog?type=shoes">Туфли</a>
                <li><a class="color6" href="/catalog?type=accessories">Аксессуары</a>
                <li><a class="color7" href="/catalog?type=bags">Сумки</a>
            </ul>
        </div>
    </div>
</div>
</div>

</body>
</html>
