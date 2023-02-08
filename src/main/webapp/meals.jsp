<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 04.02.2023
  Time: 7:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>

    <style type="text/css">
        TABLE {
            /*width: 600px;*/

            border-collapse: collapse; /* Убираем двойные линии между ячейками */
        }

        TD, TH {
            padding: 3px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
        }

        TR.red {
            color: red;
        }

        TR.green {
            color: green;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=openAddForm">Add meal</a>

<table>

    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <jsp:useBean id="mealsTos" scope="request" type="java.util.List"/>
    <c:forEach var="mealslist" items="${mealsTos}">

        <tr class=${mealslist.excess == true ?  'red'  : 'green'}>

            <fmt:parseDate value="${ mealslist.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                           type="both"/>
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ parsedDateTime }"/>
            </td>
            <td>${mealslist.description}</td>
            <td>${mealslist.calories}</td>
            <td><a href="meals?action=openEditForm&id=${mealslist.id}">edit</a></td>
            <td><a href="meals?action=delete&id=${mealslist.id}">delete</a></td>

        </tr>

    </c:forEach>
</table>

</body>
</html>
