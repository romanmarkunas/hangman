package com.romanmarkunas.hangman.applications.hangmangame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HangmanGameTest {

    private HangmanGame testGame;
    private String secretWord = "ellipse";


    @Before
    public void setUp() throws Exception {

        testGame = new HangmanGame(secretWord, 5);
    }


    @Test
    public void creation() throws Exception {

        assertEquals(5, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("_______", testGame.getRevealedWord());
    }

    @Test
    public void playerWon() throws Exception {

        testGame.guess('e');

        assertEquals(5, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("e_____e", testGame.getRevealedWord());

        testGame.guess('L');

        assertEquals(5, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("ell___e", testGame.getRevealedWord());

        testGame.guess('l');
        testGame.guess('l');

        assertEquals(5, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("ell___e", testGame.getRevealedWord());

        testGame.guess('p');
        testGame.guess('s');
        testGame.guess('i');

        assertEquals(5, testGame.getTriesLeft());
        assertTrue(testGame.complete());
        assertTrue(testGame.playerWon());
        assertEquals(secretWord, testGame.getRevealedWord());
    }

    @Test
    public void playerLost() throws Exception {

        testGame.guess('A');

        assertEquals(4, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("_______", testGame.getRevealedWord());

        testGame.guess('a');

        assertEquals(4, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("_______", testGame.getRevealedWord());

        testGame.guess('z');

        assertEquals(3, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("_______", testGame.getRevealedWord());

        testGame.guess('k');
        testGame.guess('o');
        testGame.guess('n');

        assertEquals(0, testGame.getTriesLeft());
        assertTrue(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("_______", testGame.getRevealedWord());
    }
}