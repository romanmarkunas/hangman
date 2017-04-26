<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hangman game</title>
</head>

<body>

    <p>Secret word:</p>
    <p id="secretWord"></p>

    <p>Your guess:</p>
    <p id="revealedWord"></p>

    <p>Tries left:</p>
    <p id="triesLeft"></p>

    <p>Message:</p>
    <p id="message">Game not started yet</p>

    <form>
        <input type="text" name="guesschar"><br>
        <input type="submit" value="Try this letter"><br><br>
    </form>

    <button type="button">Start new game</button>

</body>

</html>
