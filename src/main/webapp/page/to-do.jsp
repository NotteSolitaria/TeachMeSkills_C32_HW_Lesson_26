<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>To-do list</title>
    <link rel="stylesheet" href="../styles/to-do.css">
</head>
<body>

<h2>List of tasks</h2>

<div class="buttons">
    <form action="about-me" method="get">
        <button type="submit">About me page</button>
    </form>

    <c:if test="${tasks == null || tasks.isEmpty()}">
        <h4>There are no active tasks!</h4>
    </c:if>

    <form action="to-do" method="post">
        <input type="text" name="task" placeholder="Enter the task" required>
        <button type="submit">Add task</button>
    </form>

    <ol>
        <c:forEach var="task" items="${tasks}">
            <li>
                ${task}
                <form action="to-do" method="POST">
                    <input type="hidden" name="deletedTask" value="${task}">
                    <input type="submit" value="Delete" id="delete-button">
                </form>
            </li>
        </c:forEach>
    </ol>
</div>

</body>
</html>