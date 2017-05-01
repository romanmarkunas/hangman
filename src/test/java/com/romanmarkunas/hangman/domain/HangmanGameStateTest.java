package com.romanmarkunas.hangman.domain;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDateTime;


public class HangmanGameStateTest {

    @Test
    public void creation() throws Exception {

        int id = 5;
        String secret = "banana";
        String revealed = "_a_a_a";
        String charsLeft = "bcn";
        int triesLeft = 3;
        LocalDateTime date = LocalDateTime.now();

        HangmanGameState testState = new HangmanGameState(id, secret, revealed, triesLeft, charsLeft, date);

        assertEquals(id, testState.getId());
        assertEquals(secret, testState.getSecretWord());
        assertEquals(revealed, testState.getRevealedWord());
        assertEquals(charsLeft, testState.getNotUsedChars());
        assertEquals(triesLeft, testState.getTriesLeft());
        assertEquals(date, testState.getExpiryDate());
    }
}