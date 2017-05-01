package com.romanmarkunas.hangman.infrastructure.controllers;

import com.romanmarkunas.hangman.applications.hangmangame.HangmanGame;
import com.romanmarkunas.hangman.services.HangmanGameStateDAO;
import com.romanmarkunas.hangman.services.RandomWord;
import com.romanmarkunas.hangman.services.WordDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HangmanController {

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
    public Map<String, Object> getGameState(@RequestParam Map<String,String> allRequestParams) throws Exception {

        HangmanGame game;
        int id = 0;
        String secretWord = "";

        if (allRequestParams.containsKey("newgame") && "true".equals(allRequestParams.get("newgame"))) {

            secretWord = new RandomWord(wordDao.getWords()).getNext().getString();
            game = new HangmanGame(secretWord, 5);
            id = gameStateDao.createNewGame(game.getGameState());
        }
        else {
            game = new HangmanGame("gorilla", 3);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("revealed", game.getRevealedWord());
        data.put("message", "started new game with id: " + id + " and word " + secretWord);
        data.put("triesleft", game.getTriesLeft());

        return data;
    }
}
