<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Active hangman game list</title>
</head>

<body>

    <p>Currently active games:</p>

    <c:forEach var="row" items="${games}">
        <p>Game ${row.id} with word ${row.secretWord} has state ${row.revealedWord}, ${row.triesLeft} tries left;</p>
    </c:forEach>

</body>

</html>
