<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Check</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/basis.css"/>">
</head>
<body>
<jsp:include page="base.jsp"/>

<div class="window">
    <div class="container">
        <div class="check">
            <form action="/check" method="post">
            <table class="check_table">
                <tr>
                    <th class="number-order">Номер заказа</th>
                    <th class="totalsum">Сумма заказа</th>
                    <th class="date-order">Дата заказа</th>
                </tr>
                <tr>
                    <td class="number-order">${number_order}</td>
                    <td class="totalsum">${total_sum}</td>
                    <td class="date-order">${date_order}</td>
                </tr>
            </table>

                <input class="btn_basket" type="submit" name="order" value="Заказать">
            </form>
        </div>

    </div>
</div>
<div class="clear-float"></div>
<div class="banner-bottom"></div>
</body>
</html>
