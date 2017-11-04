<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table border="1">
    <th>Description</th>
    <th>LocalTime</th>
    <th>Calories</th>
    <th>Exceed</th>
    <c:forEach var="mealUnit" items="${requestScope.mealWithExceedList}">

        <tr bgcolor="${mealUnit.exceed} ? #deb887:#f0ffff">
            <td><c:out value="${mealUnit.description}"/></td>
            <td><fmt:formatDate pattern="yyyy-MMM-dd" value="${mealUnit.dateTime}"/></td>
            <td><c:out value="${mealUnit.calories}"/></td>
            <td><c:out value="${mealUnit.exceed}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>