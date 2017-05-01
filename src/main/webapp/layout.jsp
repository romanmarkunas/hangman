<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hangman game</title>
</head>

<body>

    <p>Secret word:</p>
    <p id="revealedWord"></p>

    <p>Tries left:</p>
    <p id="triesLeft"></p>

    <p>Message:</p>
    <p id="message">Game not started yet</p>

    <form>
        <input type="text" name="guesschar"><br>
        <input type="submit" value="Try this letter"><br><br>
    </form>

    <button onclick="newGame()">Start new game</button>

</body>

<script type="text/javascript" src="resources/jquery-3.2.1.min.js"></script>

<script>

function updateFields(data) {

    console.log("launching updateFields");
    $("#revealedWord").text(data.revealed);
    $("#triesLeft").text(data.triesleft);
    $("#message").text(data.message);
}

function newGame() {

    $.getJSON("${pageContext.request.contextPath}/gamestats?newgame=true", updateFields);
}

</script>

</html>
