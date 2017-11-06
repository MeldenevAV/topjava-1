<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Show All Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table border=1>
    <thead>
    <tr>
        <th>Description</th>
        <th>LocalTime</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="mealUnit" items="${requestScope.mealList}">
        <fmt:parseDate value="${mealUnit.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parseDateTime"/>
        <tr bgcolor="${mealUnit.exceed ? '#deb887':'#f0ffff'}">
            <td><c:out value="${mealUnit.description}"/></td>
            <td><fmt:formatDate value="${parseDateTime}" pattern="dd-MM-yyyy HH:mm"/></td>
            <td><c:out value="${mealUnit.calories}"/></td>
            <td><a href="meals?action=edit&mealId=<c:out value="${mealUnit.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${mealUnit.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>

