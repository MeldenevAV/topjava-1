<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add new meal</title>
</head>
<body>
<script>
    $(function () {
        $('input[name=mealDate]').datepicker();
    });
</script>
<fmt:parseDate value="${mealUnit.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="mealDateTime"/>
<form method="POST" action='meals' name="frmAddMeal">
    Meal ID : <input type="text" readonly="readonly" name="mealId"
                     value="<c:out value="${meal.id}" />"/> <br/>
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    Meal DateTime : <input
        type="text" name="mealDate"
        value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${mealDateTime}"/>"/> <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>

