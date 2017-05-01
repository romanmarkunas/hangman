package com.romanmarkunas.hangman.infrastructure.controllers;

import com.romanmarkunas.hangman.applications.hangmangame.HangmanGame;
import com.romanmarkunas.hangman.domain.HangmanGameState;
import com.romanmarkunas.hangman.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HangmanController {

    private static final String acceptedCookieName = "gameid";

    private HangmanGameStateDAO gameStateDao;
    private WordDAO wordDao;


    public HangmanController(HangmanGameStateDAO gameStateDao, WordDAO wordDao) {

        this.gameStateDao = gameStateDao;
        this.wordDao = wordDao;
    }


    @RequestMapping(value={"", "/", "game"}, method=RequestMethod.GET)
    public String getGameLayout() {

        return "layout";
    }

    // TODO - handle exceptions
    @RequestMapping(value="gamestats", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public Map<String, Object> getGameState(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String startNewGame = request.getParameter("newgame");
        String guess = request.getParameter("guesschar");
        Cookie cookie = getCookie(request);

        Map<String, Object> jsonData = new HashMap<>();

        if ("true".equals(startNewGame) && guess == null) {

            startNewGame(jsonData, response);
        }
        else if (cookie != null && guess != null) {

            if (isValidChar(guess)) {

                makeGuess(jsonData, response, Integer.parseInt(cookie.getValue()), guess);
            }
            else {
                jsonData.put("message", "Please try only english alphabet letters, one by one");
            }
        }
        else if (cookie != null) {

            refreshUserScreen(jsonData, Integer.parseInt(cookie.getValue()));
        }
        else {
            jsonData.put("message", "Bad request parameter combination!");
        }

        return jsonData;
    }


    private Cookie getCookie(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        for (Cookie c : cookies) {

            if (acceptedCookieName.equals(c.getName())) {

                return c;
            }
        }

        return null;
    }

    private Cookie createCookie(int id) {

        Cookie cookie = new Cookie(acceptedCookieName, Integer.toString(id));
        cookie.setMaxAge(20*60);

        return cookie;
    }

    private boolean isValidChar(String guess) {

        return guess.length() == 1 && Character.isLetter(guess.toCharArray()[0]);
    }

    private void startNewGame(Map<String, Object> jsonData, HttpServletResponse response) throws Exception {

        String secretWord = new RandomWord(wordDao.getWords()).getNext().getString();
        HangmanGame game = new HangmanGame(secretWord, 5);
        int id = gameStateDao.createNewGame(game.getGameState());

        response.addCookie(createCookie(id));
        updateOutputFromGame(game, jsonData);
    }

    private void makeGuess(Map<String, Object> jsonData, HttpServletResponse response, int id, String guess) throws Exception {

        HangmanGameState previousState = gameStateDao.getGame(id);

        if (previousState == null) {

            jsonData.put("message", "Session timeout! Please start new game");
            return;
        }

        HangmanGame game = new HangmanGame(previousState);

        if (game.complete()) {

            jsonData.put("message", "Thanks for such interest, but game is already complete");
            return;
        }

        response.addCookie(createCookie(id));
        char guessCh = guess.toCharArray()[0];

        if (game.triedBefore(guessCh)) {

            jsonData.put("message", "You've already tried this letter");
            return;
        }

        game.guess(guessCh);
        gameStateDao.updateGame(game.getGameState());

        updateOutputFromGame(game, jsonData);
    }

    private void refreshUserScreen(Map<String, Object> jsonData, int id) throws Exception {

        HangmanGameState previousState = gameStateDao.getGame(id);

        if (previousState == null) {

            return;
        }

        HangmanGame game = new HangmanGame(previousState);
        updateOutputFromGame(game, jsonData);
    }

    private void updateOutputFromGame(HangmanGame game, Map<String, Object> jsonData) {

        if (game.complete()) {

            if (game.playerWon()) {

                jsonData.put("message", "Congratulations! You won!");
            }
            else {
                jsonData.put("message", "Nice try, but you've lost");
            }
        }
        else {
            jsonData.put("message", "Game in process");
        }

        jsonData.put("revealed", game.getRevealedWord());
        jsonData.put("triesleft", game.getTriesLeft());
    }
}
