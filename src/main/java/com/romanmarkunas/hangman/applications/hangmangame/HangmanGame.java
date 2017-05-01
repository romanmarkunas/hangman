package com.romanmarkunas.hangman.applications.hangmangame;

import com.romanmarkunas.hangman.domain.HangmanGameState;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class HangmanGame {

    private final char[] secretWord;
    private char[] revealedWord;
    private int triesLeft;
    private int charsToGuess;
    private char[] leftChars;
    private int id;


    public HangmanGame(String secretWord, int tries) {

        this.triesLeft = tries;
        this.charsToGuess = secretWord.length();
        this.secretWord = secretWord.toCharArray();
        this.revealedWord = new char[charsToGuess];

        for (int i = 0; i < this.revealedWord.length; i++) {

            this.revealedWord[i] = '_';
        }

        this.leftChars = new char[26];

        for (int i = 0; i < 26; i++) {

            this.leftChars[i] = (char)(97 + i);
        }

        this.id = 0;
    }

    public HangmanGame(HangmanGameState state) {

        this.triesLeft = state.getTriesLeft();
        this.secretWord = state.getSecretWord().toCharArray();
        this.revealedWord = state.getRevealedWord().toCharArray();
        this.charsToGuess = 0;

        for (int i = 0; i < this.revealedWord.length; i++) {

            if (this.revealedWord[i] == '_') {

                this.charsToGuess++;
            }
        }

        this.leftChars = state.getNotUsedChars().toCharArray();
        this.id = state.getId();
    }


    public boolean triedBefore(char c) {

        boolean contains = false;
        char testChar = Character.toLowerCase(c);

        for (char ch : leftChars) {

            if (ch == testChar) {

                contains = true;
            }
        }

        return !contains;
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

        char[] temp = new char[leftChars.length - 1];
        int index = 0;

        // TODO - all these for loops is a good place for lambdas
        for (char ch : leftChars) {

            if (ch != guessChar) {

                temp[index] = ch;
                index++;
            }
        }

        leftChars = temp;
    }

    public int getTriesLeft() { return triesLeft; }

    public String getRevealedWord() {

        StringBuilder sb = new StringBuilder();

        for (char c : revealedWord) sb.append(c);

        return sb.toString();
    }

    public boolean complete() { return (charsToGuess <= 0 || triesLeft <= 0); }

    public boolean playerWon() { return (charsToGuess <= 0 && triesLeft > 0); }

    public HangmanGameState getGameState() {

        return new HangmanGameState(id, String.valueOf(secretWord), String.valueOf(revealedWord), triesLeft,
                String.valueOf(leftChars), LocalDateTime.now().plus(30, ChronoUnit.MINUTES));
    }
}
