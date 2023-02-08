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
            width: 500px; /* Ширина таблицы */
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
        }

        TD, TH {
            padding: 3px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
        }

    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<h2>${addedForm == true ?  'Add'  : 'Edit'} meal</h2>
<table>

    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <tr>
        <form action="meals">
            <td><input type="datetime-local" name="datetime" value=${meal.dateTime}></td>
            <td><input type="text" name="description" value=${meal.description}></td>
            <td><input type="number" name="calories" value=${meal.calories}></td>
            <td><input type="number" id="id" name="id" hidden value=${meal.id}><br></td>
            <td><input type="submit" name="save" value="save"></td>
            <td><input type="hidden" name="action" value="save"></td>
        </form>

        <td>
            <button onclick="window.history.back()" type="button">Cancel</button>
        </td>

    </tr>

</table>

</body>
</html>
