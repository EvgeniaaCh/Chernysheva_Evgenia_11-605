<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>GoodInfo</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/basis.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/goodInfo.css"/>">
</head>
<body>
<jsp:include page="base.jsp"/>

<div class="window">
    <div class="container">
        <div class="information">
            <div class="img_good_cont">
                <img class="img_good" src="<c:url value="/images/${img_good}"/>" alt=""/>
            </div>

            <div class="descr">
                <div class="add_good">
                    <form action="/basket/add?id=${catalogId}" method="get">
                        <input type="text" name="id" value=${catalogId} hidden>
                        <select class="select_quantity" name='quantity' required>
                            <option disabled>Select quantity</option>
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            <option>6</option>
                        </select>
                        <input type="submit" class="btn_add" name="add" value="Добавить в корзину">
                    </form>
                </div>
                <h3>${good_name}</h3>
                <p>${good_price} руб.</p>

                <div class="text_information">

                    <h3>Описание</h3>
                    <p>${good_description}</p>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="clear-float"></div>
<div class="banner-bottom"></div>


</body>
</html>
