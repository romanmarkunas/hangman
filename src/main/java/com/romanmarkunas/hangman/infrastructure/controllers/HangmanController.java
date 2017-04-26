package com.romanmarkunas.hangman.infrastructure.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HangmanController {

    @RequestMapping("/")
    public String getGameLayout() {

        return "layout";
    }
}
