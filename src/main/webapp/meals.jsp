<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>

<table>
    <c:forEach var="mealUnit" items="${requestScope.mealWithExceedList}">
        <tr>
            <td><c:out value="${mealUnit.description}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>