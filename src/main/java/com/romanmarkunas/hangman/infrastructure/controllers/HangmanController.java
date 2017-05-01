package com.romanmarkunas.hangman.infrastructure.controllers;

import com.romanmarkunas.hangman.applications.hangmangame.HangmanGame;
import com.romanmarkunas.hangman.services.HangmanGameStateDAO;
import com.romanmarkunas.hangman.services.RandomWord;
import com.romanmarkunas.hangman.services.WordDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
//        String guessChar = request.getParameter("guesschar");
//        Cookie cookie = getCookie(request);

        HangmanGame game;
        Map<String, Object> jsonData = new HashMap<>();

        if ("true".equals(startNewGame)) {

            String secretWord = new RandomWord(wordDao.getWords()).getNext().getString();
            game = new HangmanGame(secretWord, 5);
            int id = gameStateDao.createNewGame(game.getGameState());
            response.addCookie(createCookie(id));

            jsonData.put("revealed", game.getRevealedWord());
            jsonData.put("triesleft", game.getTriesLeft());
            jsonData.put("message", "started new game with id: " + id + " and word " + secretWord);
        }

        return jsonData;
    }


    private Cookie getCookie(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies.length == 0 || cookies.length > 1 || !acceptedCookieName.equals(cookies[0].getName())) {

            return null;
        }
        else {
            return cookies[0];
        }
    }

    private Cookie createCookie(int id) {

        Cookie cookie = new Cookie(acceptedCookieName, Integer.toString(id));
        cookie.setMaxAge(30*60);

        return cookie;
    }
}
