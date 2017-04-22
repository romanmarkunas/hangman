package com.romanmarkunas.hangman.applications.hangmangame;

import java.util.LinkedList;
import java.util.List;

public class HangmanGame {

    private final char[] secretWord;
    private char[] revealedWord;
    private int triesLeft;
    private int charsToGuess;
    private List<Character> leftChars;


    HangmanGame(String secretWord, int tries) {

        this.triesLeft = tries;
        this.charsToGuess = secretWord.length();
        this.secretWord = secretWord.toCharArray();
        this.revealedWord = new char[charsToGuess];

        for (int i = 0; i < this.revealedWord.length; i++) {

            this.revealedWord[i] = '_';
        }

        this.leftChars = new LinkedList<>();

        for (int i = 0; i < 26; i++) {

            this.leftChars.add((char)(97 + i));
        }
    }


    public boolean triedBefore(char c) {

        return !leftChars.contains(Character.toLowerCase(c));
    }

    public void guess(char c) {

        if (triedBefore(c)) return;

        char guessChar = Character.toLowerCase(c);
        boolean notGuessed = true;

        for (int i = 0; i < secretWord.length; i++) {

            if (guessChar == secretWord[i]) {

                revealedWord[i] = guessChar;
                charsToGuess--;
                notGuessed = false;
            }
        }

        if (notGuessed) triesLeft--;

        leftChars.remove(Character.valueOf(guessChar));
    }

    public int getTriesLeft() { return triesLeft; }

    public String getRevealedWord() {

        StringBuilder sb = new StringBuilder();

        for (char c : revealedWord) sb.append(c);

        return sb.toString();
    }

    public boolean complete() { return (charsToGuess <= 0 || triesLeft <= 0); }

    public boolean playerWon() { return (charsToGuess <= 0 && triesLeft > 0); }
}
